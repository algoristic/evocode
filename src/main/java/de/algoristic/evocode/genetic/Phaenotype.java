package de.algoristic.evocode.genetic;

import java.io.File;

public class Phaenotype {

	private final File javaFile;
	private final File classFile;

	public Phaenotype(final File javaFile, final File classFile) {
		this.javaFile = javaFile;
		this.classFile = classFile;
	}

	public File getJavaFile() {
		return javaFile;
	}

	public File getClassFile() {
		return classFile;
	}
}
