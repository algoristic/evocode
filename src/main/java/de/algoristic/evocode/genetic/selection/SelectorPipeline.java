package de.algoristic.evocode.genetic.selection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectorPipeline implements Iterable<Selector> {

	private final List<Selector> selectors;

	public SelectorPipeline() {
		selectors = new ArrayList<>();
	}

	public void add(Selector selector) {
		selectors.add(selector);
	}

	@Override
	public Iterator<Selector> iterator() {
		return selectors.iterator();
	}
}
