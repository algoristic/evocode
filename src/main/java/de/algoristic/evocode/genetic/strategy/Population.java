package de.algoristic.evocode.genetic.strategy;

import java.util.List;

import de.algoristic.evocode.app.EvaluatedIndividual;

public class Population {

	private final List<EvaluatedIndividual> individuals;
	private final int generation;

	public Population(final List<EvaluatedIndividual> individuals, final int generation) {
		this.individuals = individuals;
		this.generation = generation;
	}

	public List<EvaluatedIndividual> getIndividuals() {
		return individuals;
	}

	public int getGeneration() {
		return generation;
	}
}
