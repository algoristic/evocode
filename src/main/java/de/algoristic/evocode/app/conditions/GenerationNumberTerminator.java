package de.algoristic.evocode.app.conditions;

import de.algoristic.evocode.app.io.tasks.GenerationBuildingTask;

public class GenerationNumberTerminator implements Terminator {

	private final int lastGeneration;
	private int currentGeneration;

	public GenerationNumberTerminator(final int startGeneration, final int lastGeneration) {
		this.lastGeneration = lastGeneration;
		currentGeneration = startGeneration;
	}

	@Override
	public boolean hasNext() {
		return (currentGeneration < lastGeneration);
	}

	@Override
	public GenerationBuildingTask next() {
		GenerationBuildingTask task = new GenerationBuildingTask(currentGeneration);
		currentGeneration++;
		return task;
	}
}
