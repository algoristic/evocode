package de.algoristic.evocode.genetic.nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.algoristic.evocode.app.conf.EvocodeSettings;

public class RobotBootstrap {

	private List<Sensor> globalSensors;
	private List<RobotMethod> methods;

	private List<Sensor> localSensors;
	private List<EventObject> eventObjects;
	
	{
		globalSensors = new ArrayList<>();
		globalSensors.add(new Sensor.Builder("positionX").build());
		globalSensors.add(new Sensor.Builder("positionY").build());
		globalSensors.add(new Sensor.Builder("otherPlayers").build());
		globalSensors.add(new Sensor.Builder("round").build());
		globalSensors.add(new Sensor.Builder("turn").build());
		globalSensors.add(new Sensor.Builder("heading").build());
		globalSensors.add(new Sensor.Builder("radarHeading").build());
		globalSensors.add(new Sensor.Builder("gunHeading").build());
		globalSensors.add(new Sensor.Builder("gunHeat").build());
		globalSensors.add(new Sensor.Builder("velocity").build());
		globalSensors.add(new Sensor.Builder("energy").build());

		// Create local sensors, that can be used on multiple locations
		// (remember, no matter if my bullet is obtained from
		// "bulletHitBullet" or "bulletMissed" - it's still my bullet)
		eventObjects = new ArrayList<>();

		String ownBulletName = "myBullet";
		EventObject myBullet = new EventObject("Bullet", ownBulletName);
		myBullet.addSensor(new Sensor.Builder("heading")
			.withVariableName("myBulletHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter(ownBulletName)
					.build(), "359d"))
			.build());
		myBullet.addSensor(new Sensor.Builder("power")
			.withVariableName("myBulletPower")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("power")
					.withSignalEmitter(ownBulletName)
					.build(), "3d"))
			.build());
		myBullet.addSensor(new Sensor.Builder("velocity")
			.withVariableName("myBulletVelocity")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("velocity")
					.withSignalEmitter(ownBulletName)
					.build(), "8d"))
			.build());
		myBullet.addSensor(new Sensor.Builder("x")
			.withVariableName("myBulletX")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("x")
					.withSignalEmitter(ownBulletName)
					.build(), "battleFieldWidth"))
			.build());
		myBullet.addSensor(new Sensor.Builder("y")
			.withVariableName("myBulletY")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("y")
					.withSignalEmitter(ownBulletName)
					.build(), "battleFieldHeight"))
			.build());
		eventObjects.add(myBullet);

		String otherBulletName = "enemyBullet";
		EventObject enemyBullet = new EventObject("Bullet", otherBulletName);
		enemyBullet.addSensor(new Sensor.Builder("heading")
			.withVariableName("enemyBulletHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter(otherBulletName)
					.build(), "359d"))
			.build());
		enemyBullet.addSensor(new Sensor.Builder("power")
			.withVariableName("enemyBulletPower")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("power")
					.withSignalEmitter(otherBulletName)
					.build(), "3d"))
			.build());
		enemyBullet.addSensor(new Sensor.Builder("velocity")
			.withVariableName("enemyBulletVelocity")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("velocity")
					.withSignalEmitter(otherBulletName)
					.build(), "8d"))
			.build());
		enemyBullet.addSensor(new Sensor.Builder("x")
			.withVariableName("enemyBulletX")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("x")
					.withSignalEmitter(otherBulletName)
					.build(), "battleFieldWidth"))
			.build());
		enemyBullet.addSensor(new Sensor.Builder("y")
			.withVariableName("enemyBulletY")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("y")
					.withSignalEmitter(otherBulletName)
					.build(), "battleFieldHeight"))
			.build());
		eventObjects.add(enemyBullet);

		localSensors = new ArrayList<>(); 

		Sensor enemyEnergy = new Sensor.Builder("energy")
			.withVariableName("enemyEnergy")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("energy")
					.withSignalEmitter("event")
					.build(), "100d"))
			.build();
		Sensor enemyBearing = new Sensor.Builder("bearing")
			.withVariableName("enemyBearing")
			.withObtainer(new PlainResolvable("((event.getBearing() + 179d) / 359d)"))
			.build();
		Sensor enemyHeading = new Sensor.Builder("heading")
			.withVariableName("enemyHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter("event")
					.build(), "359d"))
			.build();
		Sensor enemyDistance = new Sensor.Builder("distance")
			.withVariableName("enemyDistance")
			.withObtainer(new PlainResolvable("(event.getDistance() / (Math.sqrt(Math.pow(battleFieldWidth, 2) + Math.pow(battleFieldHeight, 2))))"))
			.build();
		Sensor enemyBulletBearing = new Sensor.Builder("bearing")
			.withVariableName("enemyBulletBearing")
			.withObtainer(new PlainResolvable("((event.getBearing() + 179d) / 359d)"))
			.build();
		Sensor wallBearing = new Sensor.Builder("bearing")
			.withVariableName("wallBearing")
			.withObtainer(new PlainResolvable("((event.getBearing() + 179d) / 359d)"))
			.build();
		localSensors.add(enemyEnergy);
		localSensors.add(enemyBearing);
		localSensors.add(enemyHeading);
		localSensors.add(enemyDistance);
		localSensors.add(enemyBulletBearing);
		localSensors.add(wallBearing);

		// initialize actual methods
		methods = new ArrayList<>();

		RobotMethod bulletHit = new RobotMethod("bulletHit");
		bulletHit.addEventObject("getBullet", myBullet);
		bulletHit.addLocalSensor(enemyEnergy);
		methods.add(bulletHit);

		RobotMethod bulletHitBullet = new RobotMethod("bulletHitBullet");
		bulletHitBullet.addEventObject("getBullet", myBullet);
		bulletHitBullet.addEventObject("getHitBullet", enemyBullet);
		methods.add(bulletHitBullet);

		RobotMethod bulletMissed = new RobotMethod("bulletMissed");
		bulletMissed.addEventObject("getBullet", myBullet);
		methods.add(bulletMissed);

		RobotMethod hitByBullet = new RobotMethod("hitByBullet");
		hitByBullet.addEventObject("getBullet", enemyBullet);
		hitByBullet.addLocalSensor(enemyBulletBearing);
		methods.add(hitByBullet);

		RobotMethod hitRobot = new RobotMethod("hitRobot");
		hitRobot.addLocalSensor(enemyBearing);
		hitRobot.addLocalSensor(enemyEnergy);
		methods.add(hitRobot);

		RobotMethod hitWall = new RobotMethod("hitWall");
		hitWall.addLocalSensor(wallBearing);
		methods.add(hitWall);

		RobotMethod scannedRobot = new RobotMethod("scannedRobot");
		scannedRobot.addLocalSensor(enemyBearing);
		scannedRobot.addLocalSensor(enemyDistance);
		scannedRobot.addLocalSensor(enemyEnergy);
		scannedRobot.addLocalSensor(enemyHeading);
		methods.add(scannedRobot);

		RobotMethod status = new RobotMethod("status");
		methods.add(status);
	}

	public List<Sensor> sensors() {
		List<Sensor> allKnownSensors = new ArrayList<>(globalSensors);
		for(EventObject eventObject : eventObjects) {
			List<Sensor> eventObjectSensors = eventObject.getObjectSensors();
			allKnownSensors.addAll(eventObjectSensors);
		}
		allKnownSensors.addAll(localSensors);
		return filterAllowed(allKnownSensors);
	}

	private List<Sensor> filterAllowed(List<Sensor> allKnownSensors) {
		List<Sensor> allowedSensors = new ArrayList<>();
		EvocodeSettings settings = new EvocodeSettings();
		List<String> disabledSensors = settings.getDisabledSensors();
		for(Sensor sensor : allKnownSensors) {
			String identifier = sensor.getVariable();
			if(!disabledSensors.contains(identifier)) {
				allowedSensors.add(sensor);
			}
		}
		return allowedSensors;
	}
}
