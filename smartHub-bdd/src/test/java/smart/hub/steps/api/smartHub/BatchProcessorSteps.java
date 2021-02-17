package smart.hub.steps.api.smartHub;

import configuration.AppConfiguration;
import io.cucumber.java.en.Given;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.ResultState;
import smart.hub.helpers.Constants;
import smart.hub.mappings.api.models.response.batchProcessor.BatchResponseModel;

import java.io.File;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class BatchProcessorSteps {

    private Logger logger = Logger.getLogger(BatchProcessorSteps.class.getName());

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ResultState resultState;

    @Given("^I convert the response to json for BatchProcessor$")
    public void iConvertTheResponseToJsonForBatchProcessor() {
        BatchResponseModel response = connectionService.convertJsonToModel(resultState.getResult(), BatchResponseModel.class);
    }

    @Given("^I process the following batch file '(.*)'$")
    public void iMakeAPOSTRequestToTheWithTheFollowingBatchFile(String fileName) throws Exception {
        connectionService.makePostRequestWithFile(Constants.ENDPOINT_BATCH_PROCESSOR, new File(Constants.pathToFiles + "batchFiles\\" + fileName));
    }
}
