package de.algoristic.evocode.genetic.strategy;

public class Island {

	private final Population population;

	public Island(final Population population) {
		this.population = population;
	}

	public Population getPopulation() {
		return population;
	}
}
