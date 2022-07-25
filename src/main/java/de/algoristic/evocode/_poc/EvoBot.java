package de.algoristic.evocode._poc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import robocode.*;

public class EvoBot extends AdvancedRobot {

	private double playersAtStart;
	private double numberOfRounds;
	private double battleFieldWidth;
	private double battleFieldHeight;
	private double maxTurnAwareness;

	private Feedback moveFeedback;
	private Feedback turnFeedback;
	private Feedback turnGunFeedback;
	private Feedback turnRadarFeedback;
	private Feedback fireFeedback;
	private List<Feedback> feedbacks = new ArrayList<>();

	private List<SensoryNeuron> sensorLayer = new ArrayList<>();
	private List<IntermediateNeuron> hiddenLayer = new ArrayList<>();
	private List<ActionNeuron> actionLayer = new ArrayList<>();

	SensoryNeuron s_2 = new SensoryNeuron();
	SensoryNeuron s_3 = new SensoryNeuron();

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

		moveFeedback = new Feedback(100); //FIXME -> there is no hard limit here
		turnFeedback = new Feedback(359d);
		turnGunFeedback = new Feedback(359d);
		turnRadarFeedback = new Feedback(359d);
		fireFeedback = new Feedback(3.0d);

		feedbacks.add(moveFeedback);
		feedbacks.add(turnFeedback);
		feedbacks.add(turnGunFeedback);
		feedbacks.add(turnRadarFeedback);
		feedbacks.add(fireFeedback);

		sensorLayer.add(s_2);
		sensorLayer.add(s_3);

		hiddenLayer.add(i_1);

		actionLayer.add(a_1);

		s_2.addConnection(new Connection(1.0d, i_1));
		s_3.addConnection(new Connection(1.0d, i_1));

		i_1.addConnection(new Connection(1.0d, i_1));
		i_1.addConnection(new Connection(1.0d, a_1));
	}

	@Override
	public void onStatus(StatusEvent event) {
		RobotStatus status = event.getStatus();
		double turn = (status.getTime() / maxTurnAwareness);
		double fireFeedback = this.fireFeedback.getFeedback();

		s_2.process(turn);
		s_3.process(fireFeedback);

		ticks();
		processStimuli();
	}

	void raiseTurnAwareness(double amount) {
		maxTurnAwareness += amount;
	}

	void reduceTurnAwareness(double amount) {
		if(maxTurnAwareness == 0) return;
		maxTurnAwareness -= amount;
	}

	@Override
	public void ahead(double distance) {
		if(getDistanceRemaining() != 0) return;
		moveFeedback.push(distance);
		super.ahead(distance);
	}

	@Override
	public void back(double distance) {
		if(getDistanceRemaining() != 0) return;
		moveFeedback.push(-distance);
		super.back(distance);
	}

	@Override
	public void turnLeft(double degrees) {
		if(getTurnRemaining() != 0) return;
		turnFeedback.push(-degrees);
		super.turnLeft(degrees);
	}

	@Override
	public void turnRight(double degrees) {
		if(getTurnRemaining() != 0) return;
		turnFeedback.push(degrees);
		super.turnRight(degrees);
	}

	@Override
	public void fire(double power) {
		if(getGunHeat() != 0) return;
		fireFeedback.push(power);
		super.fire(power);
	}

	@Override
	public void turnGunLeft(double degrees) {
		if(getGunTurnRemaining() != 0) return;
		turnGunFeedback.push(-degrees);
		super.turnGunLeft(degrees);
	}

	@Override
	public void turnGunRight(double degrees) {
		if(getGunTurnRemaining() != 0) return;
		turnGunFeedback.push(degrees);
		super.turnGunRight(degrees);
	}

	@Override
	public void turnRadarLeft(double degrees) {
		if(getRadarTurnRemaining() != 0) return;
		turnRadarFeedback.push(-degrees);
		super.turnRadarLeft(degrees);
	}

	@Override
	public void turnRadarRight(double degrees) {
		if(getRadarTurnRemaining() != 0) return;
		turnRadarFeedback.push(degrees);
		super.turnRadarRight(degrees);
	}

	void ticks() {
		feedbacks.forEach(Feedback::tick);
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

		private final EvoBot peer;
		private final BiConsumer<EvoBot, Double> action;

		private double min;
		private double max;

		private double state = DEFAULT;

		public ActionNeuron(EvoBot peer, double min, double max, BiConsumer<EvoBot, Double> action) {
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
			state = Math.tanh(state);
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

	class Feedback {

		private double relation;
		private List<Double> feedbackQueue = new ArrayList<>();

		public Feedback(double relation) {
			this.relation = relation;
		}

		public void tick() {
			feedbackQueue.add(0d);
		}

		public void push(double value) {
			if(value > relation) value = relation;
			if(value < (-relation)) value =(-relation);
			value /= relation;
			if(feedbackQueue.isEmpty()) {
				feedbackQueue.add(value);
			} else {
				int lastIndex = (feedbackQueue.size() - 1);
				feedbackQueue.set(lastIndex, value);
			}
		}

		public double getFeedback() {
			if(feedbackQueue.isEmpty()) return 0;
			return feedbackQueue.get(0);
		}
	}
}
