package de.algoristic.evocode.app.conditions;

import java.util.concurrent.TimeUnit;

public class TimerTerminator extends EvocodeTerminator {

	private final long endTime;

	public TimerTerminator(final int startGeneration, final long duration, final TimeUnit unit) {
		super(startGeneration);
		endTime = (System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(duration, unit));
	}

	@Override
	public boolean hasNext() {
		return (System.currentTimeMillis() < endTime);
	}
}
