package de.algoristic.evocode.genetic.alteration;

import java.util.List;

import de.algoristic.evocode.app.conf.EvolutionSettings;

public class AlterationManager {

	private final EvolutionSettings settings;

	public AlterationManager() {
		this(new EvolutionSettings());
	}

	private AlterationManager(EvolutionSettings settings) {
		this.settings = settings;
	}

	public AlterationPipeline getAlterers(int generation) {
		List<String> altererSpecs = settings.getMutatorSpecs(generation);
		return new AlterationPipeline(altererSpecs);
	}
}
