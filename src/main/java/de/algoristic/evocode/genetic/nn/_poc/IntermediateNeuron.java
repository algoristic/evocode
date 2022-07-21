package de.algoristic.evocode.genetic.nn._poc;

import java.util.ArrayList;
import java.util.List;

public class IntermediateNeuron implements ReceivingNeuron, SendingNeuron {

	private static final Double DEFAULT = Double.NaN;

	private double state = DEFAULT;
	private List<Connection> connections = new ArrayList<>();

	@Override
	public void addConnection(Connection connection) {
		connections.add(connection);
	}

	@Override
	public void fire() {
		if(state == DEFAULT) return;
		connections.forEach(connection -> connection.send(state));
		state = DEFAULT;
	}

	@Override
	public void accumulate() {
		if(state == DEFAULT) return;
		state = Math.max(0, Math.tanh(state));
	}

	@Override
	public void receive(double stimulus) {
		if(state == DEFAULT) state = 0d;
		state += stimulus;
	}
}
