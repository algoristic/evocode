package de.algoristic.evocode.genetic.nn._poc;

import java.util.ArrayList;
import java.util.List;

import robocode.BulletMissedEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;

public class NeuralBot extends Robot {

	private List<SensoryNeuron> sensorLayer = new ArrayList<>();
	private List<IntermediateNeuron> hiddenLayer = new ArrayList<>();
	private List<ActionNeuron> actionLayer = new ArrayList<>();

	// all this gets rendered, depending on genome
	SensoryNeuron energySensor;
	// ...

	@Override
	public void run() {
		energySensor = new SensoryNeuron(1d);
	}

	@Override
	public void onBulletMissed(BulletMissedEvent event) {
		double energy = this.getEnergy();
		energySensor.process(energy);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		
	}

	@Override
	public void onStatus(StatusEvent e) {
		// hier auch die globalen neuronen Feuern!
		
		for(ActionNeuron action : actionLayer) {
			action.propagate();
		}
		for(IntermediateNeuron inner : hiddenLayer) {
			inner.propagate();
		}
		for(SensoryNeuron sensor : sensorLayer) {
			sensor.propagate();
		}
	}

	
}
