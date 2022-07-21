package de.algoristic.evocode.genetic.nn;

import de.algoristic.evocode.genetic.Gene;
import de.algoristic.evocode.genetic.nn.util.NNGeneParser;

public class NeuralNetworkGene implements Gene {

	private final String code;
	private NNGeneParser parser;

	public NeuralNetworkGene(String code) {
		this.code = code;
		parser = new NNGeneParser(code);
	}

	public String getCode() {
		return code;
	}

	public int getSourceTypeIdentifier() {
		return parser.getSourceTypeIdentifier();
	}

	public int getSinkTypeIdentifier() {
		return parser.getSinkTypeIdentifier();
	}

	public int getSourceId() {
		return parser.getSourceId();
	}

	public int getSinkId() {
		return parser.getSinkId();
	}

	public double getConnectionWeight() {
		return parser.getWeight();
	}
}
