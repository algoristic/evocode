package de.algoristic.evocode;

import java.util.List;

import de.algoristic.evocode.context.EvocodeContext;
import de.algoristic.evocode.run.GenerationBuildingTask;

public class Evocode {

	private final EvocodeContext evocodeContext;
	
	public Evocode() {
		this(new EvocodeContext());
	}
	
	private Evocode(final EvocodeContext context) {
		this.evocodeContext = context;
	}

	public void run() {
		List<GenerationBuildingTask> tasks = evocodeContext.getTasks();
		for(GenerationBuildingTask task : tasks) {
			// Generation generation;
			if(task.hasAnchestors()) {
				// Generation parents = task.determinePreviousGeneration();
				// generation = parents.breedNextGeneration();
			} else {
				// generation = // create new generation
			}
			// generation.runTournament();
		}
	}
}
