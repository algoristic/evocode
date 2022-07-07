package de.algoristic.evocode.run;

import java.io.File;

import de.algoristic.evocode.util.PropertyFacade;

public class EvocodeSettings {

	private final PropertyFacade properties;

	public EvocodeSettings() {
		this(new PropertyFacade());
	}

	private EvocodeSettings(PropertyFacade properties) {
		this.properties = properties;
	}

	public File getProjectLocation() {
		return properties.getProperty("evo.project.location")
			.map(File::new)
			.orElseThrow();
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

	public String getGenerationDirectoryPrefix() {
		return properties.getProperty("evo.project.generationDirectoryPrefix")
			.orElse("gen_");
	}

	public int getRunIterations() {
		return properties.getProperty("evo.run.iterations")
			.map(Integer::parseInt)
			.orElse(1);
	}
	
	public int getStartGeneration() {
		return properties.getProperty("evo.run.startGeneration")
			.map(Integer::parseInt)
			.orElse(-1);
	}

	public String getGenomeStructure() {
		return properties.getProperty("evo.genome.structure")
			.orElse("LinearGP.dnaProgramming");
	}

	public int getGeneAmount() {
		return properties.getProperty("evo.genome.genes")
			.map(Integer::valueOf)
			.orElse(1);
	}

	public int getMinimumGeneSize() {
		return 8;
	}

	public int getMaximumGeneSize() {
		return properties.getProperty("evo.genome.gene.maxSize")
			.map(Integer::valueOf)
			.orElse(8);
	}
}
