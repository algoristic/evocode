package de.algoristic.evocode.genetic;

import de.algoristic.evocode.app.Individual;

public class GenomeExpressor {

	private final Individual individual;

	public GenomeExpressor(final Individual individual) {
		this.individual = individual;
	}

	public Phaenotype expressGenome() {
		final int generation = individual.getGenerationNumber();
		final GenomeTranscriptor transkriptor = new GenomeTranscriptor(
			generation,
			individual.getIndividualNumber());
		final Genome genome = individual.getGenome();
		final Genotype genotype = transkriptor.transcribe(genome);
		final GenomeTranslator translator = new GenomeTranslator();
		final Phaenotype phaenotype = translator.translate(genotype, generation);
		return phaenotype;
	}
}
