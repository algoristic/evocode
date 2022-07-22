package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RobotMethod {

	private String name;
	private Map<String, EventObject> eventObjectMapping;
	private List<Sensor> localSensors;
	private List<String> boilerplateLOC;

	public RobotMethod(String name) {
		this.name = name;
		eventObjectMapping = new HashMap<>();
		localSensors = new ArrayList<>();
		boilerplateLOC = new ArrayList<>();
	}

	public void addEventObject(String obtainer, EventObject eventObject) {
		eventObjectMapping.put(obtainer, eventObject);
	}

	public void addLocalSensor(Sensor sensor) {
		localSensors.add(sensor);
	}

	public void addBoilerplateCode(String line) {
		boilerplateLOC.add(line);
	}

	public List<String> getBoilerplateCode() {
		return boilerplateLOC;
	}

	public List<Sensor> getAvailableSensors() {
		List<Sensor> availableSensors = new ArrayList<>(localSensors);
		for(EventObject eo : eventObjectMapping.values()) {
			List<Sensor> objectSensors = eo.getObjectSensors();
			for(Sensor sensor : objectSensors) {
				if(! availableSensors.contains(sensor)) availableSensors.add(sensor);
			}
		}
		return availableSensors;
	}

	public List<String> getEventObjects() {
		List<String> eventObjects = new ArrayList<>();
		for(Entry<String, EventObject> mapping : eventObjectMapping.entrySet()) {
			String obtainer = mapping.getKey();
			EventObject obj = mapping.getValue();
			String eventObject = obj.getVariableDeclaration(obtainer);
			eventObjects.add(eventObject);
		}
		return eventObjects;
	}

	public List<Sensor> getLocalSensors() {
		return localSensors;
	}

	public String getMethodHead(String indent) {
		return new StringBuffer()
			.append(indent)
			.append("@Override\n")
			.append(indent)
			.append("public void ")
			.append(methodName())
			.append("(")
			.append(eventName())
			.append(") {")
			.toString();
	}

	public String getMethodFoot(String indent) {
		return new StringBuffer()
			.append(indent)
			.append("}")
			.toString();
	}

	private String methodName() {
		return new StringBuffer()
			.append("on")
			.append(name.substring(0, 1).toUpperCase())
			.append(name.substring(1))
			.toString();
	}

	private String eventName() {
		return new StringBuffer()
			.append(name.substring(0, 1).toUpperCase())
			.append(name.substring(1))
			.append("Event event")
			.toString();
	}
}
