package de.algoristic.evocode.genetic;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import de.algoristic.evocode.run.EvocodeSettings;

public class Phaenotype {

	private final int generation;
	private final File javaFile;
	private final File classFile;
	private final EvocodeSettings settings;

	public Phaenotype(final File javaFile, final File classFile, int generation) {
		this.generation = generation;
		this.javaFile = javaFile;
		this.classFile = classFile;
		settings = new EvocodeSettings();
	}

	public File getJavaFile() {
		return javaFile;
	}

	public File getClassFile() {
		return classFile;
	}

	public int getGeneration() {
		return generation;
	}

	public String getCompetitionName() {
		String fileName = javaFile.getName();
		String robotName = FilenameUtils.getBaseName(fileName);
		String packageName = settings.getPackageName();
		StringBuffer buffer = new StringBuffer(packageName)
			.append(".")
			.append(robotName)
			.append("*");
		return buffer.toString();
	}
}
