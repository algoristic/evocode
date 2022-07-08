package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public enum AcceptedType {

	DOUBLE("double"), BOOLEAN("boolean"), NONE("");

	private String value;

	private AcceptedType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
