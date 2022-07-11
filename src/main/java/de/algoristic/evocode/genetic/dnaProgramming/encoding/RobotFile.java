package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import de.algoristic.evocode.run.EvocodeSettings;

public class RobotFile {

	private static final String bootstrapFileName = "src/main/resources/DnaProgramBootstrap";

	private final EvocodeSettings settings;
	private final RobotMethods methods;
	private final String packageName;
	private final String robotName;

	public RobotFile(final RobotMethods methods, final String packageName, final String robotName) {
		this.settings = new EvocodeSettings();
		this.methods = methods;
		this.packageName = packageName;
		this.robotName = robotName;
	}

	public File writeTo(Path targetPath) {
		try {
			String implementation = methods.resolve();
			String mainMethod = settings.getMainMethod();
			String javaCode = Files.readString(Paths.get(bootstrapFileName))
				.replace("[package]", packageName)
				.replace("[robotName]", robotName)
				.replace("[mainMethod]", mainMethod)
				.replace("[implementation]", implementation);
			Files.writeString(targetPath, javaCode, StandardOpenOption.CREATE);
			return targetPath.toFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
