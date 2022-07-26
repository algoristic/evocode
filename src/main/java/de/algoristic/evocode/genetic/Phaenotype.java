package de.algoristic.evocode.genetic;

import java.io.Closeable;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import de.algoristic.evocode.app.conf.EvocodeSettings;

public class Phaenotype implements Closeable {

	private final int generation;
	private final File javaFile;
	private final EvocodeSettings settings;

	public Phaenotype(final File javaFile, int generation) {
		this.javaFile = javaFile;
		this.generation = generation;
		settings = new EvocodeSettings();
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
		File directory = javaFile.getParentFile();
		List<File> classFiles = Arrays.asList(directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				String baseFileName = javaFile.getName();
				baseFileName = baseFileName.substring(0, baseFileName.lastIndexOf("."));
				return (name.startsWith(baseFileName) && name.endsWith(".class"));
			}
		}));
		javaFile.delete();
		classFiles.forEach(File::delete);
	}
}
