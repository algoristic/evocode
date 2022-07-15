package de.algoristic.evocode.app.conditions;

public class GenerationNumberTerminator extends EvocodeTerminator {

	private final int lastGeneration;

	public GenerationNumberTerminator(final int startGeneration, final int lastGeneration) {
		super(startGeneration);
		this.lastGeneration = lastGeneration;
	}

	@Override
	public boolean hasNext() {
		return (currentGeneration < lastGeneration);
	}
}
