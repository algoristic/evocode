package de.algoristic.evocode.app.io;

import robocode.BattleResults;

public class FitnessValue {

	private double value;
	private BattleResults rawData;
	private long timeInMillis = -1;

	public double getValue() {
		return value;
	}

	public synchronized void setValue(double value) {
		this.value = value;
	}

	public int getRank() {
		return rawData.getRank();
	}

	public int getScore() {
		return rawData.getScore();
	}

	public int getSurvival() {
		return rawData.getSurvival();
	}

	public int getLastSurvivorBonus() {
		return rawData.getLastSurvivorBonus();
	}

	public int getBulletDamage() {
		return rawData.getBulletDamage();
	}

	public int getBulletDamageBonus() {
		return rawData.getBulletDamageBonus();
	}

	public int getRamDamage() {
		return rawData.getRamDamage();
	}

	public int getRamDamageBonus() {
		return rawData.getRamDamageBonus();
	}

	public int getFirsts() {
		return rawData.getFirsts();
	}

	public int getSeconds() {
		return rawData.getSeconds();
	}

	public int getThirds() {
		return rawData.getThirds();
	}

	public long getTimeInMillis() {
		return timeInMillis;
	}

	public void setTimeInMillis(long timeInMillis) {
		this.timeInMillis = timeInMillis;
	}

	public synchronized void setRawData(BattleResults rawData) {
		this.rawData = rawData;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
