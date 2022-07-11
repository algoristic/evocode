package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public class Actor {

	private final AcceptedType<?> acceptedType;
	private final String name;

	private ActorValue value;

	public Actor(final AcceptedType<?> acceptedType, final String name) {
		this.acceptedType = acceptedType;
		this.name = name;
	}

	public AcceptedType<?> getAcceptedType() {
		return acceptedType;
	}

	public String getName() {
		return name;
	}

	public void setValue(ActorValue value) {
		this.value = value;
	}

	public ActorValue getValue() {
		return value;
	}

	@Override
	public Actor clone() {
		return new Actor(this.acceptedType, this.name);
	}
}
