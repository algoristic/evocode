package de.algoristic.evocode.genetic.selection;

import de.algoristic.evocode.genetic.strategy.Population;

public class TournamentSelector extends AbstractSelector {

	private final int sampleSize;

	public TournamentSelector(final int out, final int sampleSize) {
		super(out);
		this.sampleSize = sampleSize;
	}

	@Override
	public Selection getSelection(Population population) {
		// TODO Auto-generated method stub
		return null;
	}

}
