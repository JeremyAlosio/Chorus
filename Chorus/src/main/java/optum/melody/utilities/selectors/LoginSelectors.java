package optum.melody.utilities.selectors;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

public class LoginSelectors {

	public static Map<String, SelectorContainer> getMapping() {

		final Map<String, SelectorContainer> mapping = new HashMap<>();

		SelectorContainer.addToSCMap(mapping, "RACF Login Input", By.name("racfid"));
		SelectorContainer.addToSCMap(mapping, "RACF Password Input", By.name("password"));
		SelectorContainer.addToSCMap(mapping, "RACF Continue Button", By.name("Continue"));

		SelectorContainer.addToSCMap(mapping, "Superuser Username Input", new MultiSelector("Default", By.name("hsidUserName")), new MultiSelector("Legacy", By.name("userName")));
		SelectorContainer.addToSCMap(mapping, "Superuser Login Button", new MultiSelector("Default", By.className("link_btnPrimary")), new MultiSelector("Mobile", By.className("button")), new MultiSelector("Legacy", By.cssSelector("#suLoginUserForm > table:nth-child(10) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > a:nth-child(1)")));

		SelectorContainer.addToSCMap(mapping, "HSID Login Username Input", By.id("hsid-username"));
		SelectorContainer.addToSCMap(mapping, "HSID Login Password Input", By.id("hsid-password"));
		SelectorContainer.addToSCMap(mapping, "HSID Login Sign In Button", By.id("hsid-submit"));

		SelectorContainer.addToSCMap(mapping, "HSID Security Question Text", By.id("authQuestiontextLabelId"));
		SelectorContainer.addToSCMap(mapping, "HSID Security Answer Input", By.className("authQuestionAnswerBox"));
		SelectorContainer.addToSCMap(mapping, "HSID Security Continue Button", By.id("continueSubmitButton"));
		
		
		return mapping;
	}

}