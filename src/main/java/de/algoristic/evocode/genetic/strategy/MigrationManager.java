package de.algoristic.evocode.genetic.strategy;

import java.util.List;

import de.algoristic.evocode.app.conf.EvolutionSettings;

public class MigrationManager {

	private final EvolutionSettings settings;

	private MigrationManager(final EvolutionSettings settings) {
		this.settings = settings;
	}

	public MigrationManager() {
		this(new EvolutionSettings());
	}

	public MigrationBehaviour determinMigrationBehaviour(int generation) {
		MigrationBehaviour migrationBehaviour = new MigrationBehaviour();
		List<String> migrationSpecs = settings.getMigrationSpecs(generation);
		for (String spec : migrationSpecs) {
			Migration migration = getInstance(generation, spec);
			migrationBehaviour.add(migration);
		}
		return migrationBehaviour;
	}

	private Migration getInstance(int generation, String spec) {
		final int epoch = settings.getMigrationEpoch(generation, spec);
		final double chance = settings.getMigrationChance(generation, spec);
		if ("ring".equalsIgnoreCase(spec)) {
			return new RingMigration(epoch, chance);
		}
		if ("random".equalsIgnoreCase(spec)) {
			return new RandomMigration(epoch, chance);
		}
		throw new IllegalArgumentException("Insufficient migration ehaviour: " + spec);
	}
}
