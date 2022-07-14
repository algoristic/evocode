package de.algoristic.evocode.genetic.dnaProgramming;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import de.algoristic.evocode.app.io.Logger;
import de.algoristic.evocode.genetic.GeneTranscriptionParameters;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.Genotype;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.Actor;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.Actors;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ControlCodon;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ControlStructure;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ProgramCodon;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.RobotFile;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.RobotMethod;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.RobotMethods;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.Sensor;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.Sensors;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.StartCodon;
import de.algoristic.evocode.util.NumberSystemUtils;

public class DnaProgram implements Genome {

	private static Logger log = Logger.getLogger(DnaProgram.class);

	private final List<DnaProgrammingGene> genes;

	public DnaProgram(final List<DnaProgrammingGene> genes) {
		this.genes = genes;
	}

	@Override
	public Genotype transcribe(GeneTranscriptionParameters parameters, String compilationTargetPath) {
		RobotMethods methodSet = decode(genes);
		String packageName = parameters.getPackageName();
		String robotName = parameters.getRobotName();
		RobotFile robotFile = new RobotFile(methodSet, packageName, robotName);
		Path targetPath = parameters.getIndividualDirectory().toPath().resolve(robotName.concat(".java"));
		File javaFile = robotFile.writeTo(targetPath);
		Genotype genotype = new Genotype(javaFile, compilationTargetPath, robotName);
		return genotype;
	}

	private RobotMethods decode(List<DnaProgrammingGene> genes) {
		RobotMethods methodSet = new RobotMethods();
		for(DnaProgrammingGene gene : genes) {
			// retrive the method to be implemented by the start codon
			StartCodon startCodon = gene.getStartCodon();
			RobotMethod method = methodSet.getMethod(startCodon);
			// determine the control stucture and its operands by the first,
			// so called, control codon
			ControlCodon controlCodon = gene.getControlCodon();
			Sensor leftOperand = Sensors.getLeftOperand(controlCodon);
			Sensor rightOperand = Sensors.getRightOperand(controlCodon);
			ControlStructure controlStructure = ControlStructure.of(controlCodon, leftOperand, rightOperand);
			// determine the actors (= called methods) by the rest of the gene
			List<Actor> actors = new ArrayList<>();
			for(ProgramCodon codon : gene) {
				Actor actor = Actors.getActor(codon);
				actors.add(actor);
			}
			controlStructure.wrap(actors);
			method.add(controlStructure);
		}
		return methodSet;
	}

	@Override
	public String serialize() {
		return serialize(genes);
	}

	/**
	 * MEMO dieses Schema, also die Aufteilung in Gene, die dann per crossover kombiniert werden etc.,
	 *      kann ich an anderer Stelle, wenn ich einen zweiten Typ DNA einbaue einfach abstrahieren und
	 *      wiederverwenden. (Das generische Interface &lt;Gene&gt; gibt es ja bereits...
	 * */
	@Override
	public Genome crossover(Genome other) {
		if(other == null) throw new NullPointerException();
		if(! (other instanceof DnaProgram)) throw new RuntimeException("Fatal: trying to crossover DnaProgram and " + other.getClass().getSimpleName());
		
		int max = genes.size();
		int crossoverPoint = (new Random()).nextInt(max);

		DnaProgram otherDna = (DnaProgram) other;
		List<DnaProgrammingGene> offspringDna = this.copyGenesUntil(crossoverPoint);
		List<DnaProgrammingGene> secondPart = otherDna.copyGenesFrom(crossoverPoint);
		log.write("Crossover          :");
		log.write("   Crossover point : " + crossoverPoint);
		log.write("   Parent 1        : " + this);
		log.write("      Selected part: " + serialize(offspringDna));
		log.write("   Parent 2        : " + other);
		log.write("      Selected part: " + serialize(secondPart));
		offspringDna.addAll(secondPart);
		log.write("   Result/Child    : " + serialize(offspringDna));
		return new DnaProgram(offspringDna);
	}

	/**
	 * Currently uses only point mutation to mutate a genome. Other mutation types like
	 * swap are reserved for genomes where it is expected for each gene to appear just once.
	 * Those mutations are insufficient in creating enough randomness in this type of genome.
	 * */
	@Override
	public Genome mutate(String mutatorSpec, double mutationRate) {
		List<Character> ignoreList = RobotMethods.terminatorChars()
			.stream()
			.map(s -> s.charAt(0))
			.collect(Collectors.toList());
		ignoreList.add(' ');
		String serialized = serialize();
		log.write("Mutate (" + mutatorSpec + ", " + mutationRate + "):");
		log.write("   Before: " + serialized);
		StringBuffer copyBuffer = new StringBuffer();
		for(int point = 0; point < serialized.length(); point++) {
			char c = serialized.charAt(point);
			if(ignoreList.contains(c)) {
				copyBuffer.append(c);
			} else {
				String binaryString = NumberSystemUtils.hexToBinary(c);
				binaryString = NumberSystemUtils.binaryFlip(binaryString, mutationRate);
				String hexString = NumberSystemUtils.binaryToHex(binaryString);
				copyBuffer.append(hexString);
			}
		}
		String alteredCopy = copyBuffer.toString();
		log.write("   After : " + alteredCopy);
		Genetics genetics = new DnaProgramming();
		Genome alteredGenome = genetics.readFrom(alteredCopy);
		return alteredGenome ;
	}

	@Override
	public String toString() {
		return serialize();
	}

	private List<DnaProgrammingGene> copyGenesFrom(int crossoverInclusive) {
		List<DnaProgrammingGene> copy = new ArrayList<>();
		for(int index = crossoverInclusive; index < genes.size(); index++) {
			DnaProgrammingGene gene = genes.get(index);
			copy.add(gene);
		}
		return copy;
	}

	private List<DnaProgrammingGene> copyGenesUntil(int crossoverExclusive) {
		List<DnaProgrammingGene> exactCopy = new ArrayList<>();
		for(int index = 0; index < crossoverExclusive; index++) {
			DnaProgrammingGene gene = genes.get(index);
			exactCopy.add(gene);
		}
		return exactCopy;
	}

	private static String serialize(List<DnaProgrammingGene> genes) {
		String serialized = genes.stream()
			.map(gene -> {
				StartCodon startCodon = gene.getStartCodon();
				String code = startCodon.getCode();
				String baseChain = gene.getBaseChain();
				return (code + baseChain);
			})
			.collect(Collectors.joining(" "));
		return serialized;
	}
}
