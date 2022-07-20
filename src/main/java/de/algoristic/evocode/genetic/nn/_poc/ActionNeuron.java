package de.algoristic.evocode.genetic.nn._poc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import robocode.Robot;

public class ActionNeuron implements Neuron {

	private final Robot peer;
	private final Consumer<Robot> action;

	private List<Double> accumulation = new ArrayList<>();
	private Double result = null;

	public ActionNeuron(Robot peer, Consumer<Robot> action) {
		this.peer = peer;
		this.action = action;
	}

	@Override
	public void propagate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accumulate() {
		// TODO Auto-generated method stub
		
	}

}
