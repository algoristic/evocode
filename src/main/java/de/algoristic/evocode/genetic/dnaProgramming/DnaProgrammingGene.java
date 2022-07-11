package de.algoristic.evocode.genetic.dnaProgramming;

import java.util.Iterator;

import de.algoristic.evocode.genetic.Gene;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.CodonParser;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ControlCodon;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ProgramCodon;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.StartCodon;

public class DnaProgrammingGene implements Gene, Iterable<ProgramCodon> {

	private final String startCode;
	private final CodonParser parser;
	private final String baseChain;

	public DnaProgrammingGene(final String startCode, final String baseChain) {
		this.startCode = startCode;
		this.baseChain = baseChain;
		parser = new CodonParser(baseChain);
	}

	public StartCodon getStartCodon() {
		StartCodon codon = new StartCodon(startCode);
		return codon;
	}

	public ControlCodon getControlCodon() {
		return parser.getControlCodon();
	}

	public String getBaseChain() {
		return baseChain;
	}

	@Override
	public Iterator<ProgramCodon> iterator() {
		return parser;
	}
}
