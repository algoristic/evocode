package de.algoristic.evocode.genetic.nn.encoding;

public class ValueRange {

	private double fromIncluded;
	private double toIncluded;

	public ValueRange(double fromIncluded, double toIncluded) {
		this.fromIncluded = fromIncluded;
		this.toIncluded = toIncluded;
	}

	public double getFromIncluded() {
		return fromIncluded;
	}

	public double getToIncluded() {
		return toIncluded;
	}
}
