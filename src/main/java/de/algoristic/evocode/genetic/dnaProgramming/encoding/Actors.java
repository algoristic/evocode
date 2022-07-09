package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import static de.algoristic.evocode.genetic.dnaProgramming.encoding.AcceptedType.BOOLEAN;
import static de.algoristic.evocode.genetic.dnaProgramming.encoding.AcceptedType.DOUBLE;
import static de.algoristic.evocode.genetic.dnaProgramming.encoding.AcceptedType.NONE;

import java.util.Arrays;
import java.util.List;

import de.algoristic.evocode.util.NumberSystemUtils;

public abstract class Actors {

	private static final List<Actor> ACTORS = Arrays.asList(
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

	public static Actor getActor(ProgramCodon codon) {
		String hexCode = codon.firstHalf();
		int numericCode = NumberSystemUtils.hexToDecimal(hexCode);
		int position = (numericCode % ACTORS.size());
		return ACTORS.get(position);
	}
}
