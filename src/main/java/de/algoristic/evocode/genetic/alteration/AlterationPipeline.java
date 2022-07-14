package de.algoristic.evocode.genetic.alteration;

import java.util.Iterator;
import java.util.List;

public class AlterationPipeline implements Iterable<String> {

	private final List<String> alterers;

	public AlterationPipeline(List<String> alterers) {
		this.alterers = alterers;
	}

	@Override
	public Iterator<String> iterator() {
		return alterers.iterator();
	}
}
