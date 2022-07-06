package de.algoristic.evocode.run;

public class GenerationBuildingTask {

	private final int generationNumber;

	public GenerationBuildingTask(final int generationNumber) {
		this.generationNumber = generationNumber;
	}

	public int getGenerationNumber() {
		return generationNumber;
	}
	
	public boolean hasAnchestors() {
		return (generationNumber != 0);
	}
}
