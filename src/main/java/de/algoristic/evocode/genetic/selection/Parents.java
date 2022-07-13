package de.algoristic.evocode.genetic.selection;

import de.algoristic.evocode.app.Individual;

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
}
