package de.algoristic.evocode.genetic.selection;

import java.util.List;

import de.algoristic.evocode.app.conf.EvolutionSettings;

public class SelectorManager {

	private final int generation;
	private final EvolutionSettings settings;
	
	private SelectorManager(final int generation, final EvolutionSettings settings) {
		this.generation = generation;
		this.settings = settings;
	}

	public SelectorManager(final int generation) {
		this(generation, new EvolutionSettings());
	}

	public SelectorPipeline getSelectors() {
		List<String> specs = settings.getSelectorSpecs(generation);
		SelectorPipeline selectors = new SelectorPipeline();
		for(String spec : specs) {
			Selector selector = getInstance(spec);
			selectors.add(selector);
		}
		return selectors;
	}

	private Selector getInstance(String spec) {
		final int out = settings.getSelectorOutput(spec, generation);
		Selector selector = null;
		if("elite".equalsIgnoreCase(spec)) {
			selector = new EliteSelector(out);
		}
		if("rouletteWheel".equalsIgnoreCase(spec)) {
			selector = new RouletteWheelSelector(out);
		}
		if("rank".equalsIgnoreCase(spec)) {
			selector = new RankSelector(out);
		}
		if("tournament".equalsIgnoreCase(spec)) {
			int sampleSize = settings.getTournamentSampleSize(generation);
			selector = new TournamentSelector(out, sampleSize);
		}
		if(selector != null) return selector;
		else throw new IllegalArgumentException("unknown selector spec: " + spec);
	}
}
