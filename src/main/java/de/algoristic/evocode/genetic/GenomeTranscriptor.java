package de.algoristic.evocode.genetic;

import java.io.File;

import de.algoristic.evocode.context.FilesystemContext;
import de.algoristic.evocode.run.EvocodeSettings;
import de.algoristic.evocode.run.GenerationProperties;
import de.algoristic.evocode.util.Pattern;

public class GenomeTranscriptor {

	private final EvocodeSettings settings;
	private final FilesystemContext context;
	private final int generationNumber;
	private final int individualNumber;

	private GenomeTranscriptor(
		final EvocodeSettings settings,
		final FilesystemContext context,
		final int generationNumber,
		final int individualNumber) {
		this.settings = settings;
		this.context = context;
		this.generationNumber = generationNumber;
		this.individualNumber = individualNumber;
	}

	public GenomeTranscriptor(final int generationNumber, final int individualNumber) {
		this(new EvocodeSettings(), new FilesystemContext(), generationNumber, individualNumber);
	}

	public Genotype transcribe(final Genome genome) {
		final GeneTranscriptionParameters parameters = createTranscriptionParameters();
		Genotype genotype = genome.transcribe(parameters);
		final GenerationProperties generationProperties = context.getGenerationProperties(generationNumber);
		generationProperties.writeGenome(individualNumber, genome);
		return genotype;
	}

	private GeneTranscriptionParameters createTranscriptionParameters() {
		final File individualDirectory = prepareIndividualDirectory();
		final String robotName = getRobotName();
		final String packageName = settings.getPackageName();
		return new GeneTranscriptionParameters(individualDirectory, packageName, robotName);
	}

	private File prepareIndividualDirectory() {
		final File individualDirectory = context.getIndividualDirectoryName(generationNumber, individualNumber);
		if(!individualDirectory.exists()) individualDirectory.mkdir();
		return individualDirectory;
	}

	private String getRobotName() {
		final String robotNamePattern = settings.getRobotNamePattern();
		Pattern pattern = new Pattern(robotNamePattern);
		pattern = pattern
			.addVariable("[generation]", generationNumber)
			.addVariable("[individual]", individualNumber);
		final String robotName = pattern.compile();
		return robotName;
	}
}
