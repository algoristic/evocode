package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public class ProgramCodon {

	private final String value;

	public ProgramCodon(String value) {
		this.value = value;
	}

	public String firstHalf() {
		return value.substring(0, 1);
	}

	public String secondHalf() {
		return value.substring(1, 2);
	}
}
