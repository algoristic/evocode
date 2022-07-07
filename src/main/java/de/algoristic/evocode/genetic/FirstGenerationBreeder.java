package de.algoristic.evocode.genetic;

import de.algoristic.evocode.run.EvolutionSettings;
import de.algoristic.evocode.run.Generation;

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
		for(int i = 0; i < generationSize; i++) {
			Genome genome = genetics.initialize();
		}
	}

}
