package de.algoristic.evocode.genetic;

public interface Genetics {

	Genome initialize();
	Genome readFrom(String serialized);

}
