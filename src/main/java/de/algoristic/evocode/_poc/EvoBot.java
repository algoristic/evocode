package de.algoristic.evocode._poc;

import java.awt.Color;

import robocode.*;

public class EvoBot extends Robot {

	// SENSORS
	{
		double battleFieldWidth = this.getBattleFieldWidth();
		double battleFieldHeight = this.getBattleFieldHeight();
		double heading = this.getHeading();
		double height = this.getHeight();
		double width = this.getWidth();
		double x = this.getX();
		double y = this.getY();
		double gunCoolingRate = this.getGunCoolingRate();
		double gunHeading = this.getGunHeading();
		double gunHeat = this.getGunHeat();
		int numRounds = this.getNumRounds();
		int sentryBorderSize = this.getSentryBorderSize();
		int others = this.getOthers();
		int numSentries = this.getNumSentries();
		double radarHeading = this.getRadarHeading();
		int roundNum = this.getRoundNum();
		long time = this.getTime();
		double velocity = this.getVelocity();
		double energy = this.getEnergy();
	}
	
	{   // ACTORS
		this.ahead(0d);
		this.back(0d);
		this.turnLeft(0d);
		this.turnRight(0d);
		this.doNothing();
		this.fire(0d);
		this.fireBullet(0d);
		this.turnGunLeft(0d);
		this.turnGunRight(0d);
		this.turnRadarLeft(0d);
		this.turnRadarRight(0d);
		
		// initializers
		this.setAdjustGunForRobotTurn(true);
		this.setAdjustRadarForGunTurn(true);
		this.setAdjustRadarForRobotTurn(true);
		
		// maybe actors
		this.scan();
		this.stop();
		this.stop(true);
		this.resume();
	}

	@Override
	public void run() {
		{   // setup colors for better distinction of robots
			this.setBodyColor(Color.RED);
			this.setGunColor(Color.RED);
			this.setBulletColor(Color.MAGENTA);
			this.setRadarColor(Color.ORANGE);
			this.setScanColor(Color.ORANGE);
		}
	}
	
	@Override
	public void onBulletHit(BulletHitEvent event) {
		Bullet myBullet = event.getBullet();
		
		//SENSORS
		double heading = myBullet.getHeading();
		double power = myBullet.getPower();
		double velocity = myBullet.getVelocity();
		double x = myBullet.getX();
		double y = myBullet.getY();

		double enemyEnergy = event.getEnergy();
	}

	@Override
	public void onBulletHitBullet(BulletHitBulletEvent event) {
		Bullet myBullet = event.getBullet();
		Bullet enemyBullet = event.getHitBullet();
		// bullet sensors ...
	}

	@Override
	public void onBulletMissed(BulletMissedEvent event) {
		Bullet myBullet = event.getBullet();
		// bullet sensors ...
	}

	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		//SENSORS
		double bulletBearing = event.getBearing();
		double bulletHeading = event.getHeading();
		double bulletPower = event.getPower();
		
		Bullet enemyBullet =  event.getBullet();
		// bullet sensors ...
	}

	@Override
	public void onHitRobot(HitRobotEvent event) {
		// SENSORS
		double enemyBearing = event.getBearing();
		double enemyEnergy = event.getEnergy();
	}

	@Override
	public void onHitWall(HitWallEvent event) {
		// SENSORS
		double wallBearing = event.getBearing();
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		// SENSORS
		double enemyBearing = event.getBearing();
		double enemyDistance = event.getDistance();
		double enemyEnergy = event.getEnergy();
		double enemyHeading = event.getHeading();
		boolean enemyIsSentryRobot = event.isSentryRobot();
	}

	@Override
	public void onStatus(StatusEvent event) {
		RobotStatus status = event.getStatus();
		
		// SENSORS
		double distanceRemaining = status.getDistanceRemaining();
		double gunTurnRemaining = status.getGunTurnRemaining();
		double radarTurnRemaining = status.getRadarTurnRemaining();
		double turnRemaining = status.getTurnRemaining();
		
		// MEMO: über dieses Event können auch alle anderen Sensoren abgefragt werden.
	}
}
