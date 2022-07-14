package de.algoristic.evocode.genetic.selection;

import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.genetic.Genome;

public class Parents {

	private final Individual firstParent;
	private final Individual sencondParent;

	public Parents(Individual firstParent, Individual sencondParent) {
		this.firstParent = firstParent;
		this.sencondParent = sencondParent;
	}

	public Individual getFirstParent() {
		return firstParent;
	}

	public Individual getSencondParent() {
		return sencondParent;
	}

	public Genome mate() {
		Genome firstSeed = firstParent.getGenome();
		Genome secondSeed = sencondParent.getGenome();
		return firstSeed.crossover(secondSeed);
	}
}
