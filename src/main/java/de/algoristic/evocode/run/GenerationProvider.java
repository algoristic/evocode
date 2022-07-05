package de.algoristic.evocode.run;

public class GenerationProvider {

	private final String directoryPattern;

	public GenerationProvider(String directoryPattern) {
		this.directoryPattern = directoryPattern;
	}

	

	public static GenerationProvider fromSettings(ProjectSettings settings) {
		String directoryPattern = settings.getGenerationDirectoryPattern();
		return new GenerationProvider(directoryPattern);
	}
}
