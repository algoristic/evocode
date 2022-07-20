package de.algoristic.evocode.genetic.nn._poc;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNeuronModel implements Neuron {

	protected List<Connection> connections = new ArrayList<>();;

	public void addConnection(Connection connection) {
		connections.add(connection);
	}

	protected void propagate(double processedValue) {
		for(Connection connection : connections) {
			
		}
	}
}
