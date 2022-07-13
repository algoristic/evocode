package de.algoristic.evocode.app.io.files;

import java.io.File;
import java.io.IOException;

import de.algoristic.evocode.util.Pattern;

public class ProjectCSV extends RunningFile {

	public ProjectCSV(File file) {
		super(file);
	}

	@Override
	public boolean createIfNotExists() throws IOException {
		boolean created = super.createIfNotExists();
		if(created) writeLine("generation;min;max;avg;median");
		return created;
	}

	public void writeLine(int generation, double min, double max, double avg, double median) {
		String line = new Pattern("$generation;$min;$max;$avg;$median")
			.addVariable("$generation", generation)
			.addVariable("$min", min)
			.addVariable("$max", max)
			.addVariable("$avg", avg)
			.addVariable("$median", median)
			.compile()
			.replace(".", ",");
		writeLine(line);
	}
}
