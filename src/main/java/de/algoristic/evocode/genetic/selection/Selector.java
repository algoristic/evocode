package de.algoristic.evocode.genetic.selection;

import de.algoristic.evocode.genetic.breeding.BreedingPipeline;
import de.algoristic.evocode.genetic.strategy.Population;

public interface Selector {

	BreedingPipeline getOffsprings(Population population);

}
