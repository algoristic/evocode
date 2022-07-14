package de.algoristic.evocode;

import de.algoristic.evocode.app.Enviroment;
import de.algoristic.evocode.app.EvaluatedGeneration;
import de.algoristic.evocode.app.Generation;
import de.algoristic.evocode.app.conditions.Terminator;
import de.algoristic.evocode.app.conf.EvocodeContext;
import de.algoristic.evocode.app.io.EvocodeProtocol;
import de.algoristic.evocode.app.io.FieldData;
import de.algoristic.evocode.app.io.tasks.GenerationBuildingTask;
import de.algoristic.evocode.app.io.tasks.ProjectSetupTask;
import de.algoristic.evocode.genetic.breeding.Breeder;
import de.algoristic.evocode.genetic.breeding.FilialGenerationBreeder;
import de.algoristic.evocode.genetic.breeding.FirstGenerationBreeder;

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
				task.prepare();
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
