package de.algoristic.evocode.genetic;

import static de.algoristic.evocode.util.NumberSystemUtils.*;

import java.util.Random;

import de.algoristic.evocode.run.EvocodeSettings;
import de.algoristic.evocode.run.EvolutionSettings;

public class DnaProgramming implements Genetics {

	private final EvocodeSettings evocodeSettings;
	private final EvolutionSettings evolutionSettings;

	private DnaProgramming(final EvocodeSettings evocodeSettings, final EvolutionSettings evolutionSettings) {
		this.evocodeSettings = evocodeSettings;
		this.evolutionSettings = evolutionSettings;
	}

	public DnaProgramming() {
		this(new EvocodeSettings(), new EvolutionSettings());
	}

	@Override
	public Genome initialize() {
		int minGeneSize = evocodeSettings.getMinimumGeneSize();
		int maxGeneSize = evocodeSettings.getMaximumGeneSize();
		int geneAmount = evocodeSettings.getGeneAmount();
		String geneCode = "";
		for(int i = 0; i < geneAmount; i++) {
			String startCodon = randomTerminatorChar();
			geneCode += startCodon;
			int geneSize = minGeneSize + (new Random()).nextInt(maxGeneSize - minGeneSize);
			for(int j = 0; j < geneSize; j++) {
				geneCode += randomHexChar();
			}
		}
		
	}

}
