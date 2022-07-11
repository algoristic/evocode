package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class NumericType implements AcceptedType<Double> {

	private final static List<BiFunction<Double, Double, Double>> fns = Arrays.asList(
		(min, max) -> min,
		(min, max) -> max,
		(min, max) -> (max * .1),
		(min, max) -> (max * .2),
		(min, max) -> (max * .25),
		(min, max) -> (max * .3),
		(min, max) -> (max * .33),
		(min, max) -> (max * .4),
		(min, max) -> (max * .5),
		(min, max) -> (max * .6),
		(min, max) -> (max * .66),
		(min, max) -> (max * .7),
		(min, max) -> (max * .75),
		(min, max) -> (max * .8),
		(min, max) -> (max * .9));

	private final double min;
	private final double max;

	public NumericType(final double min, final double max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public int numberOfOptions() {
		return fns.size();
	}

	@Override
	public ActorValue getOption(int position) {
		BiFunction<Double, Double, Double> fn = fns.get(position);
		double value = fn.apply(min, max);
		String representation = String.valueOf(value);
		return new ActorValue(representation  + "d");
	}

}
