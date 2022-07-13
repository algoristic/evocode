package de.algoristic.evocode.genetic.breeding;

import de.algoristic.evocode.app.EvaluatedGeneration;
import de.algoristic.evocode.app.Generation;
import de.algoristic.evocode.app.Individuals;
import de.algoristic.evocode.genetic.alteration.AlterationManager;
import de.algoristic.evocode.genetic.alteration.AlterationPipeline;
import de.algoristic.evocode.genetic.selection.Selection;
import de.algoristic.evocode.genetic.selection.Selector;
import de.algoristic.evocode.genetic.selection.SelectorManager;
import de.algoristic.evocode.genetic.selection.SelectorPipeline;
import de.algoristic.evocode.genetic.strategy.Population;
import de.algoristic.evocode.genetic.strategy.PopulationManager;
import de.algoristic.evocode.genetic.strategy.Populations;

public class FilialGenerationBreeder implements Breeder {

	private final EvaluatedGeneration parents;

	public FilialGenerationBreeder(final EvaluatedGeneration parents) {
		this.parents = parents;
	}

	@Override
	public Generation breedGeneration() {
		final int generation = (1 + parents.getGenerationNumber());
		Generation filialGeneration = new Generation(generation); 

		SelectorManager selectorManager = new SelectorManager(generation);
		SelectorPipeline selectors = selectorManager.getSelectors();

		AlterationManager alterationManager = new AlterationManager();
		AlterationPipeline alterers = alterationManager.getAlterers();

		PopulationManager populationManager = new PopulationManager();
		Populations populations = populationManager.determinePopulations(parents);
		for(Population population : populations) {
			for(Selector selector : selectors) {
				// TODO next
				Selection selection = selector.getSelection(population);
				Individuals individuals = selection.getOffsprings();
				alterers.mutate(individuals);
				filialGeneration.add(individuals);
			}
		}
		return filialGeneration;
	}

}
