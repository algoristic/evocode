package de.algoristic.evocode.genetic.breeding;

import de.algoristic.evocode.app.Generation;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.app.conf.EvolutionSettings;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.GenomeManager;

public class FirstGenerationBreeder implements Breeder {

	private final EvolutionSettings evolutionSettings;

	private FirstGenerationBreeder(final EvolutionSettings evolutionSettings) {
		this.evolutionSettings = evolutionSettings;
	}

	public FirstGenerationBreeder() {
		this(new EvolutionSettings());
	}

	@Override
	public Generation breedGeneration() {
		int thisGeneration = 0;
		int generationSize = evolutionSettings.getGenerationSize(thisGeneration);
		GenomeManager genomeManager = new GenomeManager();
		Genetics genetics = genomeManager.getGenetics();
		Generation generation = new Generation(thisGeneration);
		for (int individualNumber = 0; individualNumber < generationSize; individualNumber++) {
			Genome genome = genetics.initialize();
			Individual individual = new Individual(thisGeneration, individualNumber, genome);
			generation.add(individual);
		}
		return generation;
	}
}
