package de.algoristic.evocode.genetic.strategy;

import java.util.List;

import de.algoristic.evocode.app.EvaluatedGeneration;
import de.algoristic.evocode.app.EvaluatedIndividual;

public class Population {

	private final List<EvaluatedIndividual> individuals;

	public Population(final List<EvaluatedIndividual> individuals) {
		this.individuals = individuals;
	}

	public static Population ofWholeGeneration(final EvaluatedGeneration generation) {
		final List<EvaluatedIndividual> individuals = generation.getIndividuals();
		return new Population(individuals);
	}
}
