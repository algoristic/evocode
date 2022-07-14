package de.algoristic.evocode.genetic.dnaProgramming;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import de.algoristic.evocode.genetic.GeneTranscriptionParameters;
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

public class DnaProgram implements Genome {

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

	@Override
	public Genome crossover(Genome other) {
		if(other == null) throw new NullPointerException();
		if(! (other instanceof DnaProgram)) throw new RuntimeException("Fatal: trying to crossover DnaProgram and " + other.getClass().getSimpleName());
		
		int max = genes.size();
		int crossoverPoint = (new Random()).nextInt(max);

		DnaProgram otherDna = (DnaProgram) other;
		List<DnaProgrammingGene> offspringDna = this.copyGenesUntil(crossoverPoint);
		offspringDna.addAll(otherDna.copyGenesFrom(crossoverPoint));
		return new DnaProgram(offspringDna);
	}

	@Override
	public Genome mutate(String mutatorSpec) {
		
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
}
