package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public class Sensor {

	private final String name;

	public Sensor(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "this.[name]()"
			.replace("[name]", name);
	}
}
