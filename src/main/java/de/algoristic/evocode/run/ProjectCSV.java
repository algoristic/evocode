package de.algoristic.evocode.run;

import java.io.File;
import java.io.IOException;

import de.algoristic.evocode.util.Pattern;

public class ProjectCSV extends RunningFile {

	public ProjectCSV(File file) {
		super(file);
	}

	@Override
	public void createIfNotExists() throws IOException {
		super.createIfNotExists();
		writeLine("generation,min,max,avg,median");
	}

	public void writeLine(int generation, double min, double max, double avg, double median) {
		String line = new Pattern("$generation,$min,$max,$avg,$median")
			.addVariable("$generation", generation)
			.addVariable("$min", min)
			.addVariable("$max", max)
			.addVariable("$avg", avg)
			.addVariable("$median", median)
			.compile();
		writeLine(line);
	}
}
