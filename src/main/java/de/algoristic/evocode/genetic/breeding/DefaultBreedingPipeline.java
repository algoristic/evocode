package de.algoristic.evocode.genetic.breeding;

import java.util.ArrayList;
import java.util.List;

import de.algoristic.evocode.app.Individuals;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.alteration.AlterationPipeline;

public class DefaultBreedingPipeline extends AbstractBreedingPipeline {

	public DefaultBreedingPipeline(List<Genome> offspringDna) {
		super(offspringDna);
	}

	@Override
	public Individuals apply(AlterationPipeline alterers) {
		List<Genome> alteredOffsprings = new ArrayList<>();
		for (Genome genome : offspringDna) {
			Genome alteredGenome = genome;
			for (String mutatorSpec : alterers) {
				alteredGenome = alteredGenome.mutate(mutatorSpec);
			}
			alteredOffsprings.add(alteredGenome);
		}
		return new Individuals(alteredOffsprings);
	}
}
