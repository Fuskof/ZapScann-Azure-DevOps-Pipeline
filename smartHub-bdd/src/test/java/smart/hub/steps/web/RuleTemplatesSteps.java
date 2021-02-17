package smart.hub.steps.web;

import configuration.AppConfiguration;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Screenshots;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.mappings.web.RuleTemplatesPage;

import static org.assertj.core.api.Assertions.assertThat;

@Configurable
@ContextConfiguration(classes= AppConfiguration.class)
public class RuleTemplatesSteps {

    private Logger logger = Logger.getLogger(RuleTemplatesSteps.class.getName());

    private RuleTemplatesPage ruleTemplatesPage;

    @When("I search for a rule")
    public void iSearchForARule() {
        ruleTemplatesPage.searchForRule();
    }

    @Then("I can see that the result was filtered correctly")
    @Screenshots(onlyOnFailures=true)
    public void iCanSeeThatTheResultWasFilteredCorrectly() {
        assertThat(ruleTemplatesPage.theSearchedTemplateIsDisplayed()).isTrue();
    }
}

