package de.algoristic.evocode.genetic.breeding;

import de.algoristic.evocode.app.Individuals;
import de.algoristic.evocode.genetic.alteration.AlterationPipeline;

public interface BreedingPipeline {

	Individuals apply(AlterationPipeline alterers);
}
