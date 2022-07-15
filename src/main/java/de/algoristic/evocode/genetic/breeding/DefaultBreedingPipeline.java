package de.algoristic.evocode.genetic.breeding;

import java.util.ArrayList;
import java.util.List;

import de.algoristic.evocode.app.Individuals;
import de.algoristic.evocode.app.conf.EvolutionSettings;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.alteration.AlterationPipeline;

public class DefaultBreedingPipeline extends AbstractBreedingPipeline {

	private int generation;
	private final EvolutionSettings settings;

	public DefaultBreedingPipeline(List<Genome> offspringDna, int generation) {
		super(offspringDna);
		this.generation = generation;
		settings = new EvolutionSettings();
	}

	@Override
	public Individuals apply(AlterationPipeline alterers) {
		List<Genome> alteredOffsprings = new ArrayList<>();
		for (Genome genome : offspringDna) {
			Genome alteredGenome = genome;
			for (String mutatorSpec : alterers) {
				double mutationRate = settings.getMutationRate(mutatorSpec, generation);
				alteredGenome = alteredGenome.mutate(mutatorSpec, mutationRate);
			}
			alteredOffsprings.add(alteredGenome);
		}
		return new Individuals(alteredOffsprings);
	}
}
