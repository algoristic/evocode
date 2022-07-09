package de.algoristic.evocode.run;

public class EvocodeFiles {

	private final EvocodeSettings settings;

	private EvocodeFiles(EvocodeSettings settings) {
		this.settings = settings;
	}

	public EvocodeFiles() {
		this(new EvocodeSettings());
	}

	// TODO here -> write results (per individual) in detailed CSV for generation (i corresponding dir
	//           -> write results of generation in global results CSV
	//              => content: gen_number, avg_firsts (=avg_survival), avg_fitness, ...
	public void writeProtocol(FieldData fieldData) {
		
	}
}
