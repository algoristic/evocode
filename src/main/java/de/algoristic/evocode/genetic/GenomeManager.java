package de.algoristic.evocode.genetic;

import de.algoristic.evocode.genetic.dnaProgramming.DnaProgramming;
import de.algoristic.evocode.run.EvocodeSettings;

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
		if ("LinearGP.dnaProgramming".equalsIgnoreCase(structure)) {
			return new DnaProgramming();
		}
		return null;
	}

}
