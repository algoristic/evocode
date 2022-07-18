package de.algoristic.evocode.app.io.files;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import de.algoristic.evocode.app.io.EvocodeProtocol.Performance;
import de.algoristic.evocode.genetic.strategy.IslandSettings;

public class IslandsCSV extends RunningFile {

	private final IslandSettings settings;

	public IslandsCSV(File file) {
		super(file);
		settings = new IslandSettings();
	}

	@Override
	public boolean createIfNotExists() throws IOException {
		boolean created = super.createIfNotExists();
		if(created) {
			int numberOfIslands = settings.getNumberOfIslands();
			StringBuffer lineBuffer = new StringBuffer("generation;");
			for(PerformanceProperty property : PerformanceProperty.values()) {
				for(int island = 0; island < numberOfIslands; island++) {
					String fn = property.getName();
					lineBuffer.append(fn).append("(").append(island).append(");");
				}
			}
			int end = (lineBuffer.length() - 1);
			String head = lineBuffer.substring(0, end);
			writeLine(head);
		}
		return created;
	}

	public void writeIslandResults(int generation, List<Performance> results) {
		int numberOfIslands = settings.getNumberOfIslands();
		if(numberOfIslands != results.size()) throw new RuntimeException("FATAL ERROR");
		StringBuffer lineBuffer = new StringBuffer(generation + ";");
		for(PerformanceProperty property : PerformanceProperty.values()) {
			for(Performance performance : results) {
				double value = property.getValue(performance);
				lineBuffer.append(value).append(";");
			}
		}
		int end = (lineBuffer.length() - 1);
		String line = lineBuffer.substring(0, end).replace(".", ",");		
		writeLine(line);
	}

	private enum PerformanceProperty {
		TIME("time", p -> (double) p.getTime()),
		MIN("min", p -> p.getMin()),
		MAX("max", p -> p.getMax()),
		AVG("avg", p -> p.getAvg()),
		MEDIAN("median", p -> p.getMedian());

		private String name;
		private Function<Performance, Double> valueExtractor;

		private PerformanceProperty(String name, Function<Performance, Double> valueExtractor) {
			this.name = name;
			this.valueExtractor = valueExtractor;
		}

		public String getName() {
			return name;
		}

		public double getValue(Performance performance) {
			return valueExtractor.apply(performance);
		}
	}
}
