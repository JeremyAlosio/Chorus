package optum.melody.utilities.selectors;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

public class SelectorContainer {

	private By defaultBy;
	private boolean isMultiSelector = false;

	private Map<String, By> selectorVariants = new HashMap<String, By>();

	public SelectorContainer(By singleSelector) {
		defaultBy = singleSelector;
	}

	public SelectorContainer(Map<String, By> varients) {
		selectorVariants = varients;
		isMultiSelector = true;
	}

	public By getDefault() {
		return (isMultiSelector) ? selectorVariants.get("default") : defaultBy;
	}

	public By getVariant(String variantName) {
		return selectorVariants.get(variantName.toLowerCase());
	}

	public static void addToSCMap(Map<String, SelectorContainer> map, String name, By selector) {
		map.put(name.toLowerCase(), new SelectorContainer(selector));
	}

	public static void addToSCMap(Map<String, SelectorContainer> map, String name, MultiSelector... multiSelectors) {

		Map<String, By> subMap = new HashMap<>();

		for (MultiSelector selector : multiSelectors) {
			subMap.put(selector.getName().toLowerCase(), selector.getSelector());
		}

		map.put(name.toLowerCase(), new SelectorContainer(subMap));
	}

}