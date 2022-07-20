package de.algoristic.evocode;

import java.util.List;

import javax.security.auth.x500.X500Principal;

import de.algoristic.evocode.app.Enviroment;
import de.algoristic.evocode.app.Generation;
import de.algoristic.evocode.app.Individual;
import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.app.conf.FilesystemContext;
import de.algoristic.evocode.app.io.files.GenerationProperties;
import de.algoristic.evocode.genetic.Genetics;
import de.algoristic.evocode.genetic.Genome;
import de.algoristic.evocode.genetic.GenomeManager;
import de.algoristic.evocode.genetic.nn.RobotBootstrap;
import de.algoristic.evocode.genetic.nn.Sensor;

public class EvoCompile {

	private final int generation;
	private final int individual;

	private final FilesystemContext context;

	public EvoCompile(final int generation, final int individual) {
		this.generation = generation;
		this.individual = individual;
		context = new FilesystemContext(); 
	}

	double arccot(double v) {
		return (Math.PI / 2 - Math.atan(v));
	}

	double fn(double v) {
		return (1 - arccot(v));
	}

	public void run() {
//		EvocodeSettings settings = new EvocodeSettings();
//		RobotBootstrap bootstrap = new RobotBootstrap();
//		List<Sensor> sensors = bootstrap.sensors();
//		sensors.stream().map(s -> s.getVariableDeclaration("")).forEach(System.out::println);

		for(int i = 1; i <= 25; i++) {
			System.out.println(i + ": " + fn(i));
		}

//		GenerationProperties properties = context.getGenerationProperties(generation);
//		String serializedGenome = properties.getGenome(individual);
//		GenomeManager manager = new GenomeManager();
//		Genetics genetics = manager.getGenetics();
//		Genome genome = genetics.readFrom(serializedGenome);
//		Individual individual = new Individual(this.generation, this.individual, genome);
//		Generation generation = new Generation(this.generation);
//		generation.add(individual);
//		new Enviroment(true).test(generation);
	}
}
