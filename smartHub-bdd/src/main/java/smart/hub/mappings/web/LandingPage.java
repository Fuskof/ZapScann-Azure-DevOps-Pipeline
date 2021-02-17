package smart.hub.mappings.web;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;
import smart.hub.helpers.Constants;

import java.time.Duration;

public class LandingPage extends PageObject{

    @FindBy(xpath = "//div[@class='ant-menu-submenu-title']")
    public WebElementFacade transactionsButton;

    @FindBy(xpath = "//*[@title='Rule Templates']")
    public WebElementFacade ruleTemplatesButton;

// To be added when AUTH will be available
//    public void loginAsAnUser(String username, String password) {
//        selectUsersButton.withTimeoutOf(Duration.ofSeconds(Constants.MAX)).waitUntilClickable().then().click();
//        waitABit(300);
//        usernameField.withTimeoutOf(Constants.MAX, TimeUnit.SECONDS).waitUntilClickable();
//        usernameField.sendKeys(username);
//        passwordField.withTimeoutOf(Constants.MAX, TimeUnit.SECONDS).waitUntilVisible().then().sendKeys(password);
//        submit.withTimeoutOf(Constants.MAX, TimeUnit.SECONDS).waitUntilClickable().click();
//    }

//    public void logUserOut() {
//        waitABit(500);
//        profileMenu.withTimeoutOf(Constants.MAX, TimeUnit.SECONDS).waitUntilClickable().then().click();
//        waitABit(500);
//        logOut.withTimeoutOf(Constants.MAX, TimeUnit.SECONDS).waitUntilClickable().then().click();
//    }

    public void navigateToRuleTemplates() {
        waitABit(500);
        transactionsButton.withTimeoutOf(Duration.ofSeconds(Constants.MAX)).waitUntilVisible();
        transactionsButton.withTimeoutOf(Duration.ofSeconds(Constants.MAX)).waitUntilClickable().then().click();
        waitABit(300);
        ruleTemplatesButton.withTimeoutOf(Duration.ofSeconds(Constants.MAX)).waitUntilClickable().then().click();
    }
}
