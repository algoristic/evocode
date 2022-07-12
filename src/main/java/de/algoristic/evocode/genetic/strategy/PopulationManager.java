package de.algoristic.evocode.genetic.strategy;

import org.apache.commons.lang3.NotImplementedException;

import de.algoristic.evocode.context.FilesystemContext;
import de.algoristic.evocode.run.EvaluatedGeneration;
import de.algoristic.evocode.run.EvocodeSettings;

public class PopulationManager {

	private final EvocodeSettings settings;
	private final FilesystemContext context;

	public PopulationManager() {
		this(new EvocodeSettings(), new FilesystemContext());
	}

	private PopulationManager(final EvocodeSettings settings, final FilesystemContext context) {
		this.settings = settings;
		this.context = context;
	}

	public Populations getPopulations(EvaluatedGeneration parents) {
		Populations populations = new Populations();
		boolean isIslandsEvolution = settings.isIslandsEvolution();
		if(isIslandsEvolution) {
			throw new NotImplementedException();
//			int generation = parents.getGenerationNumber();
//			GenerationProperties properties = context.getGenerationProperties(generation);
		} else {
			Population population = Population.ofWholeGeneration(parents);
			populations.add(population);
			return populations;
		}
	}

}
