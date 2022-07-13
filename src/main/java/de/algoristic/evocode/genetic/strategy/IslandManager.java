package de.algoristic.evocode.genetic.strategy;

import de.algoristic.evocode.app.EvaluatedGeneration;
import de.algoristic.evocode.app.conf.EvocodeSettings;

public class IslandManager {

	private final EvocodeSettings settings;

	public IslandManager() {
		this(new EvocodeSettings());
	}

	private IslandManager(final EvocodeSettings settings) {
		this.settings = settings;
	}

	public Islands getIslands(final EvaluatedGeneration generation) {
		final int numberOfIslands = settings.getNumberOfIslands();
		return new Islands(numberOfIslands, generation);
	}
}
