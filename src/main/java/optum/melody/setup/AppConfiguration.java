package optum.melody.setup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfiguration {

	private static String APPURL;
	private static String APPURLSTG;
	private static String APPURLLEGACY;
	private static String APPURLPRD;

	private static boolean CLOUDCONNECT;

	private static String SAUCE_USERNAME;
	private static String SAUCE_ACCESS_KEY;
	private static String SAUCE_HOST;
	private static int SAUCE_TUNNEL_PORT;

	private static int waitTime;

	public static void instantiate(String filePath) {

		if (CLOUDCONNECT) {
			SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
			SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
			SAUCE_HOST = System.getenv("SELENIUM_HOST");
			SAUCE_TUNNEL_PORT = Integer.parseInt(System.getenv("SELENIUM_PORT"));
		}

		final Properties props = new Properties();

		try (InputStream in = AppConfiguration.class.getClassLoader().getResourceAsStream(filePath)) {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		APPURL = props.getProperty("APPURL");
		APPURLSTG = props.getProperty("APPURL_STG");
		APPURLLEGACY = props.getProperty("APPURL_LEGACY");
		APPURLPRD = props.getProperty("APPURL_PRODUCTION");
		
		CLOUDCONNECT = Boolean.parseBoolean(props.getProperty("CLOUDCONNECT"));

		waitTime = Integer.parseInt(props.getProperty("WAIT_TIME"));

	}

	public static String getAPPURL() {
		return APPURL;
	}

	public static String getAPPURLSTG() {
		return APPURLSTG;
	}

	public static String getAPPURLLEGACY() {
		return APPURLLEGACY;
	}

	public static String getAPPURLPRD() {
		return APPURLPRD;
	}

	public static boolean isCLOUDCONNECT() {
		return CLOUDCONNECT;
	}

	public static String getSAUCE_USERNAME() {
		return SAUCE_USERNAME;
	}

	public static String getSAUCE_ACCESS_KEY() {
		return SAUCE_ACCESS_KEY;
	}

	public static String getSAUCE_HOST() {
		return SAUCE_HOST;
	}

	public static int getSAUCE_TUNNEL_PORT() {
		return SAUCE_TUNNEL_PORT;
	}

	public static int getWaitTime() {
		return waitTime;
	}

}