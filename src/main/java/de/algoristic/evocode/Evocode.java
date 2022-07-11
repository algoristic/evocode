package de.algoristic.evocode;

import java.util.List;

import de.algoristic.evocode.context.EvocodeContext;
import de.algoristic.evocode.genetic.Breeder;
import de.algoristic.evocode.genetic.Enviroment;
import de.algoristic.evocode.genetic.FilialGenerationBreeder;
import de.algoristic.evocode.genetic.FirstGenerationBreeder;
import de.algoristic.evocode.run.EvaluatedGeneration;
import de.algoristic.evocode.run.EvocodeFiles;
import de.algoristic.evocode.run.FieldData;
import de.algoristic.evocode.run.Generation;
import de.algoristic.evocode.run.GenerationBuildingTask;
import de.algoristic.evocode.run.ProjectSetupTask;

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
		setupTask.prepareProject();
		List<GenerationBuildingTask> buildingTasks = evocodeContext.getBuildingTasks();
		EvocodeFiles files = new EvocodeFiles();
		for(GenerationBuildingTask task : buildingTasks) {
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
		}
	}
}
