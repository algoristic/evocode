package de.algoristic.evocode.genetic;

import de.algoristic.evocode.FitnessValue;
import de.algoristic.evocode.run.FieldData;
import de.algoristic.evocode.run.Generation;
import de.algoristic.evocode.run.Individual;
import de.algoristic.evocode.run.Robocode;

public class Enviroment {

	private final Robocode robocode;
	
	public Enviroment() {
		this.robocode = Robocode.getInstance();
	}
	
	public FieldData test(final Generation generation) {
		final int generationNumber = generation.getGenerationNumber();
		final FieldData data = new FieldData(generationNumber);
		for(final Individual individual : generation) {
			final GenomeExpressor expressor = new GenomeExpressor(individual);
			final Phaenotype phaenotype = expressor.expressGenome();
			final FitnessFunction fitnessFunction = FitnessFunction.getFunctionFor(generationNumber);
			final FitnessValue fitness = robocode.eval(phaenotype, fitnessFunction);
			data.addValues(individual, fitness);
			robocode.cleanupBattlefield();
		}
		return data;
	}

}
