package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.Iterator;

import de.algoristic.evocode.run.EvocodeSettings;

public class CodonParser implements Iterator<ProgramCodon> {

	private final EvocodeSettings settings;
	private String chain;

	public CodonParser(EvocodeSettings settings, String chain) {
		this.settings = settings;
		this.chain = chain;
	}

	public CodonParser(final String chain) {
		this(new EvocodeSettings(), chain);
	}

	public ControlCodon getControlCodon() {
		int delimiter = settings.areGenesOverlapping() ? 2 : 3;
		String codon = chain.substring(0, 3);
		chain = chain.substring(delimiter);
		return new ControlCodon(codon.charAt(0), codon.charAt(1), codon.charAt(2));
	}

	@Override
	public boolean hasNext() {
		int test = settings.areGenesOverlapping() ? 1 : 0;
		return (chain.length() > test);
	}

	@Override
	public ProgramCodon next() {
		int delimiter = settings.areGenesOverlapping() ? 1 : 2;
		String codon = chain.substring(0, 2);
		chain = chain.substring(delimiter);
		return new ProgramCodon(codon);
	}
}
