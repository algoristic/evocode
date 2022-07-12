package de.algoristic.evocode.run;

import java.util.concurrent.TimeUnit;

public class TimerTerminator implements Terminator {

	private final long endTime;
	private int currentGeneration;
	
	public TimerTerminator(final int startGeneration, final long duration, final TimeUnit unit) {
		currentGeneration = startGeneration;
		endTime = (System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(duration, unit));
	}

	@Override
	public boolean hasNext() {
		return (System.currentTimeMillis() < endTime);
	}

	@Override
	public GenerationBuildingTask next() {
		GenerationBuildingTask task = new GenerationBuildingTask(currentGeneration);
		currentGeneration++;
		return task;
	}

}
