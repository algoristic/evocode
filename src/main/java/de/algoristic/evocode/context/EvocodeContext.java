package de.algoristic.evocode.context;

import de.algoristic.evocode.run.GenerationProvider;
import de.algoristic.evocode.run.ProjectFiles;
import de.algoristic.evocode.run.ProjectSettings;
import de.algoristic.evocode.run.RunConditions;

public class EvocodeContext {

	private final ProjectSettings settings;
	private final ProjectFiles files;
	private final RunConditions runConditions;

	public EvocodeContext(ProjectSettings settings, ProjectFiles files, RunConditions runConditions) {
		this.settings = settings;
		this.files = files;
		this.runConditions = runConditions;
	}

	public ProjectSettings getSettings() {
		return settings;
	}

	public ProjectFiles getFiles() {
		return files;
	}

	public RunConditions getRunConditions() {
		return runConditions;
	}

	public GenerationProvider getGenerationProvider() {
		String directoryPattern = settings.getGenerationDirectoryPattern();
		return new GenerationProvider(directoryPattern);
	}
}
