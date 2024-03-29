package de.algoristic.evocode.app.conf;

import java.io.File;
import java.nio.file.Path;

import de.algoristic.evocode.app.io.files.GenerationCSV;
import de.algoristic.evocode.app.io.files.GenerationProperties;
import de.algoristic.evocode.app.io.files.IslandsCSV;
import de.algoristic.evocode.app.io.files.ProjectCSV;

public class FilesystemContext {

	private final EvocodeSettings settings;

	private FilesystemContext(EvocodeSettings settings) {
		this.settings = settings;
	}

	public FilesystemContext() {
		this(new EvocodeSettings());
	}

	public File getLogfile() {
		File projectLocation = settings.getProjectLocation();
		File projectLogFile = projectLocation.toPath().resolve("evolution.log").toFile();
		return projectLogFile;
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

	public ProjectCSV getProjectCSV() {
		File projectLocation = settings.getProjectLocation();
		File projectCSVFile = projectLocation.toPath().resolve("project.csv").toFile();
		return new ProjectCSV(projectCSVFile);
	}

	public IslandsCSV getIslandsCSV() {
		File projectLocation = settings.getProjectLocation();
		File islandsCSVFile = projectLocation.toPath().resolve("islands.csv").toFile();
		return new IslandsCSV(islandsCSVFile);
	}

	public GenerationProperties getGenerationProperties(int generationNumber) {
		File generationDirectory = getGenerationDirectory(generationNumber);
		File generationPropertiesFile = generationDirectory.toPath().resolve("generation.properties").toFile();
		return new GenerationProperties(generationPropertiesFile);
	}

	public GenerationCSV getGenerationCSV(int generationNumber) {
		File generationDirectory = getGenerationDirectory(generationNumber);
		File generationCSVFile = generationDirectory.toPath().resolve("generation.csv").toFile();
		return new GenerationCSV(generationCSVFile);
	}
}
