package de.algoristic.evocode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class EvocodeApplication {

	public static void main(String[] args) {
		if (args.length == 0) throw new RuntimeException("No arguments provided; provide on argument for project properties");
		EvocodeApplication application;
		if(args.length == 1) application = new EvocodeApplication(args[0]);
		else application = new EvocodeApplication(args[0], args[1], args[2]);
		application.runApplication();
	}

	private final String propertiesFileName;

	private String compileGen;
	private String compileIndiv;

	private EvocodeApplication(final String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
	}

	private EvocodeApplication(
		final String propertiesFileName,
		final String compileGen,
		final String compileIndiv) {
		this(propertiesFileName);
		this.compileGen = compileGen;
		this.compileIndiv = compileIndiv;
	}

	private void runApplication() {
		loadProperties();
		if(compileMode()) {
			int generation = Integer.valueOf(compileGen);
			int individual = Integer.valueOf(compileIndiv);
			EvoCompile evoCompile = new EvoCompile(generation, individual);
			evoCompile.run();
		} else {
			Evocode evocode = new Evocode();
			evocode.run();
		}
	}

	private boolean compileMode() {
		return ((compileGen != null) && (compileIndiv != null));
	}

	private void loadProperties() {
		final String projectLocationKey = "evo.project.location";
		final String projectLocation = determineProjectLocation();
		System.setProperty(projectLocationKey, projectLocation);

		Properties projectProperties = loadProjectProperties();
		Set<Entry<Object, Object>> entries = projectProperties.entrySet();
		for(Entry<Object, Object> entry : entries) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			// TODO check key by whitelist
			System.setProperty(key, value);
		}
	}

	private String determineProjectLocation() {
		File anchorFile = new File(propertiesFileName);
		File projectDirectory = anchorFile.getParentFile();
		String location = projectDirectory.getAbsolutePath();
		return location;
	}

	private Properties loadProjectProperties() {
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
		return properties;
	}
}
