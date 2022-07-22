package de.algoristic.evocode.genetic.nn.encoding;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import de.algoristic.evocode.app.conf.EvocodeSettings;

public class RobotFile {

	private static final String bootstrapFileName = "src/main/resources/NnProgramBootstrap";

	private final EvocodeSettings settings;
	private final RobotBootstrap robot;
	private final String packageName;
	private final String robotName;

	public RobotFile(RobotBootstrap robot, String packageName, String robotName) {
		this.settings = new EvocodeSettings();
		this.robot = robot;
		this.packageName = packageName;
		this.robotName = robotName;
	}

	public File writeTo(Path targetPath) {
		try {
			RobotImplementation implementation = new RobotImplementation(robot);
			String mainMethod = settings.getMainMethod().replace(";", ";\n\t\t");
			String maxTurnAwareness = settings.getMaximumTurnAwareness();
			String javaCode = Files.readString(Paths.get(bootstrapFileName))
				.replace("/*package*/", packageName)
				.replace("/*robotName*/", robotName)
				.replace("/*mainMethod*/", mainMethod)
				.replace("/*maxTurnAwareness*/", maxTurnAwareness)
				.replace("/*neurons*/", implementation.getNeurons())
				.replace("/*connections*/", implementation.getConnections())
				.replace("/*addToLayers*/", implementation.getAddToLayers())
				.replace("/*methods*/", implementation.getMethods());
			Files.writeString(targetPath, javaCode, StandardOpenOption.CREATE);
			return targetPath.toFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
