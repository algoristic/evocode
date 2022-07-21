package de.algoristic.evocode.genetic.nn;

public class Action {

	private String name;
	private double absMin;
	private double absMax;

	public Action(String name, double absMin, double absMax) {
		this.name = name;
		this.absMin = absMin;
		this.absMax = absMax;
	}

	public String getName() {
		return name;
	}

	public double getAbsMin() {
		return absMin;
	}

	public double getAbsMax() {
		return absMax;
	}
}
