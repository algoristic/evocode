package de.algoristic.evocode.run;

import java.io.File;

import de.algoristic.evocode.util.PropertyFacade;

public class ProjectSettings {

	private final File projectLocation;
	private final PropertyFacade properties;

	public ProjectSettings(final File projectLocation, final PropertyFacade properties) {
		this.projectLocation = projectLocation;
		this.properties = properties;
	}

	public File getProjectLocation() {
		return projectLocation;
	}

	public File getRobocodeLocation() {
		return properties.getProperty("evo.project.robocodeLocation")
			.map(File::new)
			.orElseThrow();
	}
	
	public String getProjectName() {
		return properties.getProperty("evo.project.name")
			.orElse("n. a.");
	}

	public String getGenerationDirectoryPattern() {
		return properties.getProperty("evo.project.generationDirectoryName")
			.orElse("gen_{{generation}}");
	}

	int getRunIterations() {
		return properties.getProperty("evo.run.iterations")
			.map(Integer::parseInt)
			.orElse(1);
	}
	
	int getStartGeneration() {
		return properties.getProperty("evo.run.startGeneration")
			.map(Integer::parseInt)
			.orElse(-1);
	}
}
