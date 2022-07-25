package de.algoristic.evocode.genetic.nn.encoding;

public enum ActionCategory {

	// basic, essential robot action categories
	move(0, 100),
	fire(.1d, 3.0d),
	turn(0, 359),
	awareness(0, 100);

	private double absMin;
	private double absMax;

	private ActionCategory(double absMin, double absMax) {
		this.absMin = absMin;
		this.absMax = absMax;
	}

	public double getAbsMin() {
		return absMin;
	}

	public double getAbsMax() {
		return absMax;
	}
}
