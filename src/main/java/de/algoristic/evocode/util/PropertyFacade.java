package de.algoristic.evocode.util;

import java.util.Optional;
import java.util.Properties;

public class PropertyFacade {

	public Optional<String> getProperty(String key) {
		if (!properties().containsKey(key)) return Optional.empty();
		String value = properties().getProperty(key);
		return Optional.of(value);
	}

	public GenerationPropertySupplier forGeneration(int generation) {
		return new GenerationPropertySupplier(generation); 
	}
	
	public static class GenerationPropertySupplier {
		
		private final int generation;

		public GenerationPropertySupplier(final int generation) {
			this.generation = generation;
		}

		public Optional<String> getProperty(String key) {
			for(int i = generation; i >= 0; i--) {
				String replacement = String.valueOf(generation);
				String composedKey = key.replace("[]", replacement);
				if(properties().containsKey(composedKey)) {
					String value = properties().getProperty(composedKey);
					return Optional.of(value);
				}
			}
			return Optional.empty();
		}
	}

	static Properties properties() {
		return System.getProperties();
	}
}
