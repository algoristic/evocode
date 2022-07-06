package de.algoristic.evocode.context;

import java.io.File;
import java.nio.file.Path;

import de.algoristic.evocode.run.EvocodeSettings;

public class GenerationBuildingTaskContext {

	private final EvocodeSettings settings;

	private GenerationBuildingTaskContext(EvocodeSettings settings) {
		this.settings = settings;
	}
	
	public GenerationBuildingTaskContext() {
		this(new EvocodeSettings());
	}

	public File getDirectory(int generationNumber) {
		String directoryName = getGenerationDirecoryName(generationNumber);
		File projectLocation = settings.getProjectLocation();
		Path projectPath = projectLocation.toPath();
		Path generationPath = projectPath.resolve(directoryName);
		File generationLocation = generationPath.toFile();
		return generationLocation;
	}

	private String getGenerationDirecoryName(int generationNumber) {
		String prefix = settings.getGenerationDirectoryPrefix();
		String fullName = (prefix + generationNumber);
		return fullName;
	}
	
}
