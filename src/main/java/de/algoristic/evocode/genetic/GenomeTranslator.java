package de.algoristic.evocode.genetic;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.app.periphery.JavaCompilerAdaptor;

public class GenomeTranslator {

	private final JavaCompilerAdaptor compiler;
	private final EvocodeSettings settings;

	public GenomeTranslator() {
		compiler = JavaCompilerAdaptor.getInstance();
		settings = new EvocodeSettings();
	}

	public Phaenotype translate(final Genotype genotype, final int generation) {
		final Path javaFileSource = genotype.getJavaFile().toPath();
		final Path classFileSource = compiler.compile(javaFileSource).toPath();
		File[] classFileArray = classFileSource.getParent().toFile().listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.endsWith(".class");
			}
		});
		final List<File> sourceClassFiles = Arrays.asList(classFileArray);

		final Path robotLocation = settings.getRobocodeLocation().toPath().resolve("robots");
		final Path targetPath = robotLocation.resolve(genotype.getCompilationTargetPathName());
		
		File targetDir = targetPath.toFile();
		if(! targetDir.exists()) targetDir.mkdirs();

		final Path javaFileTarget = targetPath.resolve(javaFileSource.getFileName());
		if(! settings.onlyCompile()) {
			try {
				Files.copy(javaFileSource, javaFileTarget);
				for(File fileSource : sourceClassFiles) {
					String classFileName = fileSource.getName();
					Path targetFile = targetPath.resolve(classFileName);
					Path sourceFile = fileSource.toPath();
					Files.copy(sourceFile, targetFile);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		File parentDirectory = javaFileSource.getParent().toFile();
		boolean deleteJavaFiles = settings.deleteJavaFiles();
		boolean deleteClassFiles = settings.deleteClassFiles();
		if(deleteJavaFiles) {
			javaFileSource.toFile().delete();
		}
		if(deleteClassFiles) {
			sourceClassFiles.forEach(file -> file.delete());
		}
		if(deleteJavaFiles && deleteClassFiles) {
			parentDirectory.delete();
		}
		return new Phaenotype(
			javaFileTarget.toFile(),
			generation);
	}
}
