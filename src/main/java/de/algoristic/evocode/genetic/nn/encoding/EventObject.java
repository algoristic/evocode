package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.List;

public class EventObject {

	private String objectType;
	private String name;
	private List<Sensor> objectSensors;

	public EventObject(String objectType, String name) {
		this.objectType = objectType;
		this.name = name;
		objectSensors = new ArrayList<>();
	}
	
	public void addSensor(Sensor sensor) {
		objectSensors.add(sensor);
	}

	public List<Sensor> getObjectSensors() {
		return objectSensors;
	}

	public String getVariableDeclaration(String obtainer) {
		return new StringBuffer()
			.append(objectType)
			.append(" ")
			.append(name)
			.append(" = event.")
			.append(obtainer)
			.append("();")
			.toString();
	}
}
