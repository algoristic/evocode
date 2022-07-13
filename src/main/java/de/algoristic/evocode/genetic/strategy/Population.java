package de.algoristic.evocode.genetic.strategy;

import java.util.List;

import de.algoristic.evocode.app.EvaluatedIndividual;

public class Population {

	private final List<EvaluatedIndividual> individuals;

	public Population(final List<EvaluatedIndividual> individuals) {
		this.individuals = individuals;
	}

	public List<EvaluatedIndividual> getIndividuals() {
		return individuals;
	}
}
