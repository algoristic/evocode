package de.algoristic.evocode.genetic.strategy;

import de.algoristic.evocode.app.EvaluatedGeneration;

public class PopulationManager {

	public Populations determinePopulations(EvaluatedGeneration parents) {
		int filialGeneration = (1 + parents.getGenerationNumber());
		Populations populations = new Populations();
		IslandManager islandManager = new IslandManager();
		MigrationManager migrationManager = new MigrationManager();
		Islands islands = islandManager.getIslands(parents);
		MigrationBehaviour migrationBehaviour = migrationManager.determinMigrationBehaviour(filialGeneration);
		islands.perform(migrationBehaviour);
		for(Island island : islands) {
			Population islandPopulation = island.getPopulation();
			populations.add(islandPopulation);
		}
		return populations;
	}

}
