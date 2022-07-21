package de.algoristic.evocode._poc;

import java.awt.Color;

import robocode.Robot;

public class NNBot extends Robot {

	private double playersAtStart;
	private double numberOfRounds;
	private double battleFieldWidth;
	private double battleFieldHeight;
	private double maxTurnAwareness;
	@Override
	public void run() {
		playersAtStart = this.getOthers();
		numberOfRounds = this.getNumRounds();
		battleFieldWidth = this.getBattleFieldWidth();
		battleFieldHeight = this.getBattleFieldHeight();
		// configurable
		maxTurnAwareness = 10000;
		{   // setup colors for better distinction of robots
			this.setBodyColor(Color.RED);
			this.setGunColor(Color.RED);
			this.setBulletColor(Color.MAGENTA);
			this.setRadarColor(Color.ORANGE);
			this.setScanColor(Color.ORANGE);
		}
		// add configurable routine
	}
	
	// implement methods
	
}
