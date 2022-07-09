package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.Arrays;
import java.util.List;

import de.algoristic.evocode.util.NumberSystemUtils;

public abstract class Sensors {

	private static final List<Sensor> SENSORS = Arrays.asList(
		new Sensor("getHeading"),
		new Sensor("getHeight"),
		new Sensor("getWidth"),
		new Sensor("getX"),
		new Sensor("getY"),
		new Sensor("getGunCoolingRate"),
		new Sensor("getGunHeading"),
		new Sensor("getGunHeat"),
		new Sensor("getNumRounds"),
		new Sensor("getOthers"),
		new Sensor("getNumSentries"),
		new Sensor("getRadarHeading"),
		new Sensor("getRoundNum"),
		new Sensor("getTime"),
		new Sensor("getVelocity"),
		new Sensor("getEnergy"));

	public static Sensor getLeftOperand(ControlCodon controlCodon) {
		String leftOperandCode = controlCodon.getFirstConditionCode();
		return getSensor(leftOperandCode);
	}

	public static Sensor getRightOperand(ControlCodon controlCodon) {
		String rightOperandCode = controlCodon.getSecondConditionCode();
		return getSensor(rightOperandCode);
	}

	private static Sensor getSensor(String hexCode) {
		int numericCode = NumberSystemUtils.hexToDecimal(hexCode);
		int position = (numericCode % SENSORS.size());
		return SENSORS.get(position);
	}
}
