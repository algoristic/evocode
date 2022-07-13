package de.algoristic.evocode.app.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.algoristic.evocode.app.conf.FilesystemContext;
import de.algoristic.evocode.app.io.files.GenerationCSV;
import de.algoristic.evocode.app.io.files.GenerationProperties;
import de.algoristic.evocode.app.io.files.IslandsCSV;
import de.algoristic.evocode.app.io.files.ProjectCSV;
import de.algoristic.evocode.genetic.strategy.IslandSettings;

public class EvocodeProtocol {

	private final FilesystemContext context;

	private EvocodeProtocol(final FilesystemContext context) {
		this.context = context;
	}

	public EvocodeProtocol() {
		this(new FilesystemContext());
	}

	public void writeProtocols(FieldData fieldData) {
		writeDefaultProtocols(fieldData);
		writeIslandProtocols(fieldData);
	}

	private void writeDefaultProtocols(FieldData fieldData) {
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

	private void writeIslandProtocols(FieldData fieldData) {
		IslandSettings settings = new IslandSettings();
		int generation = fieldData.getGenerationNumber();
		int numberOfIslands = settings.getNumberOfIslands();

		Stream<Double> performances = Stream.empty();
		for (int island = 0; island < numberOfIslands; island++) {
			List<Integer> individualsOnIsland = settings.getIndividualsOnIsland(island, generation);
			Performance performance = calcPerformance(fieldData, individualsOnIsland);
			performances = Stream.concat(performances, performance.streamResults());
		}
		IslandsCSV csv = context.getIslandsCSV();
		csv.writeIslandResults(generation, performances.collect(Collectors.toList()));
	}

	private Performance calcPerformance(FieldData fieldData, List<Integer> ids) {
		List<Double> values = new ArrayList<>();
		double min = Double.MAX_VALUE;
		double max = 0;
		double sum = 0;
		int count = 0;
		for (Entry<Integer, FitnessValue> entry : fieldData) {
			int individualId = entry.getKey();
			if (ids.contains(individualId)) {
				FitnessValue fitnessValue = entry.getValue();
				double fitness = fitnessValue.getValue();
				count++;
				sum += fitness;
				values.add(fitness);
				min = Math.min(min, fitness);
				max = Math.max(max, fitness);
			}
		}
		double avg = (sum / count);
		Collections.sort(values);
		double median = values.get(values.size() / 2);
		return new Performance(min, max, avg, median);
	}

	private class Performance {
		private final double min;
		private final double max;
		private final double avg;
		private final double median;

		Performance(final double min, final double max, final double avg, final double median) {
			this.min = min;
			this.max = max;
			this.avg = avg;
			this.median = median;
		}

		Stream<Double> streamResults() {
			return Stream.of(min, max, avg, median);
		}
	}
}
