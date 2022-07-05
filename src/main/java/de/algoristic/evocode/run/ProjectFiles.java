package de.algoristic.evocode.run;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class ProjectFiles {

	private final Path rootPath;

	public ProjectFiles(Path rootPath) {
		this.rootPath = rootPath;
	}

	public ProjectFiles(File rootDirectoryFile) {
		this(rootDirectoryFile.toPath());
	}

	public GenerationsFile getGenerationsFile() {
		Path path = getGenerationsFilePath();
		boolean exists = exists(path);
		if(exists) return GenerationsFile.load(path);
		GenerationsFile file = GenerationsFile.createNew(path);
		return file;
	}
	
	private Path getGenerationsFilePath() {
		Path filePath = rootPath.resolve("generations.csv");
		return filePath;
	}
	
	private boolean exists(Path path) {
		File convertedPath = path.toFile();
		boolean exists = convertedPath.exists();
		return exists;
	}

	public List<File> getDirectoriesToBeDeleted(int startGeneration, GenerationProvider provider) {
		// check for (startGeneration+[offset]) if generations exist - and DELETE them
		// also rework the access to file naming etc.... provider may be placed badly
	}
}
