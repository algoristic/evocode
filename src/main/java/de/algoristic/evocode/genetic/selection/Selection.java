package de.algoristic.evocode.genetic.selection;

import de.algoristic.evocode.run.Individuals;

/**
 * MEMO can be either the parents that recombine themselves (default)
 *      or a selected elite that gets transferred
 * */
public interface Selection {

	Individuals getOffsprings();

}
