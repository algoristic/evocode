package de.algoristic.evocode.app.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

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
		long totalTime = 0;
		int count = 0;
		for (Entry<Integer, FitnessValue> entry : fieldData) {
			FitnessValue fitnessValue = entry.getValue();
			double fitness = fitnessValue.getValue();
			long time = fitnessValue.getTimeInMillis();
			totalTime += time;
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
		totalTime /= 1000; // write time in seconds, not millis
		projectCSV.writeLine(generation, totalTime, min, max, avg, median);
	}

	private void writeIslandProtocols(FieldData fieldData) {
		IslandSettings settings = new IslandSettings();
		int generation = fieldData.getGenerationNumber();
		int numberOfIslands = settings.getNumberOfIslands();

		List<Performance> islandPerformances = new ArrayList<>();
		for (int island = 0; island < numberOfIslands; island++) {
			List<Integer> individualsOnIsland = settings.getIndividualsOnIsland(island, generation);
			Performance performance = calcPerformance(fieldData, island, individualsOnIsland);
			islandPerformances.add(performance);
		}
		IslandsCSV csv = context.getIslandsCSV();
		csv.writeIslandResults(generation, islandPerformances);
	}

	private Performance calcPerformance(FieldData fieldData, int island, List<Integer> ids) {
		List<Double> values = new ArrayList<>();
		long totalTime = 0;
		double min = Double.MAX_VALUE;
		double max = 0;
		double sum = 0;
		int count = 0;
		for (Entry<Integer, FitnessValue> entry : fieldData) {
			int individualId = entry.getKey();
			if (ids.contains(individualId)) {
				FitnessValue fitnessValue = entry.getValue();
				double fitness = fitnessValue.getValue();
				long time = fitnessValue.getTimeInMillis();
				totalTime += time;
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
		totalTime /= 1000;
		return new Performance(island, totalTime, min, max, avg, median);
	}

	public static class Performance {
		private final int island;
		private final long time;
		private final double min;
		private final double max;
		private final double avg;
		private final double median;

		Performance(final int island, final long time, final double min, final double max, final double avg,
				final double median) {
			this.island = island;
			this.time = time;
			this.min = min;
			this.max = max;
			this.avg = avg;
			this.median = median;
		}

		public int getIsland() {
			return island;
		}

		public long getTime() {
			return time;
		}

		public double getMin() {
			return min;
		}

		public double getMax() {
			return max;
		}

		public double getAvg() {
			return avg;
		}

		public double getMedian() {
			return median;
		}
	}
}
