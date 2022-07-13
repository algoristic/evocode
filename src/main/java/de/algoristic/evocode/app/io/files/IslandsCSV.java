package de.algoristic.evocode.app.io.files;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
			for(int island = 0; island < numberOfIslands; island++) {
				for(String fn : Arrays.asList("min", "max", "avg", "median")) {
					lineBuffer.append(fn).append("(").append(island).append(");");
				}
			}
			int end = (lineBuffer.length() - 1);
			String head = lineBuffer.substring(0, end);
			writeLine(head);
		}
		return created;
	}

	public void writeIslandResults(int generation, List<Double> results) {
		if(settings.getNumberOfIslands() > results.size()) throw new RuntimeException("FATAL ERROR");
		String line = (generation + ";");
		line += results.stream().map(String::valueOf).collect(Collectors.joining(";")).replace(".", ",");
		writeLine(line);
	}
}
