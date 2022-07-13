package de.algoristic.evocode.genetic.selection;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.algoristic.evocode.app.EvaluatedIndividual;

public abstract class AbstractSelector implements Selector {

	protected final int out;

	public AbstractSelector(final int out) {
		this.out = out;
	}

	protected static List<EvaluatedIndividual> sortDescending(List<EvaluatedIndividual> individuals) {
		individuals = copy(individuals);
		Collections.sort(individuals, new Comparator<EvaluatedIndividual>() {
			@Override
			public int compare(EvaluatedIndividual i1, EvaluatedIndividual i2) {
				return Double.compare(i2.getFitness(), i1.getFitness());
			}
		});
		return individuals;
	}

	protected static List<EvaluatedIndividual> revert(List<EvaluatedIndividual> individuals) {
		individuals = copy(individuals);
		Collections.reverse(individuals);
		return individuals;
	}

	protected static List<EvaluatedIndividual> copy(List<EvaluatedIndividual> individuals) {
		return individuals.stream().collect(Collectors.toList());
	}
}
