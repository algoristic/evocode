package de.algoristic.evocode.run;

public class EvaluatedIndividual {

	private final Individual base;
	private final double fitness;

	public EvaluatedIndividual(final Individual base, final double fitness) {
		this.base = base;
		this.fitness = fitness;
	}

	public int getIndividualNumber() {
		return base.getIndividualNumber();
	}
}
