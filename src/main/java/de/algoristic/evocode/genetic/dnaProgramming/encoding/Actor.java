package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import static de.algoristic.evocode.genetic.dnaProgramming.encoding.AcceptedType.BOOLEAN;
import static de.algoristic.evocode.genetic.dnaProgramming.encoding.AcceptedType.DOUBLE;
import static de.algoristic.evocode.genetic.dnaProgramming.encoding.AcceptedType.NONE;

import java.util.Arrays;
import java.util.List;

public class Actor {

	private final AcceptedType acceptedType; // z. B. "double" oder "boolean" oder "<ohne>"
	private final String name; // z. B. "ahead"
	private final ValueRange valueRange;

	public Actor(final AcceptedType acceptedType, final String name, final ValueRange valueRange) {
		this.acceptedType = acceptedType;
		this.name = name;
		this.valueRange = valueRange;
	}

	public static List<Actor> actors() {
		return Arrays.asList(
			new Actor(DOUBLE, "ahead", ValueRange.of("0", "100")),
			new Actor(DOUBLE, "back", ValueRange.of("0", "100")),
			new Actor(DOUBLE, "turnLeft", ValueRange.of("0", "360")),
			new Actor(DOUBLE, "turnRight", ValueRange.of("0", "360")),
			new Actor(DOUBLE, "turnGunLeft", ValueRange.of("0", "360")),
			new Actor(DOUBLE, "turnGunRight", ValueRange.of("0", "360")),
			new Actor(DOUBLE, "turnRadarLeft", ValueRange.of("0", "360")),
			new Actor(DOUBLE, "turnRadarRight", ValueRange.of("0", "360")),
			new Actor(DOUBLE, "fire", ValueRange.of("0.1d", "3.0d")),
			new Actor(DOUBLE, "fireBullet", ValueRange.of("0.1d", "3.0d")),
			new Actor(BOOLEAN, "stop", ValueRange.of("false", "true")),
			new Actor(NONE, "doNothing", ValueRange.empty()),
			new Actor(NONE, "scan", ValueRange.empty()),
			new Actor(NONE, "stop", ValueRange.empty()),
			new Actor(NONE, "resume", ValueRange.empty()));
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
