package de.algoristic.evocode.run;

import de.algoristic.evocode.genetic.Genome;

public class Individual {

	private final int generationNumber;
	private final int individualNumber;
	private final Genome genome;

	public Individual(
		final int generationNumber,
		final int individualNumber,
		final Genome genome) {
		this.generationNumber = generationNumber;
		this.individualNumber = individualNumber;
		this.genome = genome;
	}

	public Genome getGenome() {
		return genome;
	}

	public int getGenerationNumber() {
		return generationNumber;
	}

	public int getIndividualNumber() {
		return individualNumber;
	}
}
