package de.algoristic.evocode.genetic.selection;

import java.util.ArrayList;
import java.util.List;

import de.algoristic.evocode.app.EvaluatedIndividual;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.util.rnd.Dice;
import de.algoristic.evocode.util.rnd.WeightedValue;

public class RankSelector extends MatingSelector {

	public RankSelector(int out) {
		super(out);
	}

	@Override
	protected int selectIndividual(List<EvaluatedIndividual> individuals) {
		individuals = sortDescending(individuals);
		individuals = revert(individuals);
		List<RankWeightedIndividual> weightedIndividuals = new ArrayList<>();
		EvaluatedIndividual first = individuals.get(0);
		int rank = 1;
		int same = 1;
		double lastFitness = first.getFitness();
		weightedIndividuals.add(new RankWeightedIndividual(rank, first));
		for (int i = 1; i < individuals.size(); i++) {
			EvaluatedIndividual individual = individuals.get(i);
			double fitness = individual.getFitness();
			if (fitness > lastFitness) {
				rank += same;
				same = 1;
				lastFitness = fitness;
			}
			else same++;
			weightedIndividuals.add(new RankWeightedIndividual(rank, individual));
		}
		Dice<Individual> dice = weightedIndividuals.stream().collect(Dice.toLoadedDice());
		Individual selected = dice.roll();
		return selected.getIndividualNumber();
	}

	private class RankWeightedIndividual implements WeightedValue<Individual> {

		private final int rank;
		private final Individual individual;

		public RankWeightedIndividual(int rank, Individual individual) {
			this.rank = rank;
			this.individual = individual;
		}

		@Override
		public double getWeight() {
			return rank;
		}

		@Override
		public Individual getValue() {
			return individual;
		}

	}
}
