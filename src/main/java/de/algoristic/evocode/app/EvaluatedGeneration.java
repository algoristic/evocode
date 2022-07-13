package de.algoristic.evocode.app;

import java.util.ArrayList;
import java.util.List;

public class EvaluatedGeneration {

	private final int generationNumber;
	private final List<EvaluatedIndividual> individuals;

	public EvaluatedGeneration(final int generationNumber) {
		this.generationNumber = generationNumber;
		individuals = new ArrayList<>();
	}

	public int getGenerationNumber() {
		return generationNumber;
	}

	public void add(EvaluatedIndividual individual) {
		individuals.add(individual);
	}

	public List<EvaluatedIndividual> getIndividuals() {
		return individuals;
	}

	public EvaluatedIndividual get(int id) {
		EvaluatedIndividual result = null;
		for (EvaluatedIndividual individual : individuals) {
			if (id == individual.getIndividualNumber()) {
				result = individual;
				break;
			}
		}
		return result;
	}
}
