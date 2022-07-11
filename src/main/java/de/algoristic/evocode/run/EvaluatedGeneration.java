package de.algoristic.evocode.run;

import java.util.ArrayList;
import java.util.List;

public class EvaluatedGeneration {

	private final List<EvaluatedIndividual> individuals;

	public EvaluatedGeneration() {
		individuals = new ArrayList<>();
	}

	public void add(EvaluatedIndividual individual) {
		individuals.add(individual);
	}
}
