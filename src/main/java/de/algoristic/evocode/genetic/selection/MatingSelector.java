package de.algoristic.evocode.genetic.selection;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.algoristic.evocode.app.EvaluatedIndividual;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.app.io.Logger;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.breeding.BreedingPipeline;
import de.algoristic.evocode.genetic.breeding.DefaultBreedingPipeline;
import de.algoristic.evocode.genetic.strategy.Population;

public abstract class MatingSelector extends AbstractSelector {

	private Logger logger = Logger.getLogger(getClass());

	public MatingSelector(int out) {
		super(out);
	}

	@Override
	public BreedingPipeline getOffsprings(Population population) {
		logger.write("Select for mating (out=" + out + "):");
		List<EvaluatedIndividual> individuals = population.getIndividuals();
		int generation = population.getGeneration(); 
		List<Genome> offsprings = new ArrayList<>();
		for (int i = 0; i < out; i++) {
			Parents parents = selectParents(individuals);
			Genome offspring = parents.mate();
			offsprings.add(offspring);
		}
		return new DefaultBreedingPipeline(offsprings, generation);
	}

	private Parents selectParents(List<EvaluatedIndividual> individuals) {
		individuals = copy(individuals);
		int firstParentId = selectIndividual(individuals);
		EvaluatedIndividual firstParent = filter(firstParentId, individuals);
		int secondParentId = selectIndividual(individuals);
		EvaluatedIndividual secondParent = filter(secondParentId, individuals);
		
		logSelection(firstParent, secondParent);
		return new Parents(firstParent, secondParent); 
	}

	private EvaluatedIndividual filter(int id, List<EvaluatedIndividual> individuals) {
		int index = -1;
		for(int i = 0; i < individuals.size(); i++) {
			Individual individual = individuals.get(i);
			if(id == individual.getIndividualNumber()) {
				index = i;
				break;
			}
		}
		if(index == -1) throw new RuntimeException("Fatal: Selection algorithm does not work as planned: " + this);
		return individuals.remove(index);
	}

	private void logSelection(EvaluatedIndividual firstParent, EvaluatedIndividual secondParent) {
		String firstId = StringUtils.leftPad(String.valueOf(firstParent.getIndividualNumber()), 4);
		String firstFitness = StringUtils.leftPad(String.valueOf(firstParent.getFitness()), 10);
		String secondId = StringUtils.leftPad(String.valueOf(secondParent.getIndividualNumber()), 4);
		String secondFitness = StringUtils.leftPad(String.valueOf(secondParent.getFitness()), 10);
		logger.write("   Selected couple:");
		logger.write("      1. id=" + firstId + " fitness=" + firstFitness);
		logger.write("      2. id=" + secondId + " fitness=" + secondFitness);
	}

	protected abstract int selectIndividual(List<EvaluatedIndividual> individuals);
}
