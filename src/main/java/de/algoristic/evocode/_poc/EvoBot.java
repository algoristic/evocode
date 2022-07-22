package de.algoristic.evocode._poc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import robocode.*;

public class EvoBot extends Robot {

	private double playersAtStart;
	private double numberOfRounds;
	private double battleFieldWidth;
	private double battleFieldHeight;
	private double maxTurnAwareness;

	private List<SensoryNeuron> sensorLayer = new ArrayList<>();
	private List<IntermediateNeuron> hiddenLayer = new ArrayList<>();
	private List<ActionNeuron> actionLayer = new ArrayList<>();

	SensoryNeuron s_1 = new SensoryNeuron();

	IntermediateNeuron i_1 = new IntermediateNeuron();

	ActionNeuron a_1 = new ActionNeuron(this, .1, 3.0, (robot, power) -> robot.fire(power));

	@Override
	public void run() {
		{
			this.setBodyColor(Color.RED);
			this.setGunColor(Color.RED);
			this.setBulletColor(Color.MAGENTA);
			this.setRadarColor(Color.ORANGE);
			this.setScanColor(Color.ORANGE);
		}

		playersAtStart = this.getOthers();
		numberOfRounds = this.getNumRounds();
		battleFieldWidth = this.getBattleFieldWidth();
		battleFieldHeight = this.getBattleFieldHeight();
		maxTurnAwareness = 10000d;

		sensorLayer.add(s_1);

		hiddenLayer.add(i_1);

		actionLayer.add(a_1);

		s_1.addConnection(new Connection(3.34, i_1));

		i_1.addConnection(new Connection(1.49, i_1));
		i_1.addConnection(new Connection(2.21, a_1));
	}
	
	@Override
	public void onBulletHit(BulletHitEvent event) {
		double enemyEnergy = (event.getEnergy() / 100d);
		s_1.process(enemyEnergy);
	}

	@Override
	public void onStatus(StatusEvent event) {
		RobotStatus status = event.getStatus();
		double turn = (status.getTime() / maxTurnAwareness);
		s_1.process(turn);
		processStimuli();
	}

	void raiseMaxTurnAwareness(double amount) {
		maxTurnAwareness += amount;
	}

	void lowerMaxTurnAwareness(double amount) {
		if(maxTurnAwareness == 0) return;
		maxTurnAwareness -= amount;
	}

	void processStimuli() {
		actionLayer.forEach(ActionNeuron::propagate);
		hiddenLayer.forEach(IntermediateNeuron::propagate);
		sensorLayer.forEach(SensoryNeuron::propagate);
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

		private static final double DEFAULT = Double.NaN;

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
			if (Double.isNaN(stimulus)) return;
			connections.forEach(connection -> connection.send(stimulus));
			stimulus = DEFAULT;
		}

		@Override
		public void accumulate() {
		}
	}

	class IntermediateNeuron implements ReceivingNeuron, SendingNeuron {

		private static final double DEFAULT = Double.NaN;

		private double outBoundState = DEFAULT;
		private double inBoundState = DEFAULT;
		private List<Connection> connections = new ArrayList<>();

		@Override
		public void addConnection(Connection connection) {
			connections.add(connection);
		}

		@Override
		public void fire() {
			if(Double.isNaN(outBoundState)) return;
			connections.forEach(connection -> connection.send(outBoundState));
			outBoundState = DEFAULT;
		}

		@Override
		public void accumulate() {
			if(Double.isNaN(inBoundState)) return;
			outBoundState = Math.tanh(inBoundState);
			inBoundState = DEFAULT;
		}

		@Override
		public void receive(double stimulus) {
			if(Double.isNaN(inBoundState)) inBoundState = 0d;
			inBoundState += stimulus;
		}
	}

	class ActionNeuron implements ReceivingNeuron {

		private static final double DEFAULT = Double.NaN;

		private final Robot peer;
		private final BiConsumer<Robot, Double> action;

		private double min;
		private double max;

		private double state = DEFAULT;

		public ActionNeuron(Robot peer, double min, double max, BiConsumer<Robot, Double> action) {
			this.peer = peer;
			this.min = min;
			this.max = max;
			this.action = action;
		}

		@Override
		public void fire() {
			if(Double.isNaN(state)) return;
			double value = min + (max - min) * state;
			action.accept(peer, value);
			state = DEFAULT;
		}

		@Override
		public void accumulate() {
			if(Double.isNaN(state)) return;
			state = Math.max(0, Math.tanh(state));
		}

		@Override
		public void receive(double stimulus) {
			if(Double.isNaN(state)) state = 0d;
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
