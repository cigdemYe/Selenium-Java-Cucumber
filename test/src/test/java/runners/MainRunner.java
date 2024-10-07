package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = {"classpath:features"}, glue = {"stepDefinitions"},
        tags = "@contact-us", monochrome = true, dryRun = false)
public class MainRunner extends AbstractTestNGCucumberTests {
}
