package de.algoristic.evocode.app.periphery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class LazyPropertyFacade {

	private File propertiesFile;
	private Properties inner;

	public LazyPropertyFacade(File propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public Optional<String> getProperty(String key) {
		if (!properties().containsKey(key)) return Optional.empty();
		String value = properties().getProperty(key);
		return Optional.of(value);
	}

	private Properties properties() {
		if(inner == null) {
			inner = new Properties();
			try (InputStream in = new FileInputStream(propertiesFile)) {
				inner.load(in);
			} catch (FileNotFoundException e) {
				System.err.println("Could not find project properties file");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("IOException happened during loading");
				e.printStackTrace();
			}
		}
		return inner;
	}
}
