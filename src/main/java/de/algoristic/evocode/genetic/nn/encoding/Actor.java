package de.algoristic.evocode.genetic.nn.encoding;

public class Actor implements ReceivingNeuron {

	private String uuid;
	private Action action;

	private double pMin;
	private double pMax;

	public Actor(int id, Action action, double pMin, double pMax) {
		uuid = ("a_" + id);
		this.action = action;
		this.pMin = pMin;
		this.pMax = pMax;
	}

	static ActionBuilder of(String name, double min, double max) {
		return new ActionBuilder(name, min, max);
	}

	static class ActionBuilder {
		private String name;
		private double absMin;
		private double absMax;

		public ActionBuilder(String name, double absMin, double absMax) {
			this.name = name;
			this.absMin = absMin;
			this.absMax = absMax;
		}

		public ActorBuilder withRange(double pMin, double pMax) {
			Action action = new Action(name, absMin, absMax);
			return new ActorBuilder(action, pMin, pMax);
		}
	}

	static class ActorBuilder {
		private Action action;
		private double pMin;
		private double pMax;

		public ActorBuilder(Action action, double pMin, double pMax) {
			this.action = action;
			this.pMin = pMin;
			this.pMax = pMax;
		}

		public Actor build(int id) {
			return new Actor(id, action, pMin, pMax);
		}
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return action.getName();
	}

	public Action getAction() {
		return action;
	}

	public double getpMin() {
		return pMin;
	}

	public double getpMax() {
		return pMax;
	}
}
