package de.algoristic.evocode.genetic.nn._poc;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import robocode.Robot;

public class NeuralBot extends Robot {

	private static final Random RND = new SecureRandom();

	private List<SensoryNeuron> sensorLayer = new ArrayList<>();
	private List<IntermediateNeuron> hiddenLayer = new ArrayList<>();
	private List<ActionNeuron> actionLayer = new ArrayList<>();

	/* INIT ALL NEURONS */

	@Override
	public void run() {
		/* INIT CONNECTIONS BETWEEN NEURONS */
	}

	/* GENERATE METHOD BODIES */

	void processStimuli() {
		for(ActionNeuron action : actionLayer) {
			action.propagate();
		}
		for(IntermediateNeuron inner : hiddenLayer) {
			inner.propagate();
		}
		for(SensoryNeuron sensor : sensorLayer) {
			sensor.propagate();
		}
	}

	interface Neuron {
		void fire();
		void accumulate();
		default void propagate() {
			this.accumulate();
			this.fire();
		}
	}

	interface ReceivingNeuron extends Neuron {
		void receive(double stimulus);
	}

	interface SendingNeuron extends Neuron {
		void addConnection(Connection connection);
	}

	class SensoryNeuron implements SendingNeuron {

		private final Double DEFAULT = Double.NaN;

		private double stimulus = DEFAULT;
		private List<Connection> connections = new ArrayList<>();

		@Override
		public void addConnection(Connection connection) {
			connections.add(connection);
		}

		public void process(double stimulus) {
			this.stimulus = stimulus;
		}

		@Override
		public void fire() {
			if (stimulus == DEFAULT) return;
			connections.forEach(connection -> connection.send(stimulus));
		}

		@Override
		public void accumulate() {
			stimulus = DEFAULT;
		}
	}

	class IntermediateNeuron implements ReceivingNeuron, SendingNeuron {

		private final Double DEFAULT = Double.NaN;

		private double state = DEFAULT;
		private List<Connection> connections = new ArrayList<>();

		@Override
		public void addConnection(Connection connection) {
			connections.add(connection);
		}

		@Override
		public void fire() {
			if(state == DEFAULT) return;
			connections.forEach(connection -> connection.send(state));
			state = DEFAULT;
		}

		@Override
		public void accumulate() {
			if(state == DEFAULT) return;
			state = Math.tanh(state);
		}

		@Override
		public void receive(double stimulus) {
			if(state == DEFAULT) state = 0d;
			state += stimulus;
		}
	}

	class ActionNeuron implements ReceivingNeuron {

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
			double value = min + (max - min) * RND.nextDouble();
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

	class Connection {

		private final double weight;
		private final ReceivingNeuron sink;

		public Connection(final double weight, final ReceivingNeuron sink) {
			this.weight = weight;
			this.sink = sink;
		}

		void send(double stimulus) {
			stimulus *= weight;
			sink.receive(stimulus);
		}
	}
}
