package de.algoristic.evocode.run;

import java.io.File;

import de.algoristic.evocode.context.FilesystemContext;

public class GenerationBuildingTask {

	private final FilesystemContext context;
	private final int generationNumber;
	
	public GenerationBuildingTask(final int generationNumber, final FilesystemContext context) {
		this.generationNumber = generationNumber;
		this.context = context;
	}

	public GenerationBuildingTask(final int generationNumber) {
		this(generationNumber, new FilesystemContext());
	}

	public Generation determinePreviousGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getGenerationNumber() {
		return generationNumber;
	}

	public boolean hasAnchestors() {
		return (generationNumber != 0);
	}

	public void prepareDirectory() {
		File generationDirectory = context.getGenerationDirectory(generationNumber);
		if(generationDirectory.exists()) {
			cleanup(generationDirectory);
		} else {
			create(generationDirectory);
		}
	}
	
	private void cleanup(File directory) {
		File[] dismissableContent = directory.listFiles();
		for(File dismissableFile : dismissableContent) {
			dismissableFile.delete();
		}
	}
	
	private void create(File directory) {
		directory.mkdir();
	}
}
