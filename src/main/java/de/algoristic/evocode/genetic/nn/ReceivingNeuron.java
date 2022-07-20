package de.algoristic.evocode.genetic.nn;

import java.util.List;

public interface ReceivingNeuron {

	void addConnection(SendingNeuron sendingNeuron);
	List<SendingNeuron> getSenders();
}
