package optum.melody.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.saucerest.SauceREST;

import optum.melody.setup.AppConfiguration;
import optum.melody.setup.Capabilities;
import optum.melody.setup.RunInformation;

public class WebController {

	public static WebDriver driver;
	private static String sessionId;
	private static SauceREST saucerest;
	private static DesiredCapabilities caps;

	private static WebDriver getDriver(String browserName, String testName) throws MalformedURLException, URISyntaxException {

		RemoteWebDriver driver = null;

		if (AppConfiguration.isCLOUDCONNECT()) {

			if (RunInformation.isMobile()) {

				switch (RunInformation.getPlatform()) {

				case ANDROID:
					caps = Capabilities.getAndroidCapabilities();

				case ANDROID_TABLET:
					caps = Capabilities.getAndroidTabletCapabilities();

				case IOS:
					caps = Capabilities.getIOSCapabilities();

				case IPAD:
					caps = Capabilities.getIPADCapabilities();

				default:
					throw new RuntimeException("Platform selected is not mobile");

				}
			} else {
				caps = Capabilities.setDesktopCapabilities(RunInformation.getBrowser(), RunInformation.getBrowserVersion());
			}

			URI uri = new URI("http", AppConfiguration.getSAUCE_USERNAME() + ":" + AppConfiguration.getSAUCE_ACCESS_KEY(), AppConfiguration.getSAUCE_HOST(), AppConfiguration.getSAUCE_TUNNEL_PORT(), "/wd/hub", null, null);

			driver = new RemoteWebDriver(uri.toURL(), caps);
			sessionId = driver.getSessionId().toString();

		} else {

			switch (RunInformation.getBrowser()) {

			case "Firefox":
				String firefoxDriverLocation = System.getenv("AppData") + "\\Drivers\\geckodriver.exe";
				System.setProperty("webdriver.gecko.driver", firefoxDriverLocation);
				driver = new FirefoxDriver();
				break;

			case "Chrome":
				String chromeDriverLocation = System.getenv("AppData") + "\\Drivers\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
				driver = new ChromeDriver();
				break;

			case "Internet Explorer":
				throw new RuntimeException("Local Internet Explorer functionality is not enabled");

			case "Safari":
				throw new RuntimeException("Local Safari functionality is not enabled");

			case "microsoftedge":
				throw new RuntimeException("Local Microsoft Edge functionality is not enabled");

			default:
				throw new RuntimeException("No eligible Browser selected");
			}
		}

		return driver;
	}

	public static void launchBrowser(String testName) throws MalformedURLException, URISyntaxException {

		driver = getDriver(RunInformation.getBrowser(), testName);

		setImplicitWaits();

		if (!RunInformation.isMobile()) {
			driver.manage().window().maximize();
		}
	}

	public static void updateResults(boolean testResults) {
		if (AppConfiguration.isCLOUDCONNECT()) {
			if (saucerest == null) {
				saucerest = new SauceREST(AppConfiguration.getSAUCE_USERNAME(), AppConfiguration.getSAUCE_ACCESS_KEY());
			}
			Map<String, Object> updates = new HashMap<String, Object>();
			updates.put("passed", testResults);
			saucerest.updateJobInfo(sessionId, updates);
		}
	}

	public static void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	public static void setImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(AppConfiguration.getWaitTime(), TimeUnit.SECONDS);
	}

	public static void setImplicitWaits(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
}
