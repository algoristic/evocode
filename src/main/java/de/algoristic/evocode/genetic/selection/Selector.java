package de.algoristic.evocode.genetic.selection;

import de.algoristic.evocode.genetic.strategy.Population;

public interface Selector {

	Selection getSelection(Population population);

}
