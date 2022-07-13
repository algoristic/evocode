package de.algoristic.evocode.genetic.strategy;

import java.util.List;

import de.algoristic.evocode.app.conf.EvocodeSettings;

public class MigrationManager {

	private final EvocodeSettings settings;

	private MigrationManager(final EvocodeSettings settings) {
		this.settings = settings;
	}

	public MigrationManager() {
		this(new EvocodeSettings());
	}

	public MigrationBehaviour determinMigrationBehaviour() {
		MigrationBehaviour migrationBehaviour = new MigrationBehaviour();
		List<String> migrationSpecs = settings.getMigrationSpecs();
		for (String spec : migrationSpecs) {
			Migration migration = getInstance(spec);
			migrationBehaviour.add(migration);
		}
		return migrationBehaviour;
	}

	private Migration getInstance(String spec) {
		final int epoch = settings.getMigrationEpoch(spec);
		final double chance = settings.getMigrationChance(spec);
		if ("ring".equalsIgnoreCase(spec)) {
			return new RingMigration(epoch, chance);
		}
		if ("random".equalsIgnoreCase(spec)) {
			return new RandomMigration(epoch, chance);
		}
		throw new IllegalArgumentException("Insufficient migration ehaviour: " + spec);
	}
}
