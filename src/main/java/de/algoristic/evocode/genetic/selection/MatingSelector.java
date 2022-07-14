package de.algoristic.evocode.genetic.selection;

import java.util.ArrayList;
import java.util.List;

import de.algoristic.evocode.app.EvaluatedIndividual;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.breeding.BreedingPipeline;
import de.algoristic.evocode.genetic.breeding.DefaultBreedingPipeline;
import de.algoristic.evocode.genetic.strategy.Population;

public abstract class MatingSelector extends AbstractSelector {

	public MatingSelector(int out) {
		super(out);
	}

	@Override
	public BreedingPipeline getOffsprings(Population population) {
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
		Individual firstParent = filter(firstParentId, individuals);
		int secondParentId = selectIndividual(individuals);
		Individual secondParent = filter(secondParentId, individuals);
		return new Parents(firstParent, secondParent); 
	}

	private Individual filter(int id, List<EvaluatedIndividual> individuals) {
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

	protected abstract int selectIndividual(List<EvaluatedIndividual> individuals);
}
