package de.algoristic.evocode.run;

import java.io.File;
import java.util.concurrent.TimeUnit;

import de.algoristic.evocode.util.SystemPropertyFacade;

public class EvocodeSettings {

	private final SystemPropertyFacade properties;

	public EvocodeSettings() {
		this(new SystemPropertyFacade());
	}

	private EvocodeSettings(SystemPropertyFacade properties) {
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

	public String getIndividualDirectoryPrefix() {
		return properties.getProperty("evo.project.individualDirectoryPrefix")
			.orElse("indiv_");
	}

	public String getRobotNamePattern() {
		return properties.getProperty("evo.project.robotClassName")
			.orElse("EvobotG[generation]I[individual]");
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
		int absoluteMinimum = 5;
		int configuredMinimum = properties.getProperty("evo.genome.dnaProgramming.gene.minSize")
			.map(Integer::valueOf)
			.orElse(5);
		return Math.max(absoluteMinimum, configuredMinimum);
	}

	public int getMaximumGeneSize() {
		return properties.getProperty("evo.genome.dnaProgramming.gene.maxSize")
			.map(Integer::valueOf)
			.orElse(5);
	}

	public boolean areGenesOverlapping() {
		return properties.getProperty("evo.genome.dnaProgramming.gene.overlapping")
			.map(Boolean::valueOf)
			.orElse(true);
	}

	public String getPackageName() {
		return properties.getProperty("evo.project.packageName")
			.orElse("de.algoristic.evocode");
	}

	public String getMainMethod() {
		return properties.getProperty("evo.genome.mainMethod")
			.orElse("");
	}

	public boolean isIslandsEvolution() {
		return properties.getProperty("evo.strategy.name")
			.map(strategy -> "islands".equalsIgnoreCase(strategy))
			.orElse(false);
	}

	public int getNumberOfIslands() {
		return properties.getProperty("evo.strategy.islands.num")
			.map(Integer::valueOf)
			.orElseThrow();
	}

	public String getTerminationCondition() {
		return properties.getProperty("evo.run.termination")
			.orElse("iterations");
	}

	public TimeUnit getTerminatorTimeUnit() {
		return properties.getProperty("evo.run.timer.unit")
			.map(String::toUpperCase)
			.map(TimeUnit::valueOf)
			.orElse(TimeUnit.SECONDS);
	}

	public long getTerminatorTime() {
		return properties.getProperty("evo.run.timer.time")
			.map(Long::valueOf)
			.orElse(60L);
	}
}
