package de.algoristic.evocode.app;

import java.util.List;

public class Individuals {

	private final List<Individual> inner;

	public Individuals(List<Individual> individuals) {
		inner = individuals;
	}

	public List<Individual> getList() {
		return inner;
	}
}
