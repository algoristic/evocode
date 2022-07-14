package de.algoristic.evocode.genetic;

import de.algoristic.evocode.util.FileSystemUtils;

public interface Genome {
	default Genotype transcribe(GeneTranscriptionParameters parameters) {
		String packageName = parameters.getPackageName();
		String compilationTargetPath = FileSystemUtils.resolvePackageNameToPath(packageName);
		return transcribe(parameters, compilationTargetPath);
	}
	
	Genotype transcribe(GeneTranscriptionParameters parameters, String compilationTargetPath);
	String serialize();
	Genome crossover(Genome other);
	Genome mutate(String mutatorSpec, double mutationRate);
}
