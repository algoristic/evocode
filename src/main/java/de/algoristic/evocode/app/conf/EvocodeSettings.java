package de.algoristic.evocode.app.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import de.algoristic.evocode.app.periphery.SystemPropertyFacade;

public class EvocodeSettings {

	private final SystemPropertyFacade properties;

	public static void reloadSettings() {
		String settingsFileName = new EvocodeSettings().getSettingsFile();
		Properties properties = new Properties();
		try (InputStream in = new FileInputStream(settingsFileName)) {
			properties.load(in);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find project properties file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException happened during loading");
			e.printStackTrace();
		}
		Set<Entry<Object, Object>> entries = properties.entrySet();
		for(Entry<Object, Object> entry : entries) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			System.setProperty(key, value);
		}
	}

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

	public double getConvergenceValue() {
		return properties.getProperty("evo.run.convergence")
			.map(Double::valueOf)
			.orElseThrow();
	}

	public int getNumberOfIslands() {
		return properties.getProperty("evo.strategy.islands.num")
			.map(Integer::valueOf)
			.orElse(1);
	}

	public boolean writeLogfile() {
		return properties.getProperty("evo.run.writeLog")
			.map(Boolean::valueOf)
			.orElse(true);
	}

	public String getSettingsFile() {
		return properties.getProperty("evo.project.file")
			.orElseThrow();
	}
}
