package de.algoristic.evocode.genetic.nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import static de.algoristic.evocode.util.NumberSystemUtils.randomHexChar;

public class NeuralNetworkProgramming implements Genetics {

	private EvocodeSettings settings;

	public NeuralNetworkProgramming() {
		this(new EvocodeSettings());
	}

	private NeuralNetworkProgramming(EvocodeSettings settings) {
		this.settings = settings;
	}

	@Override
	public Genome initialize() {
		List<NeuralNetworkGene> genes = new ArrayList<>();
		int geneAmount = settings.getGeneAmount();
		int geneLength = calculateGeneLength();
		for (int i = 0; i < geneAmount; i++) {
			StringBuffer buffer = new StringBuffer();
			for(int j = 0; j < geneLength; j++) {
				String hexChar = randomHexChar();
				buffer.append(hexChar);
			}
			String code = buffer.toString();
			NeuralNetworkGene gene = new NeuralNetworkGene(code);
			genes.add(gene);
		}
		Genome genome = new NeuralNetwork(genes);
		return genome;
	}

	public int calculateGeneLength() {
		int neuronIdentifier = calculateNeuronIdentifierLength();
		int sourceTypeIdentifier = 1;
		int sinkTypeIdentifier = 1;
		int weight = 16;

		int binaryGeneLength = 0;
		binaryGeneLength += sourceTypeIdentifier;
		binaryGeneLength += neuronIdentifier;
		binaryGeneLength += sinkTypeIdentifier;
		binaryGeneLength += neuronIdentifier;
		binaryGeneLength += weight;
		binaryGeneLength += (binaryGeneLength % 4);
		int hexGeneLength = (binaryGeneLength / 4);
		return hexGeneLength;
	}

	public int calculateNeuronIdentifierLength() {
		int hiddenNeurons = settings.getHiddenNeurons();
		String binary = Integer.toBinaryString(hiddenNeurons);
		int identifierLength = binary.length();
		return identifierLength;
	}

	public int getSourceTypeIdetifierLength() {
		return 1;
	}

	public int getSinkTypeIdentifierLength() {
		return 1;
	}

	public int getWeightLength() {
		return 16;
	}

	@Override
	public Genome readFrom(String serialized) {
		List<NeuralNetworkGene> genes = Arrays.asList(serialized.split(" "))
			.stream()
			.map(NeuralNetworkGene::new)
			.collect(Collectors.toList());
		Genome genome = new NeuralNetwork(genes);
		return genome;
	}

}
