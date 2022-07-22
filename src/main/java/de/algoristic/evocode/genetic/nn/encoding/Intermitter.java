package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.List;

public class Intermitter implements SendingNeuron, ReceivingNeuron {

	private final String uuid;
	private List<Connection> receivers;

	public Intermitter(int id) {
		uuid = ("i_" + id);
		receivers = new ArrayList<>();
	}

	@Override
	public void addConnection(Connection connection) {
		receivers.add(connection);
	}

	@Override
	public List<Connection> getReceivers() {
		return receivers;
	}

	@Override
	public String getInitializer() {
		return new StringBuffer()
			.append("IntermediateNeuron ")
			.append(uuid)
			.append(" = new IntermediateNeuron()")
			.toString();
	}

	@Override
	public String getUUID() {
		return uuid;
	}

	@Override
	public String getLayerAdder() {
		return new StringBuffer()
			.append("hiddenLayer.add(")
			.append(uuid)
			.append(")")
			.toString();
	}
}
