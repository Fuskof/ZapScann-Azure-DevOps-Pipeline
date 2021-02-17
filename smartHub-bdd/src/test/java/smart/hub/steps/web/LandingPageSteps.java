package smart.hub.steps.web;

import configuration.AppConfiguration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.mappings.web.LandingPage;

@Configurable
@ContextConfiguration(classes= AppConfiguration.class)
public class LandingPageSteps {

    private Logger logger = Logger.getLogger(LandingPageSteps.class.getName());

    private LandingPage landingPage;
    
    @Autowired
    private Environment env;

    @Given("^I login to the admin portal$")
    public void iLoginToTheAdmin() throws Exception{
        logger.info("Start - I am a user logged on Admin Portal system!");

        landingPage.openUrl(env.getProperty("smartHub.url"));
        Serenity.setSessionVariable("env").to(env.getProperty("env"));

        //tobe used when AUTH will be implemented
        //landingPage.loginAsAnUser(env.getProperty("admin.username"), env.getProperty("admin.password"));
        logger.info("Done - I logged on Admin Portal system!");
    }

//    tobe used when AUTH will be implemented
//    @Given("^I log out$")
//    public void iLogOut() {
//        landingPage.logUserOut();
//    }

    @When("I navigate to Rule Templates")
    public void iNavigateToRuleTemplates() {
        landingPage.navigateToRuleTemplates();
    }
}

