package de.algoristic.evocode.genetic.selection;

import java.util.List;

import de.algoristic.evocode.app.EvaluatedIndividual;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.util.rnd.Dice;
import de.algoristic.evocode.util.rnd.WeightedValue;

public class RouletteWheelSelector extends MatingSelector {

	public RouletteWheelSelector(final int out) {
		super(out);
	}

	@Override
	protected int selectIndividual(List<EvaluatedIndividual> individuals) {
		Dice<Individual> dice = individuals.stream().map(FitnessWeightedIndividual::new).collect(Dice.toLoadedDice());
		Individual selected = dice.roll();
		return selected.getIndividualNumber();
	}

	private class FitnessWeightedIndividual implements WeightedValue<Individual> {

		private final EvaluatedIndividual individual;

		public FitnessWeightedIndividual(EvaluatedIndividual individual) {
			this.individual = individual;
		}

		@Override
		public double getWeight() {
			return individual.getFitness();
		}

		@Override
		public Individual getValue() {
			return individual;
		}
	}
}
