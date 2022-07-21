package de.algoristic.evocode.genetic;

import java.util.ArrayList;
import java.util.List;

import de.algoristic.evocode.util.FileSystemUtils;

public interface Genome {
	default Genotype transcribe(GeneTranscriptionParameters parameters) {
		String packageName = parameters.getPackageName();
		String compilationTargetPath = FileSystemUtils.resolvePackageNameToPath(packageName);
		return transcribe(parameters, compilationTargetPath);
	}
	default <G extends Gene> List<G> copyGenesUntil(List<G> genes, int crossoverExclusive) {
		List<G> exactCopy = new ArrayList<>();
		for(int index = 0; index < crossoverExclusive; index++) {
			G gene = genes.get(index);
			exactCopy.add(gene);
		}
		return exactCopy;
	}
	default <G extends Gene> List<G> copyGenesFrom(List<G> genes, int crossoverInclusive) {
		List<G> copy = new ArrayList<>();
		for(int index = crossoverInclusive; index < genes.size(); index++) {
			G gene = genes.get(index);
			copy.add(gene);
		}
		return copy;
	}
	Genotype transcribe(GeneTranscriptionParameters parameters, String compilationTargetPath);
	String serialize();
	Genome crossover(Genome other);
	Genome mutate(String mutatorSpec, double mutationRate);
}
