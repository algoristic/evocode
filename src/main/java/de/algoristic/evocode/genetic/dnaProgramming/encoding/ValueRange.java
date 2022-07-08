package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public interface ValueRange {

	String getMin();

	String getMax();

	public static ValueRange of(String min, String max) {
		return new ValueRange() {

			@Override
			public String getMin() {
				return min;
			}

			@Override
			public String getMax() {
				return max;
			}
		};
	}

	public static ValueRange empty() {
		return new ValueRange() {

			@Override
			public String getMin() {
				return "";
			}

			@Override
			public String getMax() {
				// TODO Auto-generated method stub
				return "";
			}
		};
	}

}
