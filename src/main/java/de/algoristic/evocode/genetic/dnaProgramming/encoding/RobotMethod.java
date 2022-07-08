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
}
