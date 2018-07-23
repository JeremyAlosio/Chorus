package optum.melody;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import optum.melody.setup.AppConfiguration;
import optum.melody.setup.RunInformation;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/test-report" }, features = { "src/main/features/current" }, snippets = SnippetType.CAMELCASE)
public class AutomationTestRunner {

	@BeforeClass
	public static void setUp() {
		AppConfiguration.instantiate(System.getProperty("configFilePath"));
		RunInformation.instantiateTags();
	}

	@AfterClass
	public static void tearDown() {

	}
}
