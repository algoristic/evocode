package de.algoristic.evocode.genetic;

import de.algoristic.evocode.run.EvocodeSettings;
import de.algoristic.evocode.run.EvolutionSettings;

public class GeneticsContext {

	private final EvocodeSettings evocodeSettings;
	private final EvolutionSettings evolutionSettings;

	private GeneticsContext(final EvocodeSettings evocodeSettings, final EvolutionSettings evolutionSettings) {
		this.evocodeSettings = evocodeSettings;
		this.evolutionSettings = evolutionSettings;
	}

	public GeneticsContext() {
		this(new EvocodeSettings(), new EvolutionSettings());
	}
}
