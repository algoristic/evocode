package de.algoristic.evocode;

import de.algoristic.evocode.context.EvocodeContext;
import de.algoristic.evocode.genetic.Breeder;
import de.algoristic.evocode.genetic.Enviroment;
import de.algoristic.evocode.genetic.FilialGenerationBreeder;
import de.algoristic.evocode.genetic.FirstGenerationBreeder;
import de.algoristic.evocode.run.EvaluatedGeneration;
import de.algoristic.evocode.run.EvocodeProtocol;
import de.algoristic.evocode.run.FieldData;
import de.algoristic.evocode.run.Generation;
import de.algoristic.evocode.run.GenerationBuildingTask;
import de.algoristic.evocode.run.ProjectSetupTask;
import de.algoristic.evocode.run.Terminator;

public class Evocode {

	private final EvocodeContext evocodeContext;
	
	public Evocode() {
		this(new EvocodeContext());
	}
	
	private Evocode(final EvocodeContext context) {
		this.evocodeContext = context;
	}

	public void run() {
		ProjectSetupTask setupTask = evocodeContext.getSetupTask();
		EvocodeProtocol files = new EvocodeProtocol();
		Terminator terminator = evocodeContext.getTerminator();
		setupTask.prepareProject();
		while(terminator.hasNext()) {
			GenerationBuildingTask task = terminator.next();
			try {
				task.prepareDirectory();
				Generation generation;
				Breeder breeder;
				if(task.hasAnchestors()) {
					EvaluatedGeneration parents = task.determinePreviousGeneration();
					breeder = new FilialGenerationBreeder(parents);
				} else {
					breeder = new FirstGenerationBreeder();
				}
				generation = breeder.breedGeneration();
				Enviroment enviroment = new Enviroment();
				FieldData fieldData = enviroment.test(generation);
				files.writeProtocols(fieldData);
			} catch (RuntimeException e) {
				task.breakdown();
				System.err.println("Could not complete task: " + task + ", Stacktrace:");
				e.printStackTrace(System.err);
			}
		}
	}
}
