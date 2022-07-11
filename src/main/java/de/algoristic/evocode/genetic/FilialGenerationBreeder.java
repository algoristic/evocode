package de.algoristic.evocode.genetic;

import de.algoristic.evocode.run.EvaluatedGeneration;
import de.algoristic.evocode.run.Generation;

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
		// TODO/MEMO alles was mit Inseln etc. zu tun hat kommt hier und nur hier hin (!)
		//           denn es hat bei der Gen-Expression und Bewertung nichts zu suchen - nur bei der Selektion
		
		// TODO Inseln
		// TODO Selektoren / SelektorPipeline
		   // MEMO Alter von Individuen (wo speichern?)
		// TODO GeneAlteration
		
		
		return null;
	}

}
