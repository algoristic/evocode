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

	public DnaProgrammingGene(final String startCode, final String baseChain) {
		this.startCode = startCode;
		parser = new CodonParser(baseChain);
	}

	public StartCodon getStartCodon() {
		StartCodon codon = new StartCodon(startCode);
		return codon;
	}

	public ControlCodon getControlCodon() {
		return parser.getControlCodon();
	}

	@Override
	public Iterator<ProgramCodon> iterator() {
		return parser;
	}
}
