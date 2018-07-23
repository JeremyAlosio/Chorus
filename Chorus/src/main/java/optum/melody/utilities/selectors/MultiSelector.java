package optum.melody.utilities.selectors;

import org.openqa.selenium.By;

public class MultiSelector {

	private By selector;
	private String name;

	public MultiSelector(String name, By selector) {
		this.name = name;
		this.selector = selector;
	}

	public String getName() {
		return name;
	}

	public By getSelector() {
		return selector;
	}

}