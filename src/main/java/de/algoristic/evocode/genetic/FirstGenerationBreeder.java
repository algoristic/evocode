package de.algoristic.evocode.genetic;

import de.algoristic.evocode.run.EvolutionSettings;
import de.algoristic.evocode.run.Generation;
import de.algoristic.evocode.run.Individual;

public class FirstGenerationBreeder implements Breeder {

	private final GeneticsContext context;
	private final EvolutionSettings evolutionSettings;

	private FirstGenerationBreeder(final GeneticsContext context, final EvolutionSettings evolutionSettings) {
		this.context = context;
		this.evolutionSettings = evolutionSettings;
	}

	public FirstGenerationBreeder() {
		this(new GeneticsContext(), new EvolutionSettings());
	}

	@Override
	public Generation breedGeneration() {
		int thisGeneration = 0;
		int generationSize = evolutionSettings.getGenerationSize(thisGeneration);
		GenomeManager genomeManager = new GenomeManager();
		Genetics genetics = genomeManager.getGenetics();
		Generation generation = new Generation(thisGeneration);
		for(int individualNumber = 0; individualNumber < generationSize; individualNumber++) {
			Genome genome = genetics.initialize();
			Individual individual = new Individual(thisGeneration, individualNumber, genome);
			generation.add(individual);
		}
		return generation;
	}

}
