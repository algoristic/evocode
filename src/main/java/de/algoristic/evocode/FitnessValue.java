package de.algoristic.evocode;

public class FitnessValue {

	private double value;

	public double getValue() {
		return value;
	}

	public synchronized void setValue(double value) {
		this.value = value;
	}
}
