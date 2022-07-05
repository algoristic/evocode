package de.algoristic.evocode;

import java.io.File;
import java.util.List;

import de.algoristic.evocode.context.EvocodeContext;
import de.algoristic.evocode.run.GenerationProvider;
import de.algoristic.evocode.run.ProjectFiles;
import de.algoristic.evocode.run.RunConditions;

public class Evocode {

	private final EvocodeContext context;

	public Evocode(EvocodeContext context) {
		this.context = context;
	}

	public void run() {
		cleanupDirectory();
	}

	private void cleanupDirectory() {
		RunConditions runConditions = context.getRunConditions();
		int startGeneration = runConditions.getStartGeneration();
		ProjectFiles files = context.getFiles();
		GenerationProvider provider = context.getGenerationProvider();
		List<File> directoriesToBeDeleted = files.getDirectoriesToBeDeleted(startGeneration, provider);
	}
}
