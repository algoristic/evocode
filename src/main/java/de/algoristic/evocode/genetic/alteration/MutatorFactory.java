package de.algoristic.evocode.genetic.alteration;

import de.algoristic.evocode.genetic.Genome;

public interface MutatorFactory<T extends Genome> {

	Mutator<T> getMutator(String spec);
}
