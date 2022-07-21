package de.algoristic.evocode.genetic.nn.encoding;

import java.util.List;

public interface SendingNeuron {

	void addConnection(Connection connection);
	List<Connection> getReceivers();
}
