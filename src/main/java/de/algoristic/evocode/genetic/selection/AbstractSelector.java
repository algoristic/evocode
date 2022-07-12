package de.algoristic.evocode.genetic.selection;

public abstract class AbstractSelector implements Selector {

	protected final int out;

	public AbstractSelector(final int out) {
		this.out = out;
	}

}
