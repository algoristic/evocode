package de.algoristic.evocode.genetic.nn.encoding;

public class Connection {

	private final double weight;
	private final ReceivingNeuron sink;

	public Connection(double weight, ReceivingNeuron sink) {
		this.weight = weight;
		this.sink = sink;
	}

	public ReceivingNeuron getSink() {
		return sink;
	}

	public String getInitializer(String callerId) {
		String receiver = sink.getUUID();
		return new StringBuffer()
			.append(callerId)
			.append(".addConnection(new Connection(")
			.append(weight)
			.append(", ")
			.append(receiver)
			.append("))")
			.toString();
	}
}
