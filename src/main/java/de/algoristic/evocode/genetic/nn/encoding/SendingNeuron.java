package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface SendingNeuron extends Neuron {

	void addConnection(Connection connection);
	List<Connection> getReceivers();
	default List<String> getConnectionInitilizers() {
		return viableConnections()
			.stream()
			.map(c -> c.getInitializer(getUUID()))
			.collect(Collectors.toList());
	}
	default List<Connection> viableConnections() {
		List<Connection> viableConnections = new ArrayList<>();
		for(Connection connection : getReceivers()) {
			ReceivingNeuron receiver = connection.getSink();
			if(receiver instanceof Actor) viableConnections.add(connection);
			if(receiver instanceof Intermitter) {
				if(RobotBootstrap.searchOutboundConnections((Intermitter) receiver)) viableConnections.add(connection);
			}
		}
		return viableConnections;
	}
}
