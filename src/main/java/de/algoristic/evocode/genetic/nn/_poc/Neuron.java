package de.algoristic.evocode.genetic.nn._poc;

public interface Neuron {

	/**
	 * Immer von hinten nach vorne:
	 *  (1) this.fire()     => also letzten verarbeiteten Wert weitergeben
	 *  (2) this.accumulate => aufgenommene Werte weiterverarbeiten
	 * Diese Aufteilung/Bearbeitungsreihenfolge macht es uns möglich in "Ticks" zu arbeiten
	 * und dem Roboter ein "Gedächtnis" zu geben. Mit jedem "onStatus" Event werden die Werte
	 * eine Layer bzw. eine Connection weitergegeben.
	 * */
	default void propagate() {
		this.fire();
		this.accumulate();
	}

	/**
	 * Im Fall von Sensor: transformierten Wert an Connections geben.
	 * Im Fall von Intermediate/Action: akkumulierten Wert weiterverarbeiten
	 *  -> Intermediate: an Connections geben
	 *  -> Action: Action ausführen
	 * */
	void fire();
	
	/**
	 * Im Fall von Sensor: aufgenommenen Wert transformieren.
	 * Im Fall von Intermediate/Action: tanh(sum(inputs)) bilden (=akkumulierter Wert)
	 * */
	void accumulate();
}
