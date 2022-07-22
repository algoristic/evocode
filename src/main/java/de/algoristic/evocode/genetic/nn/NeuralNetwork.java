package de.algoristic.evocode.genetic.nn;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import de.algoristic.evocode.genetic.GeneTranscriptionParameters;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.Genotype;
import de.algoristic.evocode.genetic.nn.encoding.Actor;
import de.algoristic.evocode.genetic.nn.encoding.Connection;
import de.algoristic.evocode.genetic.nn.encoding.Intermitter;
import de.algoristic.evocode.genetic.nn.encoding.ReceivingNeuron;
import de.algoristic.evocode.genetic.nn.encoding.RobotBootstrap;
import de.algoristic.evocode.genetic.nn.encoding.RobotFile;
import de.algoristic.evocode.genetic.nn.encoding.SendingNeuron;
import de.algoristic.evocode.genetic.nn.encoding.Sensor;
import de.algoristic.evocode.util.NumberSystemUtils;

public class NeuralNetwork implements Genome {

	private List<NeuralNetworkGene> genes;

	public NeuralNetwork(List<NeuralNetworkGene> genes) {
		this.genes = genes;
	}

	@Override
	public Genotype transcribe(GeneTranscriptionParameters parameters, String compilationTargetPath) {
		RobotBootstrap robot = decode(genes);
		String packageName = parameters.getPackageName();
		String robotName = parameters.getRobotName();
		RobotFile robotFile = new RobotFile(robot, packageName, robotName);
		Path targetPath = parameters.getIndividualDirectory().toPath().resolve(robotName.concat(".java"));
		File javaFile = robotFile.writeTo(targetPath);
		Genotype genotype = new Genotype(javaFile, compilationTargetPath, robotName);
		return genotype;
	}

	@Override
	public String serialize() {
		return genes.stream()
			.map(NeuralNetworkGene::getCode)
			.collect(Collectors.joining(" "));
	}

	@Override
	public Genome crossover(Genome other) {
		int max = genes.size();
		int crossoverPoint = (new Random()).nextInt(max);
		NeuralNetwork otherNn = (NeuralNetwork) other;
		List<NeuralNetworkGene> offspringDna = this.copyGenesUntil(genes, crossoverPoint);
		List<NeuralNetworkGene> secondPart = otherNn.copyGenesFrom(otherNn.genes, crossoverPoint);
		offspringDna.addAll(secondPart);
		return new NeuralNetwork(genes);
	}

	@Override
	public Genome mutate(String mutatorSpec, double mutationRate) {
		String serialized = serialize();
		StringBuffer copyBuffer = new StringBuffer();
		for(int point = 0; point < serialized.length(); point++) {
			char c = serialized.charAt(point);
			if(' ' == c) {
				copyBuffer.append(c);
			} else {
				String binaryString = NumberSystemUtils.hexToBinary(c);
				binaryString = NumberSystemUtils.binaryFlip(binaryString, mutationRate);
				String hexString = NumberSystemUtils.binaryToHex(binaryString);
				copyBuffer.append(hexString);
			}
		}
		String alteredCopy = copyBuffer.toString();
		Genetics genetics = new NeuralNetworkProgramming();
		Genome alteredGenome = genetics.readFrom(alteredCopy);
		return alteredGenome ;
	}

	private RobotBootstrap decode(List<NeuralNetworkGene> genes) {
		RobotBootstrap robot = new RobotBootstrap();
		List<Sensor> sensors = robot.sensors();
		List<Intermitter> intermitters = robot.intermitters();
		List<Actor> actors = robot.actors();
		for(NeuralNetworkGene gene : genes) {
			int srcTypeIdentifier = gene.getSourceTypeIdentifier();
			int srcId = gene.getSourceId();
			SendingNeuron source;
			{
				List<SendingNeuron> senders;
				if(srcTypeIdentifier == 0) senders = new ArrayList<>(sensors);
				else senders = new ArrayList<>(intermitters);
				int pos = (srcId % senders.size());
				source = senders.get(pos);
			}
			int sinkTypeIdentifier = gene.getSinkTypeIdentifier();
			int sinkId = gene.getSinkId();
			ReceivingNeuron receiver;
			{
				List<ReceivingNeuron> receivers;
				if(sinkTypeIdentifier == 0) receivers = new ArrayList<>(actors);
				else receivers = new ArrayList<>(intermitters);
				int pos = (sinkId % receivers.size());
				receiver = receivers.get(pos);
			}
			double weight = gene.getConnectionWeight();
			source.addConnection(new Connection(weight, receiver));
		}
		return robot;
	}
}
