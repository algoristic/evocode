package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.List;

public class Intermitter implements SendingNeuron, ReceivingNeuron {

	private final String uuid;
	private List<SendingNeuron> senders;
	private List<ReceivingNeuron> receivers;

	public Intermitter(int id) {
		uuid = ("i_" + id);
		senders = new ArrayList<>();
		receivers = new ArrayList<>();
	}

	@Override
	public void addConnection(SendingNeuron sendingNeuron) {
		senders.add(sendingNeuron);
	}

	@Override
	public void addConnection(ReceivingNeuron neuron) {
		receivers.add(neuron);
	}

	@Override
	public List<SendingNeuron> getSenders() {
		return senders;
	}

	@Override
	public List<ReceivingNeuron> getReceivers() {
		return receivers;
	}
}
