package de.algoristic.evocode.run;

import java.io.File;
import java.io.IOException;

import de.algoristic.evocode.context.FilesystemContext;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.GenomeManager;

public class GenerationBuildingTask {

	private final EvolutionSettings settings;
	private final FilesystemContext context;
	private final int generationNumber;

	
	public GenerationBuildingTask(
		final int generationNumber,
		final FilesystemContext context,
		final EvolutionSettings settings) {
		this.generationNumber = generationNumber;
		this.context = context;
		this.settings = settings;
	}

	public GenerationBuildingTask(final int generationNumber) {
		this(generationNumber, new FilesystemContext(), new EvolutionSettings());
	}

	public EvaluatedGeneration determinePreviousGeneration() {
		EvaluatedGeneration parents = new EvaluatedGeneration();
		GenomeManager genomeManager = new GenomeManager();
		Genetics genetics = genomeManager.getGenetics();

		final int parentalGenerationNumber = (generationNumber - 1);
		final int parentalGenerationSize = settings.getGenerationSize(parentalGenerationNumber);

		GenerationProperties properties = context.getGenerationProperties(parentalGenerationNumber);
		for(int individualNumber = 0; individualNumber < parentalGenerationSize; individualNumber++) {
			String serializedGenome = properties.getGenome(individualNumber);
			double fitness = properties.getFitness(individualNumber);
			Genome genome = genetics.readFrom(serializedGenome);
			Individual individual = new Individual(parentalGenerationNumber, individualNumber, genome);
			EvaluatedIndividual parent = new EvaluatedIndividual(individual, fitness);
			parents.add(parent);
		}
		return parents;
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
		GenerationProperties generationProperties = context.getGenerationProperties(generationNumber);
		GenerationCSV generationCSV = context.getGenerationCSV(generationNumber);
		try {
			generationProperties.createIfNotExists();
			generationCSV.createIfNotExists();
		} catch (IOException e) {
			throw new RuntimeException("Could not create props file for generation" + generationNumber, e);
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
