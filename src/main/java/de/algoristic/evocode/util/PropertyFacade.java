package de.algoristic.evocode.util;

import java.util.Optional;
import java.util.Properties;

public class PropertyFacade {

	public Optional<String> getProperty(String key) {
		if (!properties().containsKey(key)) return Optional.empty();
		String value = properties().getProperty(key);
		return Optional.of(value);
	}

	private Properties properties() {
		return System.getProperties();
	}
}
