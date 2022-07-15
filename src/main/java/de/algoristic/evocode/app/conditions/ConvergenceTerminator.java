package de.algoristic.evocode.app.conditions;

import de.algoristic.evocode.app.conf.EvolutionSettings;
import de.algoristic.evocode.app.conf.FilesystemContext;
import de.algoristic.evocode.app.io.files.GenerationProperties;

public class ConvergenceTerminator extends EvocodeTerminator {

	private final double targetValue;
	private final FilesystemContext context;
	private final EvolutionSettings settings;

	public ConvergenceTerminator(int startGeneration, double targetValue) {
		super(startGeneration);
		this.targetValue = targetValue;
		settings = new EvolutionSettings();
		context = new FilesystemContext();
	}

	@Override
	public boolean hasNext() {
		int lastGeneration = (currentGeneration - 1);
		int generationSize = settings.getGenerationSize(lastGeneration);
		GenerationProperties properties = context.getGenerationProperties(lastGeneration);
		for (int individual = 0; individual < generationSize; individual++) {
			double fitness = properties.getFitness(individual);
			if (targetValue <= fitness) {
				return false;
			}
		}
		return true;
	}
}
