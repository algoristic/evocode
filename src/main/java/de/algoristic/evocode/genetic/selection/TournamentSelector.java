package de.algoristic.evocode.genetic.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.algoristic.evocode.app.EvaluatedIndividual;

public class TournamentSelector extends MatingSelector {

	private final int sampleSize;

	public TournamentSelector(final int out, final int sampleSize) {
		super(out);
		this.sampleSize = sampleSize;
	}

	@Override
	protected int selectIndividual(List<EvaluatedIndividual> individuals) {
		individuals = copy(individuals);
		List<EvaluatedIndividual> tournament = new ArrayList<>();
		for(int k = 0; k < sampleSize; k++) {
			int pos = (new Random()).nextInt(individuals.size());
			EvaluatedIndividual participant = individuals.remove(pos);
			tournament.add(participant);
		}
		tournament = sortDescending(tournament);
		return tournament.get(0).getIndividualNumber();
	}
}
