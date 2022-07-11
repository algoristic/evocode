package de.algoristic.evocode.genetic.dnaProgramming.encoding;

public interface AcceptedType<T> {

	int numberOfOptions();

	ActorValue getOption(int position);

}
