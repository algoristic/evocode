package de.algoristic.evocode.app.conf;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.algoristic.evocode.app.periphery.SystemPropertyFacade;

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

	public List<String> getSelectorSpecs(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].selectors")
			.map(specs -> specs.split(","))
			.stream()
			.flatMap(Arrays::stream)
			.map(String::trim)
			.collect(Collectors.toList());
	}

	public int getSelectorOutput(String selector, int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].selector." + selector + ".out")
			.map(Integer::valueOf)
			.orElseThrow();
	}

	public int getTournamentSampleSize(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].selector.tournament.sample_size")
			.map(Integer::valueOf)
			.orElse(10);
	}

	public List<String> getMigrationSpecs(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].islands.migration")
			.map(specs -> specs.split(","))
			.stream()
			.flatMap(Arrays::stream)
			.map(String::trim)
			.collect(Collectors.toList());
	}

	public int getMigrationEpoch(int generation, String spec) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].islands.migration." + spec + ".epoch")
			.map(Integer::valueOf)
			.orElse(1);
	}

	public double getMigrationChance(int generation, String spec) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].islands.migration." + spec + ".chance")
			.map(Double::valueOf)
			.orElse(.0d);
	}

	public List<String> getMutatorSpecs(int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].mutators")
			.map(specs -> specs.split(","))
			.stream()
			.flatMap(Arrays::stream)
			.map(String::trim)
			.collect(Collectors.toList());
	}

	public double getMutationRate(String mutator, int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.gen.[].mutator." + mutator + ".mutationRate")
			.map(Double::valueOf)
			.orElseThrow();
	}

	public List<Double> getValueRanges(String action, int generation) {
		return properties.forGeneration(generation)
			.getProperty("evo.genome.nn.[]." + action + ".ranges")
			.map(specs -> specs.split(","))
			.stream()
			.flatMap(Arrays::stream)
			.map(String::trim)
			.map(Double::valueOf)
			.collect(Collectors.toList());
	}
}
