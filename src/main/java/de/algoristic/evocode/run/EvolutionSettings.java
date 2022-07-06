package de.algoristic.evocode.run;

import de.algoristic.evocode.util.PropertyFacade;

public class EvolutionSettings {

	private final PropertyFacade properties;

	public EvolutionSettings() {
		this(new PropertyFacade());
	}
	
	private EvolutionSettings(PropertyFacade properties) {
		this.properties = properties;
	}

	public int getGenerationSize(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].generation.size")
			.map(Integer::valueOf)
			.orElse(100);
	}
}
