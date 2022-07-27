package de.algoristic.evocode.app.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.algoristic.evocode.app.periphery.SystemPropertyFacade;
import de.algoristic.evocode.genetic.nn.encoding.ActionCategory;

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

	public List<String> getAllowedSensors() {
		return properties.getProperty("evo.genome.nn.allowedSensors")
			.map(specs -> specs.split(","))
			.stream()
			.flatMap(Arrays::stream)
			.map(String::trim)
			.collect(Collectors.toList());
	}

	public List<String> getAllowedActors() {
		return properties.getProperty("evo.genome.nn.allowedActors")
			.map(specs -> specs.split(","))
			.stream()
			.flatMap(Arrays::stream)
			.map(String::trim)
			.collect(Collectors.toList());
	}

	public int getHiddenNeurons() {
		return properties.getProperty("evo.genome.nn.hiddenNeurons")
			.map(Integer::valueOf)
			.orElse(8);
	}

	public double getCategoryMin(ActionCategory category) {
		double defaultMin = category.getAbsMin();
		String name = category.name();
		return getCategoryMin(name, defaultMin);
	}

	public double getCategoryMin(String category, double absMin) {
		double min = properties.getProperty("evo.genome.nn.action." + category + ".min")
			.map(Double::valueOf)
			.orElse(absMin);
		return Math.max(absMin, min);
	}

	public double getCategoryMax(ActionCategory category) {
		double defaultMax = category.getAbsMax();
		String name = category.name();
		return getCategoryMax(name, defaultMax);
	}

	public double getCategoryMax(String category, double absMax) {
		double max = properties.getProperty("evo.genome.nn.action." + category + ".max")
			.map(Double::valueOf)
			.orElse(absMax);
		return Math.min(absMax, max);
	}

	public List<Double> getValueRanges(String category) {
		return properties.getProperty("evo.genome.nn.action." + category + ".ranges")
			.map(specs -> specs.split(","))
			.stream()
			.flatMap(Arrays::stream)
			.map(String::trim)
			.map(Double::valueOf)
			.collect(Collectors.toList());
	}

	public double getWeightFlattener() {
		return properties.getProperty("evo.genome.nn.weightFlattener")
			.map(Double::valueOf)
			.orElse(8192d);
	}

	public String getMaximumTurnAwareness() {
		return properties.getProperty("evo.genome.nn.maxTurnAwareness")
			.map(Integer::valueOf)
			.map(String::valueOf)
			.orElse("10000");
	}

	public String getRobotTemplate() {
		return properties.getProperty("evo.genome.nn.robot.template")
			.orElse("basic");
	}

	public List<String> getMethodBoilerplate(String method) {
		int lines = getMethodBoilerplateLines(method);
		if(lines == 0) return new ArrayList<>();
		return IntStream.rangeClosed(1, lines)
			.mapToObj(i -> properties.getProperty("evo.genome.nn." + method + ".boilerplate." + i))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(Collectors.toList());
	}

	private int getMethodBoilerplateLines(String method) {
		return properties.getProperty("evo.genome.nn." + method + ".boilerplate")
			.map(Integer::valueOf)
			.orElse(0);
	}

	public boolean closeEngineAfterRun() {
		return properties.getProperty("evo.run.closeEngine")
			.map(Boolean::valueOf)
			.orElse(false);
	}

	public boolean onlyCompile() {
		return properties.getProperty("evo.run.onlyCompile")
			.map(Boolean::valueOf)
			.orElse(false);
	}

	public boolean deleteJavaFiles() {
		return properties.getProperty("evo.run.deleteJavaFiles")
			.map(Boolean::valueOf)
			.orElse(true);
	}

	public boolean deleteClassFiles() {
		return properties.getProperty("evo.run.deleteClassFiles")
			.map(Boolean::valueOf)
			.orElse(true);
	}
}
