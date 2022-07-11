package de.algoristic.evocode.genetic.dnaProgramming;

import static de.algoristic.evocode.genetic.dnaProgramming.encoding.RobotMethods.randomTerminatorChar;
import static de.algoristic.evocode.util.NumberSystemUtils.randomHexChar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.run.EvocodeSettings;

public class DnaProgramming implements Genetics {

	private final EvocodeSettings evocodeSettings;

	private DnaProgramming(final EvocodeSettings evocodeSettings) {
		this.evocodeSettings = evocodeSettings;
	}

	public DnaProgramming() {
		this(new EvocodeSettings());
	}

	@Override
	public Genome initialize() {
		int minGeneSize = evocodeSettings.getMinimumGeneSize();
		int maxGeneSize = evocodeSettings.getMaximumGeneSize();
		int geneAmount = evocodeSettings.getGeneAmount();
		boolean areGenesOverlapping = evocodeSettings.areGenesOverlapping();

		List<DnaProgrammingGene> genes = new ArrayList<>();
		for (int i = 0; i < geneAmount; i++) {
			String startCodon = randomTerminatorChar();
			int geneSize = minGeneSize + (new Random()).nextInt(maxGeneSize - minGeneSize);
			if ((!areGenesOverlapping) && (geneSize % 2 != 0)) {
				geneSize += 1;
			}
			StringBuffer baseChain = new StringBuffer();
			for (int j = 0; j < geneSize; j++) {
				baseChain.append(randomHexChar());
			}
			DnaProgrammingGene gene = new DnaProgrammingGene(startCodon, baseChain.toString());
			genes.add(gene);
		}
		Genome genome = new DnaProgram(genes);
		return genome;
	}

	@Override
	public Genome readFrom(String serialized) {
		List<DnaProgrammingGene> genes = new ArrayList<>();
		String[] tokens = serialized.split(" ");
		for (String token : tokens) {
			String startCodon = token.substring(0, 1);
			String baseChain = token.substring(1);
			DnaProgrammingGene gene = new DnaProgrammingGene(startCodon, baseChain);
			genes.add(gene);
		}
		Genome genome = new DnaProgram(genes);
		return genome;
	}

}
