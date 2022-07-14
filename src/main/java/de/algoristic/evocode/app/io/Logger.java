package de.algoristic.evocode.app.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.app.conf.FilesystemContext;

public class Logger {

	private static Map<String, Logger> LOGGER_REGISTRY = new HashMap<>();

	public static Logger getLogger(Class<?> caller) {
		return getLogger(caller.getName());
	}

	public static Logger getLogger(String caller) {
		Logger logger = LOGGER_REGISTRY.get(caller);
		if(logger == null) {
			logger = new Logger(caller);
			LOGGER_REGISTRY.put(caller, logger);
		}
		return logger;
	}

	private final String caller;
	private final PrintStream out;

	private Path logFile;


	private Logger(String caller) {
		this(caller, System.out);
	}

	private Logger(String caller, PrintStream out) {
		this.out = out;
		this.caller = caller;
		setup();
	}

	public void write(String msg) {
		msg = (caller + ": " + msg);
		out.println(msg);
		if(writeLogFile()) {
			if(! msg.endsWith("\n")) msg += "\n";
			try {
				Files.writeString(logFile, msg, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setup() {
		FilesystemContext context = new FilesystemContext();
		File file = context.getLogfile();
		if(! file.exists() && writeLogFile()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("Was unable to create log file", e);
			}
		}
		logFile = file.toPath();
	}

	private Boolean lazySetting = null;
	private boolean writeLogFile() {
		if(lazySetting == null) {
			EvocodeSettings settings = new EvocodeSettings();
			lazySetting = settings.writeLogfile();
		}
		return lazySetting;
	}
}
