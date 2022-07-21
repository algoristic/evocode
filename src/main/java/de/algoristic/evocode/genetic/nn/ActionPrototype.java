package de.algoristic.evocode.genetic.nn;

public enum ActionPrototype {

	ahead(0, 100),
	back(0, 100),
	fire(0.1d, 3.0d),
	turnLeft(0, 359),
	turnRight(0, 359),
	turnGunLeft(0, 359),
	turnGunRight(0, 359),
	turnRadarLeft(0, 359),
	turnRadarRight(0, 359);

	private double absMin;
	private double absMax;

	private ActionPrototype(double absMin, double absMax) {
		this.absMin = absMin;
		this.absMax = absMax;
	}

	public String getName() {
		return name();
	}
	
	public double getAbsMin() {
		return absMin;
	}

	public double getAbsMax() {
		return absMax;
	}
}
