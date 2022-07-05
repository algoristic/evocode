package de.algoristic.evocode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.algoristic.evocode.context.EvocodeContext;
import de.algoristic.evocode.run.ProjectFiles;
import de.algoristic.evocode.run.ProjectSettings;
import de.algoristic.evocode.run.RunConditions;
import de.algoristic.evocode.util.PropertyFacade;

public class EvocodeApplication {

	public static void main(String[] args) {
		if (args.length == 0) throw new RuntimeException("No arguments provided; provide on argument for project properties");
		EvocodeApplication application = new EvocodeApplication(args[0]);
		application.runApplication();
	}

	private final String propertiesFileName;

	private EvocodeApplication(final String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
	}

	private void runApplication() {
		EvocodeContext context = prepareContext();
		Evocode evocode = new Evocode(context);
		evocode.run();
	}

	private EvocodeContext prepareContext() {
		ProjectSettings settings = loadProjectSettings();
		ProjectFiles files = loadProjectFiles(settings);
		RunConditions runConditions = determinRunConditions(settings, files);
		EvocodeContext context = new EvocodeContext(settings, files, runConditions);
		return context;
	}

	private ProjectSettings loadProjectSettings() {
		File projectLocation = determineProjectLocation();
		PropertyFacade projectProperties = loadProjectPropertiesFile();
		ProjectSettings settings = new ProjectSettings(projectLocation, projectProperties);
		return settings;
	}

	private File determineProjectLocation() {
		File anchorFile = new File(propertiesFileName);
		File projectDirectory = anchorFile.getParentFile();
		return projectDirectory;
	}

	private PropertyFacade loadProjectPropertiesFile() {
		Properties properties = new Properties();
		try (InputStream in = new FileInputStream(propertiesFileName)) {
			properties.load(in);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find project properties file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException happened during loading");
			e.printStackTrace();
		}
		PropertyFacade projectProperties = new PropertyFacade(properties);
		return projectProperties;
	}
	
	private ProjectFiles loadProjectFiles(ProjectSettings settings) {
		File rootDiretory = settings.getProjectLocation();
		ProjectFiles projectFiles = new ProjectFiles(rootDiretory);
		return projectFiles;
	}
	
	private RunConditions determinRunConditions(ProjectSettings settings, ProjectFiles files) {
		int runIterations = RunConditions.detectRunIterations(settings);
		int startGeneration = RunConditions.detectStartGeneration(settings, files);
		RunConditions runConditions = new RunConditions(runIterations, startGeneration);
		return runConditions;
	}
}
