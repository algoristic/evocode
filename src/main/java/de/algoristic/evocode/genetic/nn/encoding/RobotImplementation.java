package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.List;
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
		String sensorBlock = robot.getConnectedSensors()
			.stream()
			.map(Sensor::getLayerAdder)
			.collect(Collectors.joining(";\n\t\t", "", ";\n\n"));
		String intermitterBlock = robot.getConnectedIntermitters()
			.stream()
			.map(Intermitter::getLayerAdder)
			.collect(Collectors.joining(";\n\t\t", "\t\t", ";\n\n"));
		String actorBlock = robot.getConnectedActors()
			.stream()
			.map(Actor::getLayerAdder)
			.collect(Collectors.joining(";\n\t\t", "\t\t", ";"));
		return new StringBuffer()
			.append(sensorBlock)
			.append(intermitterBlock)
			.append(actorBlock)
			.toString();
	}

	private List<RobotMethod> viableMethods() {
		List<RobotMethod> viableMethods = new ArrayList<>();
		List<RobotMethod> robotMethods = robot.getMethods();
		List<Sensor> viableSensors = robot.getConnectedSensors();
		for(RobotMethod method : robotMethods) {
			boolean hasActiveSensor = false;
			List<Sensor> availableSensors = method.getAvailableSensors();
			for(Sensor sensor : availableSensors) {
				hasActiveSensor = viableSensors.contains(sensor);
				if(hasActiveSensor) break;
			}
			if(hasActiveSensor) viableMethods.add(method);
		}
		return viableMethods;
	}

	public String getMethods() {
		StringBuffer buffer = new StringBuffer();
		List<RobotMethod> methods = viableMethods();
		for(RobotMethod method : methods) {
			String methodHead = method.getMethodHead("\t");
			String eventObjects = method.getEventObjects()
				.stream()
				.collect(Collectors.joining(";\n\t\t", "\t\t", ";\n"));
			List<Sensor> viableSensors = robot.getConnectedSensors();
			List<Sensor> availableSensors = method.getAvailableSensors();
			List<Sensor> implementedSensors = new ArrayList<>();
			for(Sensor sensor : availableSensors) {
				if(viableSensors.contains(sensor)) implementedSensors.add(sensor);
			}
			String variableDeclarations = implementedSensors.stream()
				.map(Sensor::getVariableDeclaration)
				.collect(Collectors.joining(";\n\t\t", "\t\t", ";\n\n"));
			String sensorCalls = implementedSensors.stream()
				.map(Sensor::getSensorCall)
				.collect(Collectors.joining(";\n\t\t", "\t\t", ";\n"));
			List<String> boilerplate = method.getBoilerplateCode();
			String boilerplateCode = "";
			if(! boilerplate.isEmpty()) {
				boilerplateCode = boilerplate.stream()
					.collect(Collectors.joining(";\n\t\t", "\n\t\t", ";\n"));
			}
			String methodFood = method.getMethodFoot("\t");
			buffer.append(methodHead)
				.append("\n")
				.append(eventObjects)
				.append(variableDeclarations)
				.append(sensorCalls)
				.append(boilerplateCode)
				.append(methodFood)
				.append("\n\n");
		}
		return buffer.toString();
	}
}
