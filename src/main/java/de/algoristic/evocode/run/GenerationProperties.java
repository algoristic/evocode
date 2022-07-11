package de.algoristic.evocode.run;

import java.io.File;

import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.util.Pattern;

public final class GenerationProperties extends RunningFile {

	public GenerationProperties(final File file) {
		super(file);
	}

	public void writeGenome(int individualNumber, Genome genome) {
		String serializedGenome = genome.serialize();
		Pattern linePattern = new Pattern("individual.[num].genome=[genome]\n")
			.addVariable("[num]", individualNumber)
			.addVariable("[genome]", serializedGenome);
		String line = linePattern.compile();
		writeLine(line);
	}

	public void writeFitness(int individual, double fitness) {
		Pattern linePattern = new Pattern("individual.[num].fitness=[fitness]\n")
			.addVariable("[num]", individual)
			.addVariable("[fitness]", fitness);
		String line = linePattern.compile();
		writeLine(line);
	}
}
