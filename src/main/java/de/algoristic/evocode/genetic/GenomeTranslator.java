package de.algoristic.evocode.genetic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.app.periphery.JavaCompilerAdaptor;

public class GenomeTranslator {

	private final JavaCompilerAdaptor compiler;
	private final EvocodeSettings settings;

	private boolean readyForRun = true;

	public GenomeTranslator() {
		compiler = JavaCompilerAdaptor.getInstance();
		settings = new EvocodeSettings();
	}

	public Phaenotype translate(final Genotype genotype, final int generation) {
		final Path javaFileSource = genotype.getJavaFile().toPath();
		final Path classFileSource = compiler.compile(javaFileSource).toPath();

		if (!readyForRun) return new Phaenotype(javaFileSource.toFile(), classFileSource.toFile(), generation);

		final Path robotLocation = settings.getRobocodeLocation().toPath().resolve("robots");
		final Path targetPath = robotLocation.resolve(genotype.getCompilationTargetPathName());
		
		File targetDir = targetPath.toFile();
		if(! targetDir.exists()) targetDir.mkdirs();

		final Path javaFileTarget = targetPath.resolve(javaFileSource.getFileName());
		final Path classFileTarget = targetPath.resolve(classFileSource.getFileName());
		
		try {
			Files.copy(javaFileSource, javaFileTarget);
			Files.copy(classFileSource, classFileTarget);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		javaFileSource.toFile().delete();
		classFileSource.toFile().delete();
		classFileSource.getParent().toFile().delete();
		return new Phaenotype(
			javaFileTarget.toFile(),
			classFileTarget.toFile(),
			generation);
	}

	public void setReadyForRun(final boolean readyForRun) {
		this.readyForRun = readyForRun;
	}
}
