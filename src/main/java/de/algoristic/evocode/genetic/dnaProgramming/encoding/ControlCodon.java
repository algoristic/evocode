package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public class ControlCodon {

	private final String controlStructureCode;
	private final String firstConditionCode;
	private final String secondConditionCode;

	public ControlCodon(
		final String controlStructureCode,
		final String firstConditionCode,
		final String secondConditionCode) {
		this.controlStructureCode = controlStructureCode;
		this.firstConditionCode = firstConditionCode;
		this.secondConditionCode = secondConditionCode;
	}

	public ControlCodon(
		final char controlCode,
		final char firstCode,
		final char secondCode) {
		this(
			String.valueOf(controlCode),
			String.valueOf(firstCode),
			String.valueOf(secondCode));
	}

	public String getControlStructureCode() {
		return controlStructureCode;
	}

	public String getFirstConditionCode() {
		return firstConditionCode;
	}

	public String getSecondConditionCode() {
		return secondConditionCode;
	}
}
