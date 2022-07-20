package de.algoristic.evocode.genetic.nn._poc;

public class SensoryNeuron {

	private final Double DEFAULT = -100d;

	private final double scale;

	private double i0 = DEFAULT;
	private double i1 = DEFAULT;

	public SensoryNeuron(double scale) {
		this.scale = scale;
	}

	public void process(double stimulus) {
		i0 = (stimulus * scale);
	}

	public void propagate() {
		
	}
}
