package de.algoristic.evocode.genetic;

public class DnaProgrammingGene implements Gene {

	private final String startCodon;
	private final String baseChain;

	public DnaProgrammingGene(final String startCodon, final String baseChain) {
		this.startCodon = startCodon;
		this.baseChain = baseChain;
	}

}
