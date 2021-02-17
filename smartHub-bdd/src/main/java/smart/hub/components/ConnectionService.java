package smart.hub.components;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import smart.hub.builders.HeaderBuilder;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import static smart.hub.helpers.Constants.LOG_SEPARATOR;
import static smart.hub.helpers.Constants.TIMESTAMP;

@Component
public class ConnectionService {

    @Autowired
    private HeaderBuilder headerProps;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ConnectionState conState;

    @Autowired
    private ResultState resState;

    @Autowired
    private Environment env;

    private Gson gson = new GsonBuilder().setDateFormat(TIMESTAMP).setPrettyPrinting().create();

    private Logger logger = Logger.getLogger(ConnectionService.class.getName());

    public void getResponseForGET(String endPoint) throws Exception {
        executeRequest("get", "smartHub.api", endPoint, true, null, null);
    }

    public void getResponseForPUT(String endPoint, Object body) throws Exception {
        String json = convertToJson(body);
        executeRequest("put", "smartHub.api", endPoint, true, json, null);
    }

    public <T> T getResponseForPOST(String endPoint, Object body, Class<T> classOfT) throws Exception {
        String json = convertToJson(body);
        executeRequest("post", "smartHub.api", endPoint, true, json, null);
        return convertJsonToModel(resState.getResult(), classOfT);
    }

    public void getResponseForPOST(String endPoint) throws Exception {
        executeRequest("post", "smartHub.api", endPoint, true, null, null);
    }

    public void getResponseForPOST(String endPoint, String json) throws Exception {
        executeRequest("post", "smartHub.api", endPoint, true, json, null);
    }

    public void getResponseForPOST(String endPoint, String json, boolean hasAuth) throws Exception {
        executeRequest("post", "smartHub.api", endPoint, hasAuth, json, null);
    }

    public void getResponseForGET(String endPoint, String json) throws Exception {
        executeRequest("get", "smartHub.api", endPoint, true, json, null);
    }

    public void getResponseForPATCH(String endPoint, String json) throws Exception {
        executeRequest("patch", "smartHub.api", endPoint, true, json, null);
    }

    public void getResponseForPATCH(String endPoint, Object body) throws Exception {
        String json = convertToJson(body);
        executeRequest("patch", "smartHub.api", endPoint, true, json, null);
    }

    public void getResponseForPOSTNoAuth(String endPoint, String json) throws Exception {
        executeRequest("post", "smartHub.api", endPoint, false, json, null);
    }

    public void getResponseForPOSTPayEngine(String endPoint, String json) throws Exception {
        executeRequest("post", "payengine.url", endPoint, true, json, null);
    }

    public void getResponseForPOST(String endPoint, File file) throws Exception {
        executeRequest("post", "smartHub.api", endPoint, true, null, file);
    }

    public void getResponseForDELETE(String endPoint) throws Exception {
        executeRequest("delete", "smartHub.api", endPoint, true, null, null);
    }

    public void makePostRequestWithFile(String endPoint, File file) throws Exception {
        executeRequest("postMultipart", "smartHub.api", endPoint, true, null, file);
    }

    private void executeRequest(String requestType, String endpointRoot, String endPoint, boolean hasAuth, String json, File file) throws Exception {
        String fullEndpoint = env.getProperty(endpointRoot) + endPoint;
        CloseableHttpResponse response = null;
        try {
            StringEntity stringEntity = null;
            HttpRequestBase request = null;

            switch (requestType) {
                case "get":
                    request = new HttpGet(fullEndpoint);
                    break;
                case "patch":
                    request = new HttpPatch(fullEndpoint);
                    if (!Objects.isNull(json)) {
                        stringEntity = new StringEntity(json);
                        ((HttpPatch) request).setEntity(stringEntity);
                    }
                    break;
                case "post":
                    request = new HttpPost(fullEndpoint);
                    if (!Objects.isNull(json)) {
                        stringEntity = new StringEntity(json);
                        ((HttpPost) request).setEntity(stringEntity);
                    }
                    break;
                case "postFile":
                    request = new HttpPost(fullEndpoint);
                    FileEntity fileEntity = new FileEntity(file, ContentType.create("text/plain", "UTF-8"));
                    ((HttpPost) request).setEntity(fileEntity);
                    break;
                case "postMultipart":
                    request = new HttpPost(fullEndpoint);
                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    builder.addBinaryBody(
                            "file",
                            file,
                            ContentType.MULTIPART_FORM_DATA,
                            file.getName()
                    );
                    HttpEntity multipartEntity = builder.build();
                    ((HttpPost) request).setEntity(multipartEntity);
                    break;
                case "put":
                    request = new HttpPut(fullEndpoint);
                    stringEntity = new StringEntity(json);
                    ((HttpPut) request).setEntity(stringEntity);
                    break;
                case "delete":
                    request = new HttpDelete(fullEndpoint);
                    break;
            }

            if (hasAuth) {
                headerProps.basicHeaderProps(request);
            } else {
                headerProps.firstLoginHeaderProps(request);
            }

            Header[] headers = request.getAllHeaders();
            String requestOutput = "No request body";
            if (!Objects.isNull(json)) {
                resState.setRequestJson(json);
                requestOutput = "Request body:\n" + json;
            }
            logger.info(LOG_SEPARATOR
                    + "Request endpoint: " + fullEndpoint + "\n"
                    + "Request headers: " + Arrays.toString(headers) + "\n"
                    + requestOutput
                    + LOG_SEPARATOR);

            response = conState.getClient().execute(request);
            resState.setStatusCode(response.getStatusLine().getStatusCode());
            resState.setStatusMessage(response.getStatusLine().getReasonPhrase());
            resState.setHeaders(response.getAllHeaders());
            resState.setResult(resultService.rawResult(response));

            String output = resState.getResult();
            if (resState.getStatusCode() != 500 && resState.getStatusCode() != 404 && resState.getStatusCode() != 204) {
                try {
                    output = new JSONObject(resState.getResult()).toString(2);
                } catch (JSONException | NullPointerException e) {
                    logger.info("Can't convert request result to json, outputting the original result");
                }
            }
            String responseContent = "Empty response body";
            if (response.getEntity() != null && response.getEntity().getContentType() != null) {
                responseContent = response.getEntity().getContentType().toString();
            }
            logger.info(LOG_SEPARATOR
                    + "Response status code: " + resState.getStatusCode() + "\n"
                    + "Response content type: " + responseContent + "\n"
                    + "Response headers: " + Arrays.toString(resState.getHeaders()) + "\n"
                    + "Response content:" + "\n"
                    + output
                    + LOG_SEPARATOR);
        } finally {
            response.close();
        }
    }

    public String convertToJson(Object model) {
        return gson.toJson(model);
    }

    public <T> T convertJsonToModel(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
