package de.algoristic.evocode.run;

public class RunConditions {

	private final int runIterations;
	private final int startGeneration;

	public RunConditions(int runIterations, int startGeneration) {
		this.runIterations = runIterations;
		this.startGeneration = startGeneration;
	}

	public int getRunIterations() {
		return runIterations;
	}

	public int getStartGeneration() {
		return startGeneration;
	}

	public static int detectRunIterations(ProjectSettings settings) {
		return settings.getRunIterations();
	}

	public static int detectStartGeneration(ProjectSettings settings, ProjectFiles files) {
		int startGeneration = settings.getStartGeneration();
		if (startGeneration != -1) return startGeneration;
		GenerationsFile file = files.getGenerationsFile();
		int lastGrownGeneration = file.getLastGrownGeneration();
		return (++lastGrownGeneration);
	}
}
