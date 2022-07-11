package de.algoristic.evocode.run;

import de.algoristic.evocode.util.SystemPropertyFacade;

public class EvolutionSettings {

	private final SystemPropertyFacade properties;

	public EvolutionSettings() {
		this(new SystemPropertyFacade());
	}
	
	private EvolutionSettings(SystemPropertyFacade properties) {
		this.properties = properties;
	}

	public int getGenerationSize(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].generation.size")
			.map(Integer::valueOf)
			.orElse(100);
	}

	public String getFitnessFunction(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].fitnessFunction")
			.orElseThrow();
	}

	public String getEnemies(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].enemies")
			.orElseThrow();
	}

	public int getBattlefieldWidth(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].battlefield.width")
			.map(Integer::valueOf)
			.orElse(800);
	}

	public int getBattlefieldHeight(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].battlefield.height")
			.map(Integer::valueOf)
			.orElse(600);
	}

	public int getBattleRounds(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].battleRounds")
			.map(Integer::valueOf)
			.orElse(10);
	}
}
