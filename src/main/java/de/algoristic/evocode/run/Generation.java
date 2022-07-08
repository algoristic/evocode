package de.algoristic.evocode.run;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	@Override
	public Iterator<Individual> iterator() {
		return individuals.iterator();
	}

	public int getGenerationNumber() {
		return generationNumber;
	}
}
