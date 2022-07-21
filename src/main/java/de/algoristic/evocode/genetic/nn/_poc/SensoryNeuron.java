package de.algoristic.evocode.genetic.nn._poc;

import java.util.ArrayList;
import java.util.List;

public class SensoryNeuron implements SendingNeuron {

	private static final Double DEFAULT = Double.NaN;

	private double stimulus = DEFAULT;
	private List<Connection> connections = new ArrayList<>();

	@Override
	public void addConnection(Connection connection) {
		connections.add(connection);
	}

	public void process(double stimulus) {
		this.stimulus = stimulus;
	}

	@Override
	public void fire() {
		if (stimulus == DEFAULT) return;
		connections.forEach(connection -> connection.send(stimulus));
	}

	/**
	 * Reset the sensor to default after firing.
	 */
	@Override
	public void accumulate() {
		stimulus = DEFAULT;
	}
}
