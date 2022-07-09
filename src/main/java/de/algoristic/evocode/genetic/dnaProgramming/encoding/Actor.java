package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public class Actor {

	private final AcceptedType acceptedType;
	private final String name;
	private final ValueRange valueRange;

	public Actor(final AcceptedType acceptedType, final String name, final ValueRange valueRange) {
		this.acceptedType = acceptedType;
		this.name = name;
		this.valueRange = valueRange;
	}

	public AcceptedType getAcceptedType() {
		return acceptedType;
	}

	public String getName() {
		return name;
	}

	public ValueRange getValueRange() {
		return valueRange;
	}
}
