package de.algoristic.evocode.genetic.breeding;

import java.util.List;

import de.algoristic.evocode.genetic.Genome;

public abstract class AbstractBreedingPipeline implements BreedingPipeline {

	protected final List<Genome> offspringDna;

	public AbstractBreedingPipeline(List<Genome> offspringDna) {
		this.offspringDna = offspringDna;
	}
}
