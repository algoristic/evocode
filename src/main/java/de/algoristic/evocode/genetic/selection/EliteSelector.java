package de.algoristic.evocode.genetic.selection;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.algoristic.evocode.app.EvaluatedIndividual;
import de.algoristic.evocode.app.io.Logger;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.breeding.BreedingPipeline;
import de.algoristic.evocode.genetic.breeding.NoAlterationPipeline;
import de.algoristic.evocode.genetic.strategy.Population;

public class EliteSelector extends AbstractSelector {

	private Logger logger = Logger.getLogger(getClass());

	public EliteSelector(final int out) {
		super(out);
	}

	@Override
	public BreedingPipeline getOffsprings(Population population) {
		logger.write("Select elite (out=" + out + "):");
		List<EvaluatedIndividual> individuals = population.getIndividuals();
		individuals = sortDescending(individuals);
		List<Genome> elite = new ArrayList<>();
		for(int i = 0; i < out; i++) {
			EvaluatedIndividual individual = individuals.get(i);
			elite.add(individual.getGenome());
			
			int number = individual.getIndividualNumber();
			double fitness = individual.getFitness();
			logger.write("   Selected " + (i + 1)
				+ ": Individual " + StringUtils.leftPad(String.valueOf(number), 4)
				+ " Fitness: " + StringUtils.leftPad(String.valueOf(fitness), 10));
		}
		return new NoAlterationPipeline(elite);
	}
}
