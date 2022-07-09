package de.algoristic.evocode._poc;

import java.awt.Color;

import robocode.Bullet;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.RobotStatus;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;

public class EvoBot extends Robot {

	// CONSTANTS
	{
		double acceleration = Rules.ACCELERATION;
		double deceleration = Rules.DECELERATION;
		double gunTurnRate = Rules.GUN_TURN_RATE;
		double maxBulletPower = Rules.MAX_BULLET_POWER;
		double maxTurnRate = Rules.MAX_TURN_RATE;
		double maxVelocity = Rules.MAX_VELOCITY;
		double minBulletPower = Rules.MIN_BULLET_POWER;
		double scanRadius = Rules.RADAR_SCAN_RADIUS;
		double radarTurnRate = Rules.RADAR_TURN_RATE;
		double robotHitBonus = Rules.ROBOT_HIT_BONUS;
		double robotHitDamage = Rules.ROBOT_HIT_DAMAGE;
		
		Rules.getBulletDamage(maxBulletPower);
		Rules.getBulletHitBonus(maxBulletPower);
		Rules.getBulletSpeed(maxBulletPower);
		Rules.getGunHeat(maxBulletPower);
		Rules.getTurnRate(maxVelocity);
		Rules.getWallHitDamage(maxVelocity);
	}

	// SENSORS
	{
		double battleFieldWidth = this.getBattleFieldWidth();//
		double battleFieldHeight = this.getBattleFieldHeight();//
		double heading = this.getHeading();
		double height = this.getHeight();
		double width = this.getWidth();
		double x = this.getX();
		double y = this.getY();
		double gunCoolingRate = this.getGunCoolingRate();
		double gunHeading = this.getGunHeading();
		double gunHeat = this.getGunHeat();
		int numRounds = this.getNumRounds();
		int sentryBorderSize = this.getSentryBorderSize();//
		int others = this.getOthers();
		int numSentries = this.getNumSentries();
		double radarHeading = this.getRadarHeading();
		int roundNum = this.getRoundNum();
		long time = this.getTime();
		double velocity = this.getVelocity();
		double energy = this.getEnergy();
	}
	
	{   // ACTORS /* +Zahlenraum */
		this.ahead(0d);          // in px,  z. B. -100 bis 100
		this.back(0d);           // in px,  z. B. -100 bis 100
		this.turnLeft(0d);       // in deg, z. B. -360 bis 360
		this.turnRight(0d);      // in deg, z. B. -360 bis 360
		this.doNothing();        // 
		this.fire(0d);           // in abs, von 0.1 bis 3.0
		this.fireBullet(0d);     // in abs, von 0.1 bis 3.0
		this.turnGunLeft(0d);    // in deg, von -360 bis 360
		this.turnGunRight(0d);   // in deg, von -360 bis 360
		this.turnRadarLeft(0d);  // in deg, von -360 bis 360
		this.turnRadarRight(0d); // in deg, von -360 bis 360
		
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

	{	// TRANSFORMATORS
		
	}
	private double min(double value, double min) {
		return min;
	}
	
	private double max(double value, double max) {
		return max;
	}
	
	private double limit(double value, double min, double max) {
		double minDist = Math.abs(value - min);
		double maxDist = Math.abs(max - value);
		if(minDist == maxDist) {
			return value;
		} else if(maxDist > minDist) {
			return max;
		} else {
			return min;
		}
	}
	
	private boolean on = true;
	private synchronized double osc(double value, double min, double max) {
		try {
			if(on) {
				return max;
			} else {
				return min;
			}
		} finally {
			on = !on;
		}
	}

	private double asIs(double value) {
		return value;
	}
	
	private double half(double value, double max) {
		return (max / 2);
	}
	
	private double quarter(double value, double max) {
		return (max / 4);
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
