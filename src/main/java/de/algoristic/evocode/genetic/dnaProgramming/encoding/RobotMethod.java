package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.ArrayList;
import java.util.List;

public class RobotMethod {

	private final String name;
	private final String event;
	private final List<ControlStructure> controlStructures;

	public RobotMethod(final String name, final String event) {
		this.name = name;
		this.event = event;
		controlStructures = new ArrayList<>();
	}

	public void add(ControlStructure controlStructure) {
		this.controlStructures.add(controlStructure);
	}

	public String getName() {
		return name;
	}

	public String getEvent() {
		return event;
	}

	public List<ControlStructure> getControlStructures() {
		return controlStructures;
	}

	@Override
	public RobotMethod clone() {
		return new RobotMethod(this.name, this.event);
	}
}
