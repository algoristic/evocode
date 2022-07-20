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
//		double battleFieldWidth = this.getBattleFieldWidth();//
//		double battleFieldHeight = this.getBattleFieldHeight();//
//		double height = this.getHeight();
//		double width = this.getWidth();
//		double x = this.getX();
//		double y = this.getY();
		double others = this.getOtherPlayers();
		double round = this.getRound();
		double heading = this.getHeading();
		double radarHeading = this.getRadarHeading();
		double gunHeading = this.getGunHeading();
//		double gunCoolingRate = this.getGunCoolingRate();
		double gunHeat = this.getGunHeat();
		double velocity = this.getVelocity();
		double energy = this.getEnergy();

//		int numRounds = this.getNumRounds();
//		int sentryBorderSize = this.getSentryBorderSize();//
//		int others = this.getOthers();
//		int numSentries = this.getNumSentries();
		int roundNum = this.getRoundNum();

		long time = this.getTime();
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

	private double playersAtStart;
	/** Impl. */
	private double getOtherPlayers() {
		return (((double) this.getOthers()) / playersAtStart);
	}

	private double numberOfRounds;
	/** Impl. */
	private double getRound() {
		return ((double) this.getRoundNum() / numberOfRounds);
	}

	private double battleFieldWidth;
	/** Impl. */
	private double getPositionX() {
		return (this.getX() / battleFieldWidth);
	}

	private double battleFieldHeight;
	/** Impl. */
	private double getPositionY() {
		return (this.getY() / battleFieldHeight);
	}

	private double maxTurnAwareness;
	/** Impl. */
	private double getTurn() {
		return Math.min(1, ((double) this.getTime() / maxTurnAwareness));
	}

	/** Impl. */
	@Override
	public double getHeading() {
		return (super.getHeading() / 359d);
	}

	/** Impl. */
	@Override
	public double getGunHeading() {
		return (super.getGunHeading() / 359d);
	}

	/** Impl. */
	@Override
	public double getGunHeat() {
		//maxHeat = 1 + (Rules.MAX_BULLET_POWER / 5);
		return (super.getGunHeat() / 1.6d);
	}

	/** Impl. */
	@Override
	public double getRadarHeading() {
		return (super.getRadarHeading() / 359d);
	}

	/** Impl. */
	@Override
	public double getVelocity() {
		return (super.getVelocity() / 8d);
	}

	/** Impl. */
	@Override
	public double getEnergy() {
		return (super.getEnergy() / 100d);
	}

	@Override
	public void run() {
		playersAtStart = this.getOthers();
		numberOfRounds = this.getNumRounds();
		battleFieldWidth = this.getBattleFieldWidth();
		battleFieldHeight = this.getBattleFieldHeight();
		maxTurnAwareness = 10000; // configure this
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
		double enemyDistance = event.getDistance() / Math.sqrt(Math.pow(battleFieldWidth, 2) + Math.pow(battleFieldHeight, 2));
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
