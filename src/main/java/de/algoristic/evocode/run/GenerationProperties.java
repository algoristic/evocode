package de.algoristic.evocode.run;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.util.LazyPropertyFacade;
import de.algoristic.evocode.util.Pattern;

public final class GenerationProperties extends RunningFile {

	private LazyPropertyFacade properties;

	public GenerationProperties(final File file) {
		super(file);
		properties = new LazyPropertyFacade(file);
	}

	public void writeGenome(int individualNumber, Genome genome) {
		String serializedGenome = genome.serialize();
		Pattern linePattern = new Pattern("individual.[num].genome=[genome]\n")
			.addVariable("[num]", individualNumber)
			.addVariable("[genome]", serializedGenome);
		String line = linePattern.compile();
		writeLine(line);
	}

	public void writeFitness(int individual, double fitness) {
		Pattern linePattern = new Pattern("individual.[num].fitness=[fitness]\n")
			.addVariable("[num]", individual)
			.addVariable("[fitness]", fitness);
		String line = linePattern.compile();
		writeLine(line);
	}

	public void writeIslandSpec(int island, String spec) {
		Pattern linePattern = new Pattern("island.[island].spec=[spec]")
			.addVariable("[island]", island)
			.addVariable("[spec]", spec);
		String line = linePattern.compile();
		writeLine(line);
	}

	public void writeIslandSpec(int id, List<Integer> specs) {
		String spec = specs.stream().map(String::valueOf).collect(Collectors.joining(","));
		writeIslandSpec(id, spec);
	}

	public String getGenome(int individual) {
		String key = new Pattern("individual.$indiv.genome")
			.addVariable("$indiv", individual)
			.compile();
		return properties.getProperty(key)
			.orElseThrow();
	}

	public double getFitness(int individual) {
		String key = new Pattern("individual.$indiv.fitness")
			.addVariable("$indiv", individual)
			.compile();
		return properties.getProperty(key)
			.map(Double::valueOf)
			.orElseThrow();
	}

	public List<Integer> getIslandSpec(int island) {
		String key = new Pattern("island.$island.spec")
			.addVariable("$island", island)
			.compile();
		return properties.getProperty(key)
			.map(specs -> specs.split(","))
			.stream()
			.flatMap(Arrays::stream)
			.map(String::trim)
			.map(Integer::valueOf)
			.collect(Collectors.toList());
	}
}
