package de.algoristic.evocode.run;

import java.io.File;

import de.algoristic.evocode.context.GenerationBuildingTaskContext;

public class GenerationBuildingTask {

	private final GenerationBuildingTaskContext context;
	private final int generationNumber;
	
	public GenerationBuildingTask(final int generationNumber, final GenerationBuildingTaskContext context) {
		this.generationNumber = generationNumber;
		this.context = context;
	}

	public GenerationBuildingTask(final int generationNumber) {
		this(generationNumber, new GenerationBuildingTaskContext());
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
		File generationDirectory = context.getDirectory(generationNumber);
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
