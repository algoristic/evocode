package de.algoristic.evocode.genetic.nn.util;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.genetic.nn.NeuralNetworkProgramming;
import de.algoristic.evocode.util.NumberSystemUtils;

public class NNGeneParser {

	private final EvocodeSettings settings;
	private final String binaryCode;
	private int neuronIdentifier;
	private int sourceTypeIdentifier;
	private int sinkTypeIdentifier;
	private int weight;

	public NNGeneParser(String code) {
		this.binaryCode = NumberSystemUtils.hexToBinaryWithLeadingZeroes(code);
		this.settings = new EvocodeSettings();
		NeuralNetworkProgramming genetics = new NeuralNetworkProgramming();
		neuronIdentifier = genetics.calculateNeuronIdentifierLength();
		sourceTypeIdentifier = genetics.getSourceTypeIdetifierLength();
		sinkTypeIdentifier = genetics.getSinkTypeIdentifierLength();
		weight = genetics.getWeightLength();
	}

	public int getSourceTypeIdentifier() {
		return parseBinaryPart(0, sourceTypeIdentifier);
	}

	public int getSourceId() {
		int end = (sourceTypeIdentifier + neuronIdentifier);
		return parseBinaryPart(sourceTypeIdentifier, end);
	}

	public int getSinkTypeIdentifier() {
		int start = (sourceTypeIdentifier + neuronIdentifier);
		int end = (start + sinkTypeIdentifier);
		return parseBinaryPart(start, end);
	}

	public int getSinkId() {
		int start = (sourceTypeIdentifier + neuronIdentifier + sinkTypeIdentifier);
		int end = (start + neuronIdentifier);
		return parseBinaryPart(start, end);
	}

	public double getWeight() {
		int start = (sourceTypeIdentifier + 2 * neuronIdentifier + sinkTypeIdentifier);
		int end = (start + weight);
		double weight = parseBinaryPart(start, end);
		weight -= 32768; // because it's not an signed integer we need this step to produce numbers above and below zero
		double flattener = settings.getWeightFlattener();
		return (weight / flattener);
	}

	private int parseBinaryPart(int start, int end) {
		String binIdentifier = binaryCode.substring(start, end);
		return NumberSystemUtils.binaryToDecimal(binIdentifier);
	}
}
