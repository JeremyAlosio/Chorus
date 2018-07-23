package optum.melody.stepDefiniton;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.openqa.selenium.TimeoutException;

import cucumber.api.Scenario;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import optum.melody.controller.WebController;
import optum.melody.setup.AppConfiguration;
import optum.melody.setup.RunInformation;
import optum.melody.stepDefiniton.Global.Login;
import optum.melody.utilities.CoreFunctions;

public class CommonStepDefinition {

	@Before
	public static void setUp(Scenario scenario) throws MalformedURLException, URISyntaxException {
		WebController.launchBrowser(scenario.getName());
	}

	@After
	public void tearDown(Scenario scenario) {

	}

	// ------------------------------------------------------------------------
	// Login Function
	// ------------------------------------------------------------------------
	@Given("^the user logs in as \"?([^\" ]*)\"?(?: with the password \"?([^\" ]*)\"?|)(?:( and does not redirect to 2.0)|)(?: with the credentials \"?(.+)\"?|)")
	public void login(String username, String password, String redirect, final String credentials) {

		RunInformation.setCredentials(credentials);

		Login.loginSetup(username, password, redirect == null);

	}

	// ------------------------------------------------------------------------
	// Element Selection
	// ------------------------------------------------------------------------
	@When("^the user clicks on \"?([^\"\\,]*)\"?(?:, with the variant \"?([^\"\\,]*)\"?|))")
	public static void clickOnLink(String linkName, String variant) {
		CoreFunctions.clickOnElement(linkName, variant);
	}

	// ------------------------------------------------------------------------
	// Switches Windows/Frames
	// ------------------------------------------------------------------------
	@When("^the user switches(?: windows| to the iFrame \"?([^\"\\,]*)\"?(?:, with the variant \"?([^\"\\,]*)\"?|))$")
	public static void switchToWF(String frameName, String frameVariant) {
		if (frameName == null)
			CoreFunctions.switchWindows();
		else
			CoreFunctions.switchFrames(frameName, frameVariant);
	}

	// ------------------------------------------------------------------------
	// URL Manipulation
	// ------------------------------------------------------------------------
	@Given("^the user has navigated to the url \"?([^\"]*)\"?$")
	public static void changeURL(@Transform(CoreFunctions.URLTransformer.class) String URL) throws MalformedURLException {
		
	}

	// ------------------------------------------------------------------------
	// URL Check
	// ------------------------------------------------------------------------
	@Then("^the user comes to the \"([^\"]*)\" page$")
	public void successfulLanding(@Transform(CoreFunctions.URLTransformer.class) String URL) throws MalformedURLException {

		try {
			optum.melody.utilities.ExplicitWait.WaitForURLToContain(URL, AppConfiguration.getWaitTime());
		} catch (TimeoutException e) {
			final String actualURL = WebController.driver.getCurrentUrl();
			Assert.fail(String.format("Timeout while waiting for URL to contain \"%s\", was \"%s\"", URL, actualURL));
		}
	}

}
