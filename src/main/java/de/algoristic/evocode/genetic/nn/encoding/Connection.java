package de.algoristic.evocode.genetic.nn.encoding;

public class Connection {

	private final double weight;
	private final ReceivingNeuron sink;

	public Connection(double weight, ReceivingNeuron sink) {
		this.weight = weight;
		this.sink = sink;
	}
}
