package de.algoristic.evocode.genetic;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.algoristic.evocode.context.FilesystemContext;
import de.algoristic.evocode.run.EvocodeSettings;
import de.algoristic.evocode.run.EvolutionSettings;
import de.algoristic.evocode.run.Generation;
import de.algoristic.evocode.run.GenerationProperties;
import de.algoristic.evocode.run.Individual;

public class FirstGenerationBreeder implements Breeder {

	private final EvolutionSettings evolutionSettings;
	private final EvocodeSettings evocodeSettings;
	private final FilesystemContext context;

	private FirstGenerationBreeder(
		final EvolutionSettings evolutionSettings,
		final EvocodeSettings evocodeSettings,
		final FilesystemContext context) {
		this.evolutionSettings = evolutionSettings;
		this.evocodeSettings = evocodeSettings;
		this.context = context;
	}

	public FirstGenerationBreeder() {
		this(new EvolutionSettings(), new EvocodeSettings(), new FilesystemContext());
	}

	@Override
	public Generation breedGeneration() {
		int thisGeneration = 0;
		int generationSize = evolutionSettings.getGenerationSize(thisGeneration);
		boolean isIslandsEvolution = evocodeSettings.isIslandsEvolution();
		if(isIslandsEvolution) {
			defineIslands(thisGeneration, generationSize);
		}

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

	private void defineIslands(int generation, int generationSize) {
		int islands = evocodeSettings.getNumberOfIslands();
		int individualsPerIsland = (generationSize / islands);
		if((individualsPerIsland * islands) != generationSize) throw new RuntimeException("Insufficient definition of island number");
		GenerationProperties properties = context.getGenerationProperties(generation);

		int start = 0;
		int end = individualsPerIsland;
		for(int island = 0; island < islands; island++) {
			String islandSpec = IntStream.range(start, end)
				.mapToObj(String::valueOf)
				.collect(Collectors.joining(","));
			properties.writeIslandSpec(island, islandSpec);
			start += individualsPerIsland;
			end += individualsPerIsland;
		}
	}
}
