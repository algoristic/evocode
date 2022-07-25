package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.List;

import de.algoristic.evocode.app.conf.EvocodeSettings;

public class ValueRangeManager {

	private EvocodeSettings settings;

	public ValueRangeManager() {
		this(new EvocodeSettings());
	}

	private ValueRangeManager(EvocodeSettings settings) {
		this.settings = settings;
	}

	public List<ValueRange> getRanges(String category) {
		List<Double> rangeValues = settings.getValueRanges(category);
		List<ValueRange> ranges = new ArrayList<>();
		if(! rangeValues.contains(100d)) rangeValues.add(100d);
		double start = 0;
		for (double limit : rangeValues) {
			double pMin = (start / 100);
			double pMax = (limit / 100);
			ValueRange range = new ValueRange(pMin, pMax);
			ranges.add(range);
			start = (limit + 1);
		}
		return ranges;
	}
}
