package smart.hub.runners;

import configuration.AppConfiguration;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
            monochrome = true,
        glue = "smart.hub"
)
@ContextConfiguration(classes = AppConfiguration.class)
public class RunTests {

}
