package optum.melody.utilities;

import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import cucumber.api.Transformer;
import optum.melody.controller.WebController;
import optum.melody.setup.AppConfiguration;
import optum.melody.setup.RunInformation;
import optum.melody.utilities.selectors._Selectors;
import optum.melody.utilities.selectors._URLs;

public class CoreFunctions {

	// ------------------------------------------------------------------------
	// Waiting on Pages and Loading
	// ------------------------------------------------------------------------
	public static void waitForPageLoad() {

		if (!WebController.driver.getCurrentUrl().contains("uhc.com/dashboard") && !WebController.driver.getCurrentUrl().contains("myuhc.com/member/") && !WebController.driver.getCurrentUrl().contains("=securityQuestion")) {
			WaitForLoadingOverlay();
		}

		JavascriptExecutor js = null;
		for (int i = 0; i < AppConfiguration.getWaitTime(); i++) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			js = (JavascriptExecutor) WebController.driver;
			if (js.executeScript("return document.readyState").toString().equals("complete"))
				break;

		}
	}

	public static void WaitForLoadingOverlay() {
		try {
			ExplicitWait.WaitForElementToBeInvisible(By.id("loading-overlay"), 45);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------
	// Selecting/Clicking on Elements and Element manipulation
	// ------------------------------------------------------------------------

	public static WebElement getElement(String elementName) {
		return getElement(elementName, null);
	}

	public static WebElement getElement(String elementName, String elementVariant) {

		waitForPageLoad();

		String name = elementName.toLowerCase();

		if (_Selectors.MAPPING.containsKey(name)) {
			By selector = (elementVariant == null) ? _Selectors.MAPPING.get(name).getDefault() : _Selectors.MAPPING.get(name).getVariant(elementVariant);
			try {
				return WebController.driver.findElement(selector);
			} catch (NoSuchElementException e) {
				return null;
			}
		} else {
			Assert.fail("No mapping for friendly name \"" + elementName + "\"");
			return null;
		}
	}

	public static String getElementText(String linkName) {
		return getElementText(linkName, null);
	}

	public static String getElementText(String linkName, String variant) {

		WebElement element = getElement(linkName, variant);

		if (element == null) {
			Assert.fail("Element " + linkName + " is null. \nCurrent page is: " + WebController.driver.getCurrentUrl());
		} else {
			return element.getText();
		}

		return null;
	}

	public static void clickOnElement(String linkName) {
		clickOnElement(linkName, null);
	}

	public static void clickOnElement(String linkName, String variant) {

		WebElement element = getElement(linkName, variant);

		if (element == null) {
			Assert.fail("Element " + linkName + " is null. \nCurrent page is: " + WebController.driver.getCurrentUrl());
		} else {
			element.click();
		}
	}

	public static void sendToElement(String linkName, String data) {
		sendToElement(linkName, data, null, false);
	}

	public static void sendToElement(String linkName, String data, String variant) {
		sendToElement(linkName, data, variant, false);
	}

	public static void sendToElement(String linkName, String data, boolean checkIfSuccessful) {
		sendToElement(linkName, data, null, false);
	}

	public static void sendToElement(String linkName, String data, String variant, boolean checkIfSuccessful) {

		WebElement element = getElement(linkName, variant);

		if (element == null) {
			Assert.fail("Element " + linkName + " is null. \nCurrent page is: " + WebController.driver.getCurrentUrl());
		} else {
			element.click();
			element.sendKeys(data);
		}

		if (checkIfSuccessful)
			if (!element.getText().equals(data))
				Assert.fail("Data wasn't successfully sent to element: " + linkName);

	}

	// ------------------------------------------------------------------------
	// URL Retrievers
	// ------------------------------------------------------------------------

	public static String getLegacyURL() {

		switch (RunInformation.getRunEnvironment()) {

		case OFFLINE_PRODUCTION:
			return MyUHCConstants.OFFLINE_PRODUCTION_URL_LEGACY;

		case PRODUCTION:
			return MyUHCConstants.PRODUCTION_URL_LEGACY;

		case STAGE:
			return MyUHCConstants.STAGE_URL_LEGACY;

		case TEST:
			return MyUHCConstants.TEST_URL_LEGACY;

		default:
			return null;
		}
	}
	
	// getSecondaryPageURL

	// ------------------------------------------------------------------------
	// Window Handles
	// ------------------------------------------------------------------------

	public static void switchWindows() {
		
		String currWindow = WebController.driver.getWindowHandle();

		Set<String> allWindows = WebController.driver.getWindowHandles();

		try {
			allWindows.remove(currWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String window : allWindows) {
			WebController.driver.switchTo().window(window);
		}

		WebController.driver.manage().window().maximize();

	}
	
	public static void switchFrames(String elementName, String elementVariant) {
		
		WebElement frame = getElement(elementName, elementVariant);
		
		try {
			ExplicitWait.WaitForElementToBeDisplayed(frame, 45);
		} catch (Exception e) {
			Assert.fail(elementName + " was not displayed");
		}
		WebController.driver.switchTo().frame(frame);

	}
	
	// ------------------------------------------------------------------------
	// URL Transformer
	// ------------------------------------------------------------------------
	public static class URLTransformer extends Transformer<String> {
		@Override
		public String transform(String currentPageName) {
			final String name = currentPageName.toLowerCase();
			if (_URLs.LINK_MAPPING.containsKey(name)) {
				return _URLs.LINK_MAPPING.get(name);
			}

			Assert.fail("The URL was not found Link Mapping or External Link Mapping for page " + name);
			return null;
		}
	}
	
}