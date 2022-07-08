package de.algoristic.evocode.genetic.dnaProgramming;

import java.util.Iterator;

import de.algoristic.evocode.genetic.Gene;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.CodonParser;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ControlCodon;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ProgramCodon;

public class DnaProgrammingGene implements Gene, Iterable<ProgramCodon> {

	private final String startCodon;
	private final CodonParser parser;

	public DnaProgrammingGene(final String startCodon, final String baseChain) {
		this.startCodon = startCodon;
		parser = new CodonParser(baseChain);
	}

	public String getStartCodon() {
		return startCodon;
	}

	public ControlCodon getControlCodon() {
		return parser.getControlCodon();
	}

	@Override
	public Iterator<ProgramCodon> iterator() {
		return parser;
	}
}
