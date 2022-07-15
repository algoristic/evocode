package de.algoristic.evocode.app.conditions;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.app.io.tasks.GenerationBuildingTask;

public abstract class EvocodeTerminator implements Terminator {

	protected int currentGeneration;

	protected EvocodeTerminator(int startGeneration) {
		currentGeneration = startGeneration;
	}

	@Override
	public GenerationBuildingTask next() {
		EvocodeSettings.reloadSettings();
		GenerationBuildingTask task = new GenerationBuildingTask(currentGeneration);
		currentGeneration++;
		return task;
	}
}
