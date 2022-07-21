package de.algoristic.evocode.genetic.nn._poc;

public class Connection {

	private final double weight;
	private final ReceivingNeuron sink;

	public Connection(final double weight, final ReceivingNeuron sink) {
		this.weight = weight;
		this.sink = sink;
	}

	void send(double stimulus) {
		stimulus *= weight;
		sink.receive(stimulus);
	}
}
