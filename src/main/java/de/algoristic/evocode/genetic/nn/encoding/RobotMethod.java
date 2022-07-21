package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
