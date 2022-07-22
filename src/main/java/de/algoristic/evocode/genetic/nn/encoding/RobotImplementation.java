package de.algoristic.evocode.genetic.nn.encoding;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RobotImplementation {

	private RobotBootstrap robot;

	public RobotImplementation(RobotBootstrap robot) {
		this.robot = robot;
	}

	public String getNeurons() {
		String sensorBlock = robot.getConnectedSensors()
			.stream()
			.map(Sensor::getInitializer)
			.collect(Collectors.joining(";\n\t", "", ";\n\n"));
		String intermitterBlock = robot.getConnectedIntermitters()
			.stream()
			.map(Intermitter::getInitializer)
			.collect(Collectors.joining(";\n\t", "\t", ";\n\n"));
		String actorBlock = robot.getConnectedActors()
			.stream()
			.map(Actor::getInitializer)
			.collect(Collectors.joining(";\n\t", "\t", ";"));
		return new StringBuffer()
			.append(sensorBlock)
			.append(intermitterBlock)
			.append(actorBlock)
			.toString();
	}

	public String getConnections() {
		String sensorBlock = robot.getConnectedSensors()
			.stream()
			.map(Sensor::getConnectionInitilizers)
			.flatMap(List::stream)
			.collect(Collectors.joining(";\n\t\t", "", ";\n\n"));
		String intermitterBlock = robot.getConnectedIntermitters()
			.stream()
			.map(Intermitter::getConnectionInitilizers)
			.flatMap(List::stream)
			.collect(Collectors.joining(";\n\t\t", "\t\t", ""));
		return new StringBuffer()
			.append(sensorBlock)
			.append(intermitterBlock)
			.toString();
	}

	public String getAddToLayers() {
		return null;
	}

	public String getMethods() {
		return null;
	}
}
