package de.algoristic.evocode.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.algoristic.evocode.genetic.Genome;

public class Generation implements Iterable<Individual> {

	private final int generationNumber;
	private final List<Individual> individuals = new ArrayList<>();

	public Generation(final int generationNumber) {
		this.generationNumber = generationNumber;
	}

	public void add(Individual individual) {
		individuals.add(individual);
	}

	public void add(List<Individual> individuals) {
		this.individuals.addAll(individuals);
	}

	public void add(Individuals individuals) {
		for(Genome genome : individuals.getOffspringDna()) {
			int id = this.individuals.size();
			Individual individual = new Individual(generationNumber, id, genome);
			add(individual);
		}
	}

	@Override
	public Iterator<Individual> iterator() {
		return individuals.iterator();
	}

	public int getGenerationNumber() {
		return generationNumber;
	}

	public int size() {
		return individuals.size();
	}
}
