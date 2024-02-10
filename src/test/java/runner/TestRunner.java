package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "html:target/cucumber-report/cucumber.html"},
		features = {"src/test/resources/features"},
		glue={"stepDefinitions"},
		monochrome = true,
		dryRun = false
				//	strict = true
		)

public class TestRunner {

}


