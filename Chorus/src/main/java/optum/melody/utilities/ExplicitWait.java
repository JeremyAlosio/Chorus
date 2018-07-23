package optum.melody.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import optum.melody.controller.WebController;

public class ExplicitWait {

	public static void WaitForURLToEqual(String url, int seconds) {
		while (seconds > 0) {
			if (WebController.driver.getCurrentUrl().equals(url)) {
				if (MaintainedLocation(url)) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}
	
	public static void WaitForURLToNotContain(String url, int seconds) {
		while (seconds > 0) {
			if (!WebController.driver.getCurrentUrl().contains(url)) {
				if (MaintainedNotLocationContains(url)) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForURLToContain(String url, int seconds) {
		while (seconds > 0) {
			if (WebController.driver.getCurrentUrl().contains(url)) {
				if (MaintainedLocationContains(url)) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForElementToBeInvisible(WebElement element, int seconds) {
		CoreFunctions.waitForPageLoad();

		while (seconds > 0) {
			if (!element.isDisplayed()) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForElementToBeInvisible(By by, int seconds) {
		CoreFunctions.waitForPageLoad();

		WebElement element = WebController.driver.findElement(by);
		while (seconds > 0) {
			if (!element.isDisplayed()) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForElementToBeClickable(By by, int seconds) {
		while (seconds > 0) {
			WebElement element = WebController.driver.findElement(by);

			if (element.isDisplayed() && element.isEnabled()) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForElementToBeClickable(WebElement element, int seconds) {
		while (seconds > 0) {
			try {
				if (element.isDisplayed() && element.isEnabled()) {
					break;
				}
			} catch (StaleElementReferenceException e) {
				throw new StaleElementReferenceException(e.getMessage());
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForAllElementsToBeClickable(List<WebElement> elements, int seconds) {
		Boolean currentStatus = true;

		while (seconds > 0) {
			for (WebElement element : elements) {
				if (!element.isDisplayed() || !element.isEnabled()) {
					currentStatus = false;
				}
			}

			if (currentStatus == true) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
			currentStatus = true;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForAllElementsToBeClickable(By by, int seconds) {
		List<WebElement> elements = WebController.driver.findElements(by);

		Boolean currentStatus = true;

		while (seconds > 0) {
			for (WebElement element : elements) {
				if (!element.isDisplayed() || !element.isEnabled()) {
					currentStatus = false;
				}
			}

			if (currentStatus == true) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
			currentStatus = true;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForAllElementsToBeDisplayed(List<WebElement> elements, int seconds) {
		Boolean currentStatus = true;

		while (seconds > 0) {
			for (WebElement element : elements) {
				if (!element.isDisplayed()) {
					currentStatus = false;
				}
			}

			if (currentStatus == true) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
			currentStatus = true;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForAllElementsToBeDisplayed(By by, int seconds) {
		List<WebElement> elements = WebController.driver.findElements(by);

		Boolean currentStatus = true;

		while (seconds > 0) {
			for (WebElement element : elements) {
				if (!element.isDisplayed()) {
					currentStatus = false;
				}
			}

			if (currentStatus == true) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
			currentStatus = true;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForElementToBeDisplayed(By by, int seconds) {

		WebElement element;
		while (seconds > 0) {

			try {
				element = WebController.driver.findElement(by);
			} catch (Exception e) {
				element = null;
			}
			if (element != null) {
				if (element.isDisplayed()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	public static void WaitForElementToBeDisplayed(WebElement element, int seconds) {
		while (seconds > 0) {
			if (element.isDisplayed()) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		if (seconds == 0) {
			throw new TimeoutException();
		}
	}

	private static boolean MaintainedLocation(String url) {
		int seconds = 2;
		while (seconds > 0) {
			if (!WebController.driver.getCurrentUrl().equals(url)) {
				return false;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		return true;
	}

	private static boolean MaintainedLocationContains(String url) {
		int seconds = 5;
		while (seconds > 0) {
			if (!WebController.driver.getCurrentUrl().contains(url)) {
				return false;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		return true;
	}

	private static boolean MaintainedNotLocationContains(String url) {
		int seconds = 3;
		while (seconds > 0) {
			if (WebController.driver.getCurrentUrl().contains(url)) {
				return false;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
		}

		return true;
	}
}
