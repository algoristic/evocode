package de.algoristic.evocode.genetic;

import java.io.File;

import de.algoristic.evocode.run.FieldData;
import de.algoristic.evocode.run.Generation;
import de.algoristic.evocode.run.Individual;
import de.algoristic.evocode.run.Robocode;

public class Enviroment {

	private final Robocode robocode;
	
	public Enviroment() {
		this.robocode = Robocode.getInstance();
	}
	
	public FieldData test(final Generation generation) {
		final int generationNumber = generation.getGenerationNumber();
		for(Individual individual : generation) {
			final int individualNumber = individual.getIndividualNumber();
			final GenomeTranscriptor transkriptor = new GenomeTranscriptor(generationNumber, individualNumber);
			final Genome genome = individual.getGenome();
			final Genotype genotype = transkriptor.transcribe(genome);
			final GenomeTranslator translator = new GenomeTranslator();
			final Phaenotype phaenotype = translator.translate(genotype);
		}
		return null;
	}

}
