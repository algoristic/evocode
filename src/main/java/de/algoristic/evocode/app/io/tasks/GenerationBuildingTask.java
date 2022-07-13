package de.algoristic.evocode.app.io.tasks;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import de.algoristic.evocode.app.EvaluatedGeneration;
import de.algoristic.evocode.app.EvaluatedIndividual;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.app.conf.EvolutionSettings;
import de.algoristic.evocode.app.conf.FilesystemContext;
import de.algoristic.evocode.app.io.files.GenerationCSV;
import de.algoristic.evocode.app.io.files.GenerationProperties;
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
		final int parentalGenerationNumber = (generationNumber - 1);
		final int parentalGenerationSize = settings.getGenerationSize(parentalGenerationNumber);

		EvaluatedGeneration parents = new EvaluatedGeneration(parentalGenerationNumber);
		GenomeManager genomeManager = new GenomeManager();
		Genetics genetics = genomeManager.getGenetics();

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

	public void breakdown() {
		File currentDirectoryFile = context.getGenerationDirectory(generationNumber);
		try {
			deleteRecursive(currentDirectoryFile);
		} catch (Exception e) {
			System.err.println("Fatal error: could not breakdown task: " + this);
		}
	}

	@Override
	public String toString() {
		return "[build generation=" + generationNumber + "]";
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

	private void deleteRecursive(File directory) throws IOException {
		FileUtils.deleteDirectory(directory);
	}
}
