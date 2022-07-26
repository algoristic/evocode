package de.algoristic.evocode.genetic;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.genetic.dnaProgramming.DnaProgramming;
import de.algoristic.evocode.genetic.nn.NeuralNetworkProgramming;

public class GenomeManager {

	private final EvocodeSettings settings;

	private GenomeManager(final EvocodeSettings settings) {
		this.settings = settings;
	}

	public GenomeManager() {
		this(new EvocodeSettings());
	}

	public Genetics getGenetics() {
		String structure = settings.getGenomeStructure();
		if ("LinearGP.dna".equalsIgnoreCase(structure)) {
			return new DnaProgramming();
		}
		if("LinearGP.nn".equalsIgnoreCase(structure)) {
			return new NeuralNetworkProgramming();
		}
		return null;
	}
}
