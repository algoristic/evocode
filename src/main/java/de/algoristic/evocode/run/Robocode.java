package de.algoristic.evocode.run;

import java.io.File;

import robocode.control.RobocodeEngine;

public class Robocode {

	private static RobocodeEngine ENGINE_SINGLETON;

	private final RobocodeEngine engine;

	private Robocode(final RobocodeEngine engine) {
		this.engine = engine;
	}
	
	public static Robocode getInstance() {
		if(ENGINE_SINGLETON == null) {
			EvocodeSettings settings = new EvocodeSettings();
			File engineLocation = settings.getRobocodeLocation();
			ENGINE_SINGLETON = new RobocodeEngine(engineLocation);
		}
		return new Robocode(ENGINE_SINGLETON);
	}
	
}
