package de.algoristic.evocode.app;

import java.util.List;

import de.algoristic.evocode.genetic.Genome;

public class Individuals {

	private final List<Genome> offspringDna;

	public Individuals(List<Genome> offspringDna) {
		this.offspringDna = offspringDna;
	}

	public List<Genome> getOffspringDna() {
		return offspringDna;
	}

	public int size() {
		return offspringDna.size();
	}
}
