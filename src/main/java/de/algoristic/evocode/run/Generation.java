package de.algoristic.evocode.run;

import de.algoristic.evocode.genetic.Breeder;
import de.algoristic.evocode.genetic.FilialGenerationBreeder;

public class Generation {

	public Generation breedNextGeneration() {
		Breeder breeder = new FilialGenerationBreeder(this);
		Generation children = breeder.breedGeneration();
		return children;
	}



}
