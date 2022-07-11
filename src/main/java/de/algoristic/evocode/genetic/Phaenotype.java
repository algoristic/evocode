package de.algoristic.evocode.genetic;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import de.algoristic.evocode.run.EvocodeSettings;

public class Phaenotype implements Closeable {

	private final int generation;
	private final File javaFile;
	private final File classFile;
	private final EvocodeSettings settings;

	public Phaenotype(final File javaFile, final File classFile, int generation) {
		this.javaFile = javaFile;
		this.classFile = classFile;
		this.generation = generation;
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

	@Override
	public void close() throws IOException {
		// delete resources under /robocode/robots/**
		javaFile.delete();
		classFile.delete();
	}
}
