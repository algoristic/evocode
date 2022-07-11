package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public class EmptyType implements AcceptedType<Void> {

	@Override
	public int numberOfOptions() {
		return 1;
	}

	@Override
	public ActorValue getOption(int _p) {
		return new ActorValue("");
	}
}
