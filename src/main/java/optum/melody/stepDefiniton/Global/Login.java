package optum.melody.stepDefiniton.Global;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import optum.melody.controller.WebController;
import optum.melody.setup.RunInformation;
import optum.melody.utilities.CoreFunctions;
import optum.melody.utilities.ExplicitWait;

public class Login {

	private static final String SUPERUSER_USERNAME = "";
	private static final String SUPERUSER_PRODUCTION_PASSWORD = "";
	private static final String SUPERUSER_TEST_PASSWORD = "";
	private static String loginURL = "";

	public static void loginSetup(String featureUsername, String featurePassword, Boolean redirect) {

		boolean isSuperUser = true;

		if (featurePassword != null)
			isSuperUser = false;

		loginURL = GenerateLoginURL(isSuperUser, RunInformation.isMobile());

		if (isSuperUser) {

			loginRACF();
			loginSuperuser(featureUsername, (isLegacyUser()));

		} else {
			loginThroughHSID(featureUsername, featurePassword);
			answerStandardSecurityQuestions();
		}

		checkLoginRedirectFinished();

		if (handleMultiplePlans())
			checkLoginRedirectFinished();

		if (redirect)
			redirectToSecondaryPages();

		checkForiPerceptionsFrame();

	}

	private static void answerStandardSecurityQuestions() {
		String securityQuestionAnswer = "";

		if (CoreFunctions.getElementText("HSID Security Question Text").contains("phone")) {
			securityQuestionAnswer = "testnumber";
		} else if (CoreFunctions.getElementText("HSID Security Question Text").contains("name")) {
			securityQuestionAnswer = "testname";
		} else if (CoreFunctions.getElementText("HSID Security Question Text").contains("color")) {
			securityQuestionAnswer = "testcolor";
		}

		CoreFunctions.sendToElement("HSID Security Answer Input", securityQuestionAnswer);
		CoreFunctions.clickOnElement("HSID Security Continue Button");
	}

	private static void loginThroughHSID(String username, String password) {
		WebController.driver.get(CoreFunctions.getLegacyURL());

		CoreFunctions.sendToElement("HSID Login Username Input", username);
		CoreFunctions.sendToElement("HSID Login Password Input", password);
		CoreFunctions.clickOnElement("HSID Login Sign In Button");

		ExplicitWait.WaitForURLToContain("=securityQuestion", 30);
	}

	private static void redirectToSecondaryPages() {

		String desiredURL = "";

		switch (RunInformation.getRunEnvironment()) {

		case OFFLINE_PRODUCTION:
		case PRODUCTION:
			desiredURL = "https://prd.myuhc.com/content/myuhc/en/secure/account-profile.html";
			break;

		case STAGE:
			desiredURL = "https://stg.myuhc.com/content/myuhc/en/secure/account-profile.html";
			break;

		case TEST:
			desiredURL = "https://test3.myuhc.com/content/myuhc/en/secure/account-profile.html";
			break;

		}

		WebController.driver.get(desiredURL);

		ExplicitWait.WaitForURLToEqual(desiredURL, 20);

		if (RunInformation.getRunEnvironment() == RunInformation.Environment.OFFLINE_PRODUCTION) {

			desiredURL = "https://offlineprd.myuhc.com/content/myuhc/en/secure/account-profile.html";
			WebController.driver.get(desiredURL);

			ExplicitWait.WaitForURLToEqual(desiredURL, 20);
		}

	}

	private static void checkLoginRedirectFinished() {

		try {
			// To Do:
			// Possibly Change this to checking for key links, each one of these
			// cost 3 seconds
			// Possibly remove the number of checks
			ExplicitWait.WaitForURLToNotContain("action.do", 10);
			ExplicitWait.WaitForURLToNotContain("login.do", 10);
			ExplicitWait.WaitForURLToNotContain("preSuHsidloginuser.do", 10);
			ExplicitWait.WaitForURLToNotContain("prewelcome.do", 10);
			ExplicitWait.WaitForURLToNotContain("accounts.myuhc.com", 30);
			ExplicitWait.WaitForURLToNotContain(".uhc.com", 30);

		} catch (TimeoutException e) {
			Assert.fail("Login redirection failed. Current URL is: " + WebController.driver.getCurrentUrl());
		}
	}

	private static void loginSuperuser(String username, Boolean isLegacyUser) {
		CoreFunctions.sendToElement("Superuser Username Input", username, (!isLegacyUser ? "Default" : "Legacy"));
		CoreFunctions.clickOnElement("Superuser Login Button", (RunInformation.isMobile() ? "Mobile" : (!isLegacyUser ? "Default" : "Legacy")));
	}

	private static void loginRACF() {

		try {

			WebController.driver.get(loginURL);
			CoreFunctions.sendToElement("RACF Login Input", SUPERUSER_USERNAME);
			CoreFunctions.sendToElement("RACF Password Input", (!isTest()) ? SUPERUSER_PRODUCTION_PASSWORD : SUPERUSER_TEST_PASSWORD);
			CoreFunctions.clickOnElement("RACF Continue Button");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to login at RACF page.\n");
		}

	}

	// URL Generation
	private static String GenerateLoginURL(boolean isSuperUser, boolean isMobile) {
		String loginURL = "";

		loginURL = CoreFunctions.getLegacyURL();
		loginURL += (isSuperUser) ? "/member/" : "";
		if (isSuperUser)
			loginURL += (isMobile) ? "premsuaction.do" : "presuaction.do";

		return loginURL;
	}
	// -------------------------------------------------------

	// Check user/environment information
	private static boolean isTest() {
		return (RunInformation.getRunEnvironment() == RunInformation.Environment.TEST);
	}

	public static boolean isLegacyUser() {

		if (RunInformation.getCredentials() == null)
			return false;

		return RunInformation.getCredentials().replace(" ", "").contains("legacylogin");

	}
	// -------------------------------------------------------

	// Multiplan Users
	private static boolean handleMultiplePlans() {

		Integer whichPlan = getPlanNumber();

		if (whichPlan != null) {
			WebController.driver.findElement(By.id("planId" + whichPlan)).click();
			WebController.driver.findElement(By.id("continuebutton")).click();

			return true;
		}

		return false;
	}

	private static Integer getPlanNumber() {

		if (RunInformation.isCredentialsEmpty() || !RunInformation.getCredentials().contains("chooseplan"))
			return null;

		return Integer.parseInt(RunInformation.getCredentials().replace(" ", "").split("chooseplan")[1].substring(0, 1)) - 1;
	}

	// IPerceptions Popup Issue
	private static void checkForiPerceptionsFrame() {
		WebController.driver.navigate().refresh();
		// This is to remove the iPerception frame
	}

}