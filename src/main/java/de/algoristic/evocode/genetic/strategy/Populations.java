package de.algoristic.evocode.genetic.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Populations implements Iterable<Population> {

	private final List<Population> populations;

	public Populations() {
		populations = new ArrayList<>();
	}

	public void add(Population population) {
		populations.add(population);
	}

	@Override
	public Iterator<Population> iterator() {
		return populations.iterator();
	}

}
