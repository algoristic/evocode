package de.algoristic.evocode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class EvocodeApplication {

	static Options cli = new Options();

	static {
		cli.addOption("p", "project", true, "*.properties");
		cli.addOption("m", "mode", true, "run|compile|test");
		cli.addOption("v", "visualize", false, "display battlefield when -m=test");
		cli.addOption("g", "generation", true, "generation number for -m=compile|test");
		cli.addOption("i", "individual", true, "individual number for -m=compile|test");
		cli.addOption("h", "help", false, "help");
	}

	public static void main(String[] args) {
		CommandLineParser clParser = new DefaultParser();
		try {
			CommandLine cl = clParser.parse(cli, args);
			EvocodeApplication application = new EvocodeApplication(cl);
			application.runApplication();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private final CommandLine command;

	private EvocodeApplication(CommandLine command) {
		this.command = command;
	}

	private void runApplication() throws ParseException {
		if(command.hasOption("help")) {
			HelpFormatter help = new HelpFormatter();
			help.printHelp("evocode", cli);
			return;
		}
		loadProperties();
		String mode = command.getOptionValue("mode");
		Application application;
		switch (mode) {
			case "run":
				application = new Evocode();
				break;
			case "compile":
			case "test":
				int generation = Integer.valueOf(command.getOptionValue("generation"));
				int individual = Integer.valueOf(command.getOptionValue("individual"));
				if(mode.equals("compile")) {
					System.setProperty("evo.run.onlyCompile", "true");
					System.setProperty("evo.run.deleteJavaFiles", "false");
					System.setProperty("evo.run.deleteClassFiles", "false");
					application = new EvoCompile(generation, individual);
				} else {
					boolean visualize = command.hasOption("visualize");
					System.setProperty("evo.run.closeEngine", String.valueOf(visualize));
					application = new EvoFight(generation, individual, visualize);
				}
				break;
			default:
				throw new RuntimeException("Unknown mode: " + mode);
		}
		application.run();
	}

	private void loadProperties() {
		String propertiesFileName = command.getOptionValue("project");

		final String projectLocationKey = "evo.project.location";
		final String projectLocation = determineProjectLocation(propertiesFileName);
		System.setProperty(projectLocationKey, projectLocation);

		Properties projectProperties = loadProjectProperties(propertiesFileName);
		final String projectPropertiesFileKey = "evo.project.file";
		System.setProperty(projectPropertiesFileKey, propertiesFileName);

		Set<Entry<Object, Object>> entries = projectProperties.entrySet();
		for(Entry<Object, Object> entry : entries) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			// TODO check key by whitelist
			System.setProperty(key, value);
		}
	}

	private String determineProjectLocation(String propertiesFileName) {
		File anchorFile = new File(propertiesFileName);
		File projectDirectory = anchorFile.getParentFile();
		String location = projectDirectory.getAbsolutePath();
		return location;
	}

	private Properties loadProjectProperties(String propertiesFileName) {
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
