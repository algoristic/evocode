package de.algoristic.evocode.app.io.files;

import java.io.File;
import java.io.IOException;

public class ProjectCSV extends RunningFile {

	public ProjectCSV(File file) {
		super(file);
	}

	@Override
	public boolean createIfNotExists() throws IOException {
		boolean created = super.createIfNotExists();
		if(created) writeLine("generation;time;min;max;avg;median");
		return created;
	}

	public void writeLine(int generation, long time, double min, double max, double avg, double median) {
		String line = new StringBuffer()
			.append(generation).append(";")
			.append(time).append(";")
			.append(min).append(";")
			.append(max).append(";")
			.append(avg).append(";")
			.append(median)
			.toString()
			.replace(".", ",");
		writeLine(line);
	}
}
