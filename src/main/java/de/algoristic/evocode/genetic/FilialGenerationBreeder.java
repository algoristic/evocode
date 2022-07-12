package de.algoristic.evocode.genetic;

import de.algoristic.evocode.genetic.alteration.AlterationManager;
import de.algoristic.evocode.genetic.alteration.AlterationPipeline;
import de.algoristic.evocode.genetic.selection.Selection;
import de.algoristic.evocode.genetic.selection.Selector;
import de.algoristic.evocode.genetic.selection.SelectorManager;
import de.algoristic.evocode.genetic.selection.SelectorPipeline;
import de.algoristic.evocode.genetic.strategy.Population;
import de.algoristic.evocode.genetic.strategy.PopulationManager;
import de.algoristic.evocode.genetic.strategy.Populations;
import de.algoristic.evocode.run.EvaluatedGeneration;
import de.algoristic.evocode.run.Generation;
import de.algoristic.evocode.run.Individuals;

public class FilialGenerationBreeder implements Breeder {

	private final GeneticsContext context;
	private final EvaluatedGeneration parents;

	private FilialGenerationBreeder(final GeneticsContext context, final EvaluatedGeneration parents) {
		this.context = context;
		this.parents = parents;
	}

	public FilialGenerationBreeder(final EvaluatedGeneration parents) {
		this(new GeneticsContext(), parents);
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
		Populations populations = populationManager.getPopulations(parents);
		for(Population population : populations) {
			for(Selector selector : selectors) {
				Selection selection = selector.getSelection(population);
				Individuals individuals = selection.getOffsprings();
				alterers.mutate(individuals);
				filialGeneration.add(individuals);
			}
		}
		return filialGeneration;
	}

}
