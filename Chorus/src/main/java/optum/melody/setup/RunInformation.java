package optum.melody.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunInformation {

	private static List<String> tags;

	private static String browser = null;
	private static String browserVersion = null;
	private static Boolean isMobile = null; // Boolean can null, boolean can't
	private static Platform platform = null;
	private static Environment runEnvironment = null;
	private static String userCredentials = "";

	public static void instantiateTags() {
		tags = (new ArrayList<String>(Arrays.asList(System.getProperty("cucumber.options").replaceAll("\\s+", "").toLowerCase().split("--tags~?"))));
		tags.removeAll(Arrays.asList("", null));
	}

	public static String getBrowser() {

		// If browser is set don't waste time
		if (browser != null)
			return browser;

		// If browser isn't set get it
		if (tags.contains("@chrome"))
			browser = "Chrome";

		else if (tags.contains("@ie"))
			browser = "Internet Explorer";

		else if (tags.contains("@firefox"))
			browser = "Firefox";

		else if (tags.contains("@safari"))
			browser = "Safari";

		else if (tags.contains("@edge"))
			browser = "microsoftedge";

		return browser;
	}

	public static String getBrowserVersion() {

		// If browserVersion is set don't waste time
		if (browserVersion != null)
			return browserVersion;

		// If browserVersion isn't set get it
		if (tags.contains("@version"))
			return tags.stream().filter(x -> x.contains("@version")).findFirst().get().split(":")[1];

		return browserVersion;
	}

	public static Boolean isMobile() {

		// If isMobile is set don't waste time
		if (isMobile != null)
			return isMobile;

		// If isMobile isn't set get it
		if (tags.contains("@mobile"))
			isMobile = true;
		else
			isMobile = false;

		return isMobile;
	}

	public enum Platform {
		ANDROID, ANDROID_TABLET, IOS, IPAD, OSX, WINDOWS7, WINDOWS8, WINDOWS10;
	}

	public static Platform getPlatform() {

		// If platform is set don't waste time
		if (platform != null)
			return platform;

		// If platform isn't set get it
		if (tags.contains("@android"))
			platform = Platform.ANDROID;

		else if (tags.contains("@android_tablet"))
			platform = Platform.ANDROID_TABLET;

		else if (tags.contains("@ios"))
			platform = Platform.IOS;

		else if (tags.contains("@ipad"))
			platform = Platform.IPAD;

		else if (tags.contains("@osx"))
			platform = Platform.OSX;

		else if (tags.contains("@windows8"))
			platform = Platform.WINDOWS8;

		else if (tags.contains("@windows10"))
			platform = Platform.WINDOWS10;

		else
			platform = Platform.WINDOWS7;

		return platform;
	}

	public static String getSauceDesktopPlatform() {

		switch (getPlatform()) {

		case OSX:
			return "macOS 10.13";

		case WINDOWS7:
			return "Windows 7";

		case WINDOWS8:
			return "Windows 8.1";

		case WINDOWS10:
			return "Windows 10";

		default:
			return null;

		}
	}

	public enum Environment {
		OFFLINE_PRODUCTION, PRODUCTION, STAGE, TEST
	}

	public static Environment getRunEnvironment() {

		// If runEnvironment is set don't waste time
		if (runEnvironment != null)
			return runEnvironment;

		if (tags.contains("@stage"))
			runEnvironment = Environment.STAGE;

		else if (tags.contains("@testenv"))
			runEnvironment = Environment.TEST;

		else if (tags.contains("@offlineprd"))
			runEnvironment = Environment.OFFLINE_PRODUCTION;

		else if (tags.contains("@production"))
			runEnvironment = Environment.PRODUCTION;

		return runEnvironment;
	}

	public static List<String> getTags() {
		return tags;
	}

	public static void setCredentials(String credentials) {
		userCredentials = credentials;
	}

	public static String getCredentials() {
		return userCredentials.toLowerCase();
	}

	public static boolean isCredentialsEmpty() {

		if (userCredentials == null || userCredentials.equals(""))
			return true;

		return false;
	}

}
