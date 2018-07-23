package optum.melody.setup;

import org.openqa.selenium.remote.DesiredCapabilities;

public class Capabilities {

	// Get more mobile devices and settings
	// https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/

	private static DesiredCapabilities caps;

	public Capabilities() {
		caps.setCapability("maxDuration", 3600);
		caps.setCapability("name", String.format("Build #%s - %s", System.getenv("BUILD_NUMBER"), System.getenv("JOB_BASE_NAME")));
	}

	public static DesiredCapabilities setDesktopCapabilities(String browser, String browserVersion) {

		caps.setCapability("platform", RunInformation.getSauceDesktopPlatform());
		caps.setCapability("browserName", RunInformation.getBrowser());
		caps.setCapability("version", RunInformation.getBrowserVersion());
		caps.setCapability("screenResolution", "1920x1200");

		if (browser.equals("safari"))
			caps.setCapability("screenResolution", "1920x1440");

		return caps;
	}

	public static DesiredCapabilities getAndroidCapabilities() {

		caps = DesiredCapabilities.android();
		caps.setCapability("appiumVersion", "1.5.3");
		caps.setCapability("deviceName", "Android Emulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("browserName", "Browser");
		caps.setCapability("platformVersion", "5.1");
		caps.setCapability("platformName", "Android");

		return caps;
	}

	public static DesiredCapabilities getIOSCapabilities() {

		caps = DesiredCapabilities.iphone();
		caps.setCapability("appiumVersion", "1.6.3");
		caps.setCapability("deviceName", "iPhone 7 Plus Simulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("platformVersion", "10.0");
		caps.setCapability("platformName", "iOS");
		caps.setCapability("browserName", "Safari");

		return caps;
	}

	public static DesiredCapabilities getIPADCapabilities() {

		caps = DesiredCapabilities.iphone();
		caps.setCapability("appiumVersion", "1.6.3");
		caps.setCapability("deviceName", "iPad Simulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("platformVersion", "10.0");
		caps.setCapability("platformName", "iOS");
		caps.setCapability("browserName", "Safari");

		return caps;
	}

	public static DesiredCapabilities getAndroidTabletCapabilities() {

		caps = DesiredCapabilities.android();
		caps.setCapability("appiumVersion", "1.5.3");
		caps.setCapability("deviceName", "Amazon Kindle Fire HD 8.9 GoogleAPI Emulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("browserName", "Browser");
		caps.setCapability("platformVersion", "4.4");
		caps.setCapability("platformName", "Android");

		return caps;
	}
}