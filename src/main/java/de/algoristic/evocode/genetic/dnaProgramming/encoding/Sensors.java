package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.Arrays;
import java.util.List;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.util.NumberSystemUtils;

public abstract class Sensors {

	private static final List<Sensor> ADVANCED_SENSORS = Arrays.asList(
		new Sensor("getHeading"),
		new Sensor("getX"),
		new Sensor("getY"),
		new Sensor("getGunHeading"),
		new Sensor("getGunHeat"),
		new Sensor("getOtherPlayers"),
		new Sensor("getRadarHeading"),
		new Sensor("getRound"),
		new Sensor("getTurn"),
		new Sensor("getVelocity"),
		new Sensor("getEnergy"));

	private static final List<Sensor> BASIC_SENSORS = Arrays.asList(
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
		String template = (new EvocodeSettings()).getRobotTemplate();
		List<Sensor> sensors = template.equals("basic") ? BASIC_SENSORS : ADVANCED_SENSORS;
		int numericCode = NumberSystemUtils.hexToDecimal(hexCode);
		int position = (numericCode % sensors.size());
		return sensors.get(position);
	}
}
