package de.algoristic.evocode.app;

public class EvaluatedIndividual extends Individual {

	private final double fitness;

	public EvaluatedIndividual(final Individual base, final double fitness) {
		super(base);
		this.fitness = fitness;
	}

	public double getFitness() {
		return fitness;
	}

	public Individual getIndividual() {
		return this;
	}

	@Override
	public String toString() {
		return ("[" + fitness + "]");
	}
}
