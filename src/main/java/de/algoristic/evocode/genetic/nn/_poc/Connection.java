package de.algoristic.evocode.genetic.nn._poc;

public class Connection {

	private final double weight;
	private final Neuron sink;

	public Connection(double weight, Neuron sink) {
		this.weight = weight;
		this.sink = sink;
	}

}
