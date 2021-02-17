package smart.hub.helpers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smart.hub.components.ResultState;

import java.util.Calendar;

@Component
public class Helpers {

    @Autowired
    private ResultState resState;

    public static String getUnique() {
        return Calendar.getInstance().getTimeInMillis() + "";
    }

    public String getNode(String node, String fromJson){
        JSONObject obj = new JSONObject();
        String nodeString = obj.getJSONObject(fromJson).getString(node);

        return nodeString;
    }

    public String getNode(String node){
        JSONObject obj = new JSONObject(resState.getRequestJson());
        String nodeString = obj.getString(node);

        return nodeString;
    }

    public String getNodeFromResponse(String node){
        JSONObject obj = new JSONObject(resState.getResult());
        String nodeString = obj.getString(node);

        return nodeString;
    }

    public String modifySingleNodeJson(String key, String value){
        JSONObject js = new JSONObject(resState.getRequestJson());
        js.put(key, value);

        return js.toString();
    }
}
