package de.algoristic.evocode.app.conf;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import de.algoristic.evocode.app.conditions.ConvergenceTerminator;
import de.algoristic.evocode.app.conditions.GenerationNumberTerminator;
import de.algoristic.evocode.app.conditions.Terminator;
import de.algoristic.evocode.app.conditions.TimerTerminator;
import de.algoristic.evocode.app.io.tasks.GenerationBuildingTask;
import de.algoristic.evocode.app.io.tasks.ProjectSetupTask;

public class EvocodeContext {
	
	private final EvocodeSettings settings;
	
	public EvocodeContext() {
		this(new EvocodeSettings());
	}
	
	private EvocodeContext(final EvocodeSettings settings) {
		this.settings = settings;
	}

	public ProjectSetupTask getSetupTask() {
		return new ProjectSetupTask();
	}

	public Terminator getTerminator() {
		String terminatorName = settings.getTerminationCondition();
		Terminator terminator = getInstance(terminatorName);
		return terminator;
	}

	@Deprecated
	public List<GenerationBuildingTask> getBuildingTasks() {
		List<GenerationBuildingTask> tasks = new ArrayList<>();
		int startGeneration = determineStartGeneration();
		int lastGeneration = calculateLastGeneration(startGeneration);
		for(int generation = startGeneration; generation < lastGeneration; generation++) {
			GenerationBuildingTask task = new GenerationBuildingTask(generation);
			tasks.add(task);
		}
		return tasks;
	}

	private int calculateLastGeneration(int startGeneration) {
		int iterations = settings.getRunIterations();
		return (startGeneration + iterations);
	}

	private int determineStartGeneration() {
		int startGeneration = readStartGenerationFromSettings();
		if(isUndetermined(startGeneration)) {
			startGeneration = detectStartGenerationFromFileSystem();
		}
		return startGeneration;
	}

	private int readStartGenerationFromSettings() {
		return settings.getStartGeneration();
	}
	
	private boolean isUndetermined(int startGeneration) {
		return (startGeneration == -1);
	}
	
	private int detectStartGenerationFromFileSystem() {
		// TODO detect a VALID generation, not empty directories...
		File projectLocation = settings.getProjectLocation();
		String generationDirectoryPrefix = settings.getGenerationDirectoryPrefix();
		List<Integer> presentGenerations = new ArrayList<>();
		Consumer<Integer> filterCallback = generation -> presentGenerations.add(generation);
		projectLocation.list(new GenerationDirectoryFilter(generationDirectoryPrefix, filterCallback));
		return presentGenerations.stream()
			.mapToInt(Integer::intValue)
			.map(i -> i + 1)
			.max()
			.orElse(0);
	};

	private Terminator getInstance(String terminator) {
		int startGeneration = determineStartGeneration();
		if("iterations".equalsIgnoreCase(terminator)) {
			int lastGeneration = calculateLastGeneration(startGeneration);
			return new GenerationNumberTerminator(startGeneration, lastGeneration);
		}
		if("timer".equalsIgnoreCase(terminator)) {
			TimeUnit timeUnit = settings.getTerminatorTimeUnit();
			long duration = settings.getTerminatorTime();
			return new TimerTerminator(startGeneration, duration, timeUnit);
		}
		if("convergence".equalsIgnoreCase(terminator)) {
			double convergenceValue = settings.getConvergenceValue();
			return new ConvergenceTerminator(startGeneration, convergenceValue);
		}
		throw new IllegalArgumentException("Insufficient terminator definition: " + terminator);
	}

	private static class GenerationDirectoryFilter implements FilenameFilter {
		
		private final String prefix;
		private final Consumer<Integer> callback;

		public GenerationDirectoryFilter(final String prefix, final Consumer<Integer> callback) {
			this.prefix = prefix;
			this.callback = callback;
		}

		@Override
		public boolean accept(File dir, String name) {
			if(! name.startsWith(prefix)) return false;
			int prefixLength = prefix.length();
			String generationDenominator = name.substring(prefixLength);
			Integer generationNumber = Integer.valueOf(generationDenominator);
			callback.accept(generationNumber);
			return true;
		}
		
	}
}
