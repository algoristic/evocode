package de.algoristic.evocode;

import de.algoristic.evocode.app.Enviroment;
import de.algoristic.evocode.app.Generation;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.app.conf.FilesystemContext;
import de.algoristic.evocode.app.io.files.GenerationProperties;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.GenomeManager;

public class EvoFight implements Application {

	private final int generation;
	private final int individual;
	private final boolean visualize;

	private final FilesystemContext context;

	public EvoFight(int generation, int individual, boolean visualize) {
		this.generation = generation;
		this.individual = individual;
		this.visualize = visualize;
		context = new FilesystemContext();
	}

	@Override
	public void run() {
		GenerationProperties properties = context.getGenerationProperties(generation);
		String serializedGenome = properties.getGenome(individual);
		GenomeManager manager = new GenomeManager();
		Genetics genetics = manager.getGenetics();
		Genome genome = genetics.readFrom(serializedGenome);
		Individual individual = new Individual(this.generation, this.individual, genome);
		Generation generation = new Generation(this.generation);
		generation.add(individual);
		new Enviroment(visualize).test(generation);
	}

}
