package de.algoristic.evocode.genetic.strategy;

import de.algoristic.evocode.context.FilesystemContext;
import de.algoristic.evocode.run.EvaluatedGeneration;
import de.algoristic.evocode.run.EvocodeSettings;
import de.algoristic.evocode.run.GenerationProperties;

public class PopulationManager {

	private final EvocodeSettings settings;
	private final FilesystemContext context;

	public PopulationManager() {
		this(new EvocodeSettings(), new FilesystemContext());
	}

	private PopulationManager(final EvocodeSettings settings, final FilesystemContext context) {
		this.settings = settings;
		this.context = context;
	}

	public Populations preparePopulations(EvaluatedGeneration parents) {
		Populations populations = new Populations();
		boolean isIslandsEvolution = settings.isIslandsEvolution();
		if(isIslandsEvolution) {
			int generation = parents.getGenerationNumber();
			int nextGeneration = (generation + 1);
			IslandManager islandManager = new IslandManager();
			MigrationManager migrationManager = new MigrationManager();
			Islands islands = islandManager.getIslands(parents);
			MigrationBehaviour migrationBehaviour = migrationManager.determinMigrationBehaviour();
			islands.perform(migrationBehaviour);
			for(Island island : islands) {
				Population islandPopulation = island.getPopulation();
				populations.add(islandPopulation);
			}
		} else {
			Population population = Population.ofWholeGeneration(parents);
			populations.add(population);
		}
		return populations;
	}

}
