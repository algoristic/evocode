package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.Arrays;
import java.util.List;

public class TruthType implements AcceptedType<Boolean> {

	private final static List<Boolean> ls = Arrays.asList(true, false);

	@Override
	public int numberOfOptions() {
		return 2;
	}

	@Override
	public ActorValue getOption(int position) {
		boolean value = ls.get(position);
		String representation = String.valueOf(value);
		return new ActorValue(representation);
	}

}
