package de.algoristic.evocode.genetic.nn.encoding;

import java.util.List;

public interface ReceivingNeuron {

	void addConnection(SendingNeuron sendingNeuron);
	List<SendingNeuron> getSenders();
}
