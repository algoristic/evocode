package de.algoristic.evocode.genetic.nn.encoding;

public class PlainResolvable implements Resolvable {

	private String value;

	public PlainResolvable(String value) {
		this.value = value;
	}

	@Override
	public String resolve() {
		return value;
	}
}
