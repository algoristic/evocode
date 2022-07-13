package de.algoristic.evocode.genetic.selection;

import java.util.ArrayList;
import java.util.List;

import de.algoristic.evocode.app.EvaluatedIndividual;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.breeding.BreedingPipeline;
import de.algoristic.evocode.genetic.breeding.NoAlterationPipeline;
import de.algoristic.evocode.genetic.strategy.Population;

public class EliteSelector extends AbstractSelector {

	public EliteSelector(final int out) {
		super(out);
	}

	@Override
	public BreedingPipeline getOffsprings(Population population) {
		List<EvaluatedIndividual> individuals = population.getIndividuals();
		individuals = sortDescending(individuals);
		List<Genome> elite = new ArrayList<>();
		for(int i = 0; i < out; i++) {
			Individual individual = individuals.get(i);
			elite.add(individual.getGenome());
		}
		return new NoAlterationPipeline(elite);
	}
}
