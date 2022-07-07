package de.algoristic.evocode.genetic;

import de.algoristic.evocode.run.Generation;

public class FilialGenerationBreeder implements Breeder {

	private final GeneticsContext context;
	private final Generation parents;

	private FilialGenerationBreeder(final GeneticsContext context, final Generation parents) {
		this.context = context;
		this.parents = parents;
	}

	public FilialGenerationBreeder(final Generation parents) {
		this(new GeneticsContext(), parents);
	}

	@Override
	public Generation breedGeneration() {
		return null;
	}

}
