package de.algoristic.evocode.genetic.nn._poc;

import java.security.SecureRandom;
import java.util.function.BiConsumer;

import robocode.Robot;

public class ActionNeuron implements ReceivingNeuron {

	private static final double DEFAULT = Double.NaN;

	private final Robot peer;
	private final BiConsumer<Robot, Double> action;

	private double min;
	private double max;

	private double state = DEFAULT;

	public ActionNeuron(Robot peer, BiConsumer<Robot, Double> action) {
		this.peer = peer;
		this.action = action;
	}

	@Override
	public void fire() {
		if(state == DEFAULT) return;
		double value = min + (max - min) * (new SecureRandom()).nextDouble();
		action.accept(peer, value);
	}

	@Override
	public void accumulate() {
		if(state == DEFAULT) return;
		state = Math.max(0, Math.tanh(state));
	}

	@Override
	public void receive(double stimulus) {
		if(state == DEFAULT) state = 0d;
		state += stimulus;
	}
}
