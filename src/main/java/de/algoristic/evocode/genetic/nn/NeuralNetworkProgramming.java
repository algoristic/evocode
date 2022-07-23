package de.algoristic.evocode.genetic.nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.nn.encoding.RobotBootstrap;

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

	private Integer _lazy_geneLength;
	public int calculateGeneLength() {
		if(_lazy_geneLength == null) {
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
			_lazy_geneLength = hexGeneLength;
		}
		return _lazy_geneLength;
	}

	private Integer _lazy_neuronIdentifierLength;
	public int calculateNeuronIdentifierLength() {
		if(_lazy_neuronIdentifierLength == null) {
			int maxLayerSize = getBiggestLayer();
			String binary = Integer.toBinaryString(maxLayerSize);
			_lazy_neuronIdentifierLength = binary.length();
			
		}
		return _lazy_neuronIdentifierLength;
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

	private int getBiggestLayer() {
		List<Integer> layerSizes = Arrays.asList(
			numberOfSensors(),
			numberOfHiddenNeurons(),
			numberOfActors());
		Collections.sort(layerSizes, Collections.reverseOrder());
		return layerSizes.get(0);
	}

	private int numberOfHiddenNeurons() {
		return prototype().intermitters().size();
	}

	private int numberOfActors() {
		return prototype().actors().size();
	}

	private int numberOfSensors() {
		return prototype().sensors().size();
	}

	private RobotBootstrap _lazy_bootstrap;
	private RobotBootstrap prototype() {
		if(_lazy_bootstrap == null) {
			_lazy_bootstrap = new RobotBootstrap();
		}
		return _lazy_bootstrap;
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
