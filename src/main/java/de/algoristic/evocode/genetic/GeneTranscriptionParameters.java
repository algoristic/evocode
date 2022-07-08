package de.algoristic.evocode.genetic;

import java.io.File;

public class GeneTranscriptionParameters {

	private final File individualDirectory;

	private final String packageName;
	private final String robotName;

	public GeneTranscriptionParameters(
		final File individualDirectory,
		final String packageName,
		final String robotName) {
		this.individualDirectory = individualDirectory;
		this.packageName = packageName;
		this.robotName = robotName;
	}

	public File getIndividualDirectory() {
		return individualDirectory;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getRobotName() {
		return robotName;
	}
}
