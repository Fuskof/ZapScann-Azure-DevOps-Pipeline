package smart.hub.mappings.web;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;
import smart.hub.helpers.Constants;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class RuleTemplatesPage extends PageObject{

    @FindBy(xpath = "//*[@class='ant-input']")
    public WebElementFacade searchForRuleInputField;

    @FindBy(xpath = "//td//span[contains(@class,'column--name')]")
    public List<WebElementFacade> ruleNamesFromTable;

    @FindBy(xpath = "//tbody//tr")
    public List<WebElementFacade> linesDisplayedInTheTable;

    public void searchForRule() {
        Random rand = new Random();
        String templateName = ruleNamesFromTable.get(rand.nextInt(ruleNamesFromTable.size())).getText();
        searchForRuleInputField.withTimeoutOf(Duration.ofSeconds(Constants.MAX)).waitUntilClickable().then().sendKeys(templateName);
    }

    public boolean theSearchedTemplateIsDisplayed() {
        if (linesDisplayedInTheTable.size() == 1){
           return true;
        };
        return false;
    }
}
