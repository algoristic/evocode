package de.algoristic.evocode.genetic.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import de.algoristic.evocode.app.EvaluatedGeneration;
import de.algoristic.evocode.app.EvaluatedIndividual;

public class Islands implements Iterable<Island> {

	private final int numberOfIslands;
	private final EvaluatedGeneration generation;
	private final IslandSettings settings;
	private List<ProtoIsland> protoIslands;

	public Islands(final int numberOfIslands, final EvaluatedGeneration generation) {
		this.numberOfIslands = numberOfIslands;
		this.generation = generation;
		settings = new IslandSettings();
		protoIslands = new ArrayList<>();
		init();
	}

	@Override
	public Iterator<Island> iterator() {
		List<Island> islands = new ArrayList<>();
		for(ProtoIsland island : protoIslands) {
			List<Integer> individuals = island.getIndividualIDs();
			List<EvaluatedIndividual> evaluatedIndividuals = new ArrayList<>();
			
			for(Integer individual : individuals) {
				EvaluatedIndividual evaluatedIndividual = generation.get(individual);
				evaluatedIndividuals.add(evaluatedIndividual);
			}
			Population islandPopulation = new Population(evaluatedIndividuals);
			islands.add(new Island(islandPopulation));
		}
		return islands.iterator();
	}

	public void perform(MigrationBehaviour migrationBehaviour) {
		List<ProtoIsland> afterMigration = new ArrayList<>();
		IntStream.range(0, numberOfIslands).forEach(id -> afterMigration.add(new ProtoIsland(id)));
		int nextGeneration = (1 + generation.getGenerationNumber());

		// FIXME this needs refactoring for better readability
		for (ProtoIsland island : protoIslands) {
			int currentIslandId = island.getId();
			List<Integer> individuals = island.getIndividualIDs();
			for (Migration migration : migrationBehaviour) {
				if (migration.isApplicable(nextGeneration)) {
					int start = 0;
					Integer size = individuals.size();
					migration:
					for(int i = start; i < size; i++) {
						if(migration.migrate()) {
							int targetIsland = migration.getTargetIsland(currentIslandId, numberOfIslands);
							int individual = individuals.remove(i);
							afterMigration.get(targetIsland).add(individual);
							size--;
							start = (i + 1);
							continue migration;
						}
					}
				}
			}
			afterMigration.get(currentIslandId).addAll(individuals);
		}
		protoIslands = afterMigration;
	}

	private void init() {
		final int generationNumber = generation.getGenerationNumber();
		for(int island = 0; island < numberOfIslands; island++) {
			List<Integer> individuals = settings.getIndividualsOnIsland(island, generationNumber);
			protoIslands.add(new ProtoIsland(island, individuals));
		}
	}

	private class ProtoIsland {
		private final int id;
		private final List<Integer> individualIDs;

		public ProtoIsland(int id) {
			this(id, new ArrayList<>());
		}

		public ProtoIsland(int id, List<Integer> individualIDs) {
			this.id = id;
			this.individualIDs = individualIDs;
		}

		public void add(int individual) {
			individualIDs.add(individual);
		}

		public void addAll(List<Integer> individuals) {
			this.individualIDs.addAll(individuals);
		}

		public List<Integer> getIndividualIDs() {
			return individualIDs;
		}

		public int getId() {
			return id;
		}
	}
}
