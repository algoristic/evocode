package de.algoristic.evocode.genetic.strategy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.app.conf.EvolutionSettings;

public class IslandSettings {

	private final EvocodeSettings evocodeSetting;
	private final EvolutionSettings evolutionSettings;

	public IslandSettings() {
		this(new EvocodeSettings(), new EvolutionSettings());
	}

	private IslandSettings(final EvocodeSettings evocodeSettings, final EvolutionSettings evolutionSettings) {
		this.evocodeSetting = evocodeSettings;
		this.evolutionSettings = evolutionSettings;
	}

	public int getNumberOfIslands() {
		return evocodeSetting.getNumberOfIslands();
	}

	public int getIndividualsPerIsland(int generation) {
		int generationSize = evolutionSettings.getGenerationSize(generation);
		int numberOfIslands = getNumberOfIslands();
		int individualsPerIsland = (generationSize / numberOfIslands);
		if((individualsPerIsland * numberOfIslands) != generationSize) throw new RuntimeException("Insufficient definition of island number");
		return individualsPerIsland;
	}

	public List<Integer> getIndividualsOnIsland(int islandNumber, int generation) {
		int individualsPerIsland = getIndividualsPerIsland(generation);
		int start = (individualsPerIsland * islandNumber);
		int end = (individualsPerIsland * ++islandNumber);
		return IntStream.range(start, end).boxed().collect(Collectors.toList());
	}
}
