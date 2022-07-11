package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.Arrays;
import java.util.List;

import de.algoristic.evocode.util.NumberSystemUtils;

public abstract class Actors {

	private static final List<Actor> ACTORS = Arrays.asList(
		new Actor(new NumericType(0, 100), "ahead"),
		new Actor(new NumericType(0, 100), "back"),
		new Actor(new NumericType(0, 360), "turnLeft"),
		new Actor(new NumericType(0, 360), "turnRight"),
		new Actor(new NumericType(0, 360), "turnGunLeft"),
		new Actor(new NumericType(0, 360), "turnGunRight"),
		new Actor(new NumericType(0, 360), "turnRadarLeft"),
		new Actor(new NumericType(0, 360), "turnRadarRight"),
		new Actor(new NumericType(0.1d, 3.0d), "fire"),
		new Actor(new NumericType(0.1d, 3.0d), "fireBullet"),
		new Actor(new TruthType(), "stop"),
		new Actor(new EmptyType(), "doNothing"),
		new Actor(new EmptyType(), "scan"),
		new Actor(new EmptyType(), "stop"),
		new Actor(new EmptyType(), "resume"));

	public static Actor getActor(ProgramCodon codon) {
		Actor actor = determineActor(codon);
		ActorValue value = determineValue(codon, actor);
		actor.setValue(value);
		return actor;
	}

	private static ActorValue determineValue(ProgramCodon codon, Actor actor) {
		AcceptedType<?> acceptedType = actor.getAcceptedType();
		String hexCode = codon.secondHalf();
		int numericCode = NumberSystemUtils.hexToDecimal(hexCode);
		int position = (numericCode % acceptedType.numberOfOptions());
		ActorValue value = acceptedType.getOption(position);
		return value;
	}

	private static Actor determineActor(ProgramCodon codon) {
		String hexCode = codon.firstHalf();
		int numericCode = NumberSystemUtils.hexToDecimal(hexCode);
		int position = (numericCode % ACTORS.size());
		Actor actor = ACTORS.get(position).clone();
		return actor;
	}
}
