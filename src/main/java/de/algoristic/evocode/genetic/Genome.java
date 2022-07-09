package de.algoristic.evocode.genetic;

public interface Genome {
	default Genotype transcribe(GeneTranscriptionParameters parameters) {
		String packageName = parameters.getPackageName();
		String compilationTargetPath = getCompilationTargetPath(packageName);
		return transcribe(parameters, compilationTargetPath);
	}
	
	Genotype transcribe(GeneTranscriptionParameters parameters, String compilationTargetPath);
	
	default String getCompilationTargetPath(String packageName) {
		return packageName.replace(".", "/");
	}
}
