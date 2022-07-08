package de.algoristic.evocode.genetic.dnaProgramming;

import java.util.List;

import de.algoristic.evocode.genetic.GeneTranscriptionParameters;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.Genotype;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ControlCodon;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.ControlStructure;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.RobotMethod;
import de.algoristic.evocode.genetic.dnaProgramming.encoding.RobotMethods;

public class DnaProgram implements Genome {

	private final List<DnaProgrammingGene> genes;

	public DnaProgram(final List<DnaProgrammingGene> genes) {
		this.genes = genes;
	}

	@Override
	public Genotype transcribe(GeneTranscriptionParameters parameters) {
		RobotMethods methodSet = new RobotMethods();
		for(DnaProgrammingGene gene : genes) {
			String startCodon = gene.getStartCodon();
			RobotMethod method = methodSet.getMethod(startCodon);
			ControlCodon controlCodon = gene.getControlCodon();
			ControlStructure controlStructure = ControlStructure.of(controlCodon);
		}
		return null;
	}

}
