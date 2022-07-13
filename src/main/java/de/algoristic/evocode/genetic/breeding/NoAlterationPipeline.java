package de.algoristic.evocode.genetic.breeding;

import java.util.List;

import de.algoristic.evocode.app.Individuals;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.alteration.AlterationPipeline;

public class NoAlterationPipeline extends AbstractBreedingPipeline {

	public NoAlterationPipeline(List<Genome> offspringDna) {
		super(offspringDna);
	}

	@Override
	public Individuals apply(AlterationPipeline alterers) {
		return new Individuals(offspringDna);
	}
}
