package de.algoristic.evocode.run;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.util.Pattern;

public class GenerationProperties {

	private final File file;

	public GenerationProperties(final File file) {
		this.file = file;
	}

	public void createNewFile() throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public void writeGenome(int individualNumber, Genome genome) {
		String serializedGenome = genome.serialize();
		Pattern linePattern = new Pattern("individual.[num].genome=[genome]\n")
			.addVariable("[num]", individualNumber)
			.addVariable("[genome]", serializedGenome);
		String line = linePattern.compile();
		Path target = file.toPath();
		try {
			Files.writeString(target, line, StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RuntimeException("Could not write to generation.properties", e);
		}
	}

}
