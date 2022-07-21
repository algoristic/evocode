package de.algoristic.evocode.genetic.nn.encoding;

import java.util.List;

public interface SendingNeuron {

	void addConnection(ReceivingNeuron neuron);
	List<ReceivingNeuron> getReceivers();
}
