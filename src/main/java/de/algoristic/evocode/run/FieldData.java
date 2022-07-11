package de.algoristic.evocode.run;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import de.algoristic.evocode.FitnessValue;

public class FieldData implements Iterable<Entry<Integer, FitnessValue>> {

	private final int generationNumber;
	private final Map<Integer, FitnessValue> fitnessValues;

	public FieldData(final int generationNumber) {
		this.generationNumber = generationNumber;
		this.fitnessValues = new HashMap<>();
	}

	public void addValues(final Individual individual, final FitnessValue fitness) {
		final int individualNumber = individual.getIndividualNumber();
		fitnessValues.put(individualNumber, fitness);
	}

	public int getGenerationNumber() {
		return generationNumber;
	}

	public Map<Integer, FitnessValue> getFitnessValues() {
		return fitnessValues;
	}

	@Override
	public Iterator<Entry<Integer, FitnessValue>> iterator() {
		return fitnessValues.entrySet().iterator();
	}
}
