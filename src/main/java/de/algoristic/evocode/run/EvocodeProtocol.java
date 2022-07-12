package de.algoristic.evocode.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import de.algoristic.evocode.FitnessValue;
import de.algoristic.evocode.context.FilesystemContext;

public class EvocodeProtocol {

	private final FilesystemContext context;

	private EvocodeProtocol(final FilesystemContext context) {
		this.context = context;
	}

	public EvocodeProtocol() {
		this(new FilesystemContext());
	}

	public void writeProtocols(FieldData fieldData) {
		// write fitness to generation.properties
		int generation = fieldData.getGenerationNumber();
		GenerationProperties properties = context.getGenerationProperties(generation);
		GenerationCSV generationCSV = context.getGenerationCSV(generation);
		ProjectCSV projectCSV = context.getProjectCSV();

		List<Double> values = new ArrayList<>();
		double min = Double.MAX_VALUE;
		double max = 0;
		double sum = 0;
		int count = 0;
		for (Entry<Integer, FitnessValue> entry : fieldData) {
			FitnessValue fitnessValue = entry.getValue();
			double fitness = fitnessValue.getValue();
			count++;
			sum += fitness;
			values.add(fitness);
			min = Math.min(min, fitness);
			max = Math.max(max, fitness);
			int individual = entry.getKey();
			properties.writeFitness(individual, fitness);
			generationCSV.writeFitness(individual, fitnessValue);
		}
		double avg = (sum / count);
		Collections.sort(values);
		double median = values.get(values.size() / 2);
		projectCSV.writeLine(generation, min, max, avg, median);
	}
}
