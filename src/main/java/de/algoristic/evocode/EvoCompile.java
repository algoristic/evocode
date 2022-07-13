package de.algoristic.evocode;

import de.algoristic.evocode.app.conf.FilesystemContext;
import de.algoristic.evocode.app.io.files.GenerationProperties;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.GenomeManager;
import de.algoristic.evocode.genetic.GenomeTranscriptor;
import de.algoristic.evocode.genetic.GenomeTranslator;
import de.algoristic.evocode.genetic.Genotype;
import de.algoristic.evocode.genetic.Phaenotype;

public class EvoCompile {

	private final int generation;
	private final int individual;

	private final FilesystemContext context;

	public EvoCompile(final int generation, final int individual) {
		this.generation = generation;
		this.individual = individual;
		context = new FilesystemContext(); 
	}

	public void run() {
		GenerationProperties properties = context.getGenerationProperties(generation);
		String serializedGenome = properties.getGenome(individual);
		GenomeManager manager = new GenomeManager();
		Genetics genetics = manager.getGenetics();
		Genome genome = genetics.readFrom(serializedGenome);
		GenomeTranscriptor transcriptor = new GenomeTranscriptor(generation, individual);
		Genotype genotype = transcriptor.transcribe(genome);
		GenomeTranslator translator = new GenomeTranslator();
		translator.setReadyForRun(false);
		Phaenotype phaenotype = translator.translate(genotype, generation);
		System.out.println("Translated Individual[g=" + generation + ", i=" + individual + "] to\n" + phaenotype.getJavaFile() + "\n" + phaenotype.getClassFile());
	}
}
