package optum.melody.utilities.selectors;

import java.util.HashMap;
import java.util.Map;

public class _Selectors {

	public static final Map<String, SelectorContainer> MAPPING = new HashMap<String, SelectorContainer>();

	static {
		MAPPING.putAll(LoginSelectors.getMapping());
	}

}