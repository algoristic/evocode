package de.algoristic.evocode.genetic.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MigrationBehaviour implements Iterable<Migration> {

	private final List<Migration> behaviours;

	public MigrationBehaviour() {
		behaviours = new ArrayList<>();
	}

	public void add(Migration migration) {
		behaviours.add(migration);
	}

	@Override
	public Iterator<Migration> iterator() {
		return behaviours.iterator();
	}
}
