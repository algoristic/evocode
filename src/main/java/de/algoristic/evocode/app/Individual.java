package de.algoristic.evocode.app;

import de.algoristic.evocode.genetic.Genome;

public class Individual {

	protected final int generationNumber;
	protected final int individualNumber;
	protected final Genome genome;

	public Individual(
		final int generationNumber,
		final int individualNumber,
		final Genome genome) {
		this.generationNumber = generationNumber;
		this.individualNumber = individualNumber;
		this.genome = genome;
	}

	public Individual(Individual individual) {
		this.generationNumber = individual.getGenerationNumber();
		this.individualNumber = individual.getIndividualNumber();
		this.genome = individual.getGenome();
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

	@Override
	public String toString() {
		return ("[i=" + individualNumber + "]");
	}
}
