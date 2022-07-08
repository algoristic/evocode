package de.algoristic.evocode.context;

import java.io.File;
import java.nio.file.Path;

import de.algoristic.evocode.run.EvocodeSettings;

public class FilesystemContext {

	private final EvocodeSettings settings;

	private FilesystemContext(EvocodeSettings settings) {
		this.settings = settings;
	}

	public FilesystemContext() {
		this(new EvocodeSettings());
	}

	public File getGenerationDirectory(final int generationNumber) {
		final String directoryName = getGenerationDirectoryName(generationNumber);
		final File projectLocation = settings.getProjectLocation();
		final Path projectPath = projectLocation.toPath();
		final Path generationPath = projectPath.resolve(directoryName);
		final File generationLocation = generationPath.toFile();
		return generationLocation;
	}

	public File getIndividualDirectoryName(final int generationNumber, final int individualNumber) {
		final File generationDirectory = getGenerationDirectory(generationNumber);
		final Path generationDirectoryPath = generationDirectory.toPath();
		final String individualDirectoryName = getIndividualDirectoryName(individualNumber);
		final Path individualPath = generationDirectoryPath.resolve(individualDirectoryName);
		final File individualLocation = individualPath.toFile();
		return individualLocation;
	}

	private String getIndividualDirectoryName(final int individualNumber) {
		final String prefix = settings.getIndividualDirectoryPrefix();
		final String fullName = (prefix + individualNumber);
		return fullName;
	}

	private String getGenerationDirectoryName(final int generationNumber) {
		final String prefix = settings.getGenerationDirectoryPrefix();
		final String fullName = (prefix + generationNumber);
		return fullName;
	}
}
