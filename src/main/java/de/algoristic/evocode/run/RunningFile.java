package de.algoristic.evocode.run;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class RunningFile {

	protected File file;

	public RunningFile(final File file) {
		this.file = file;
	}

	public boolean createIfNotExists() throws IOException {
		return file.createNewFile();
	}

	protected void writeLine(String line) {
		if(! line.endsWith("\n")) line = line.concat("\n");
		Path target = file.toPath();
		try {
			Files.writeString(target, line, StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RuntimeException("Could not write to generation.properties", e);
		}
	}
}
