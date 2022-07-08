package de.algoristic.evocode.genetic;

import java.io.File;

public class Genotype {

	private final File javaFile;
	private final String compilationTargetPathName;
	private final String robotName;

	public Genotype(final File javaFile, final String compilationTargetPathName, final String robotName) {
		this.javaFile = javaFile;
		this.compilationTargetPathName = compilationTargetPathName;
		this.robotName = robotName;
	}

	public File getJavaFile() {
		return javaFile;
	}

	public String getCompilationTargetPathName() {
		return compilationTargetPathName;
	}

	public String getRobotName() {
		return robotName;
	}
}
