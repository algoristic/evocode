package de.algoristic.evocode.app;

import java.io.IOException;

import de.algoristic.evocode.app.io.FieldData;
import de.algoristic.evocode.app.io.FitnessValue;
import de.algoristic.evocode.app.periphery.RobocodeAdaptor;
import de.algoristic.evocode.genetic.FitnessFunction;
import de.algoristic.evocode.genetic.GenomeExpressor;
import de.algoristic.evocode.genetic.Phaenotype;

public class Enviroment {

	private final RobocodeAdaptor robocode;
	private final boolean visualize;

	public Enviroment(boolean visualize) {
		this.visualize = visualize;
		robocode = RobocodeAdaptor.getInstance();
	}
	
	public Enviroment() {
		this(false);
	}
	
	public FieldData test(final Generation generation) {
		final int generationNumber = generation.getGenerationNumber();
		final FieldData data = new FieldData(generationNumber);
		for(final Individual individual : generation) {
			final GenomeExpressor expressor = new GenomeExpressor(individual);
			try (Phaenotype phaenotype = expressor.expressGenome()) {
				final FitnessFunction fitnessFunction = FitnessFunction.getFunctionFor(generationNumber);
				final FitnessValue fitness = robocode.eval(phaenotype, fitnessFunction, visualize);
				data.addValues(individual, fitness);
			} catch (IOException e) {
				throw new RuntimeException("Cannot remove resources of " + individual, e);
			}
		}
		return data;
	}
}
