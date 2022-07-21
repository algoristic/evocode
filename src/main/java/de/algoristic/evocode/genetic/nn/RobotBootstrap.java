package de.algoristic.evocode.genetic.nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.algoristic.evocode.app.conf.EvocodeSettings;

public class RobotBootstrap {

	private List<RobotMethod> methods;
	private List<Intermitter> intermitters;
	private List<Actor> actors;

	private List<Sensor> localSensors;
	private List<EventObject> eventObjects;
	
	{
		eventObjects = new ArrayList<>();
		String robotStatusObject = "status";
		EventObject robotStatus = new EventObject("RobotStatus", robotStatusObject);
		robotStatus.addSensor(new Sensor.Builder("positionX")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("x")
					.withSignalEmitter(robotStatusObject)
					.build(), "battleFieldWidth"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("positionY")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("y")
					.withSignalEmitter(robotStatusObject)
					.build(), "battleFieldHeight"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("otherPlayers")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("others")
					.withSignalEmitter(robotStatusObject)
					.build(), "playersAtStart"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("round")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("roundNum")
					.withSignalEmitter(robotStatusObject)
					.build(), "numberOfRounds"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("turn")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("time")
					.withSignalEmitter(robotStatusObject)
					.build(), "maxTurnAwareness"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("heading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter(robotStatusObject)
					.build(), "359d"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("radarHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("radarHeading")
					.withSignalEmitter(robotStatusObject)
					.build(), "359d"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("gunHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("gunHeading")
					.withSignalEmitter(robotStatusObject)
					.build(), "359d"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("gunHeat")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("gunHeat")
					.withSignalEmitter(robotStatusObject)
					.build(), "1.6d"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("velocity")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("velocity")
					.withSignalEmitter(robotStatusObject)
					.build(), "8d"))
			.build());
		robotStatus.addSensor(new Sensor.Builder("energy")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("energy")
					.withSignalEmitter(robotStatusObject)
					.build(), "100d"))
			.build());
		eventObjects.add(robotStatus);

		String ownBulletObject = "myBullet";
		EventObject myBullet = new EventObject("Bullet", ownBulletObject);
		myBullet.addSensor(new Sensor.Builder("heading")
			.withVariableName("myBulletHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter(ownBulletObject)
					.build(), "359d"))
			.build());
		myBullet.addSensor(new Sensor.Builder("power")
			.withVariableName("myBulletPower")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("power")
					.withSignalEmitter(ownBulletObject)
					.build(), "3d"))
			.build());
		myBullet.addSensor(new Sensor.Builder("velocity")
			.withVariableName("myBulletVelocity")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("velocity")
					.withSignalEmitter(ownBulletObject)
					.build(), "8d"))
			.build());
		myBullet.addSensor(new Sensor.Builder("x")
			.withVariableName("myBulletX")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("x")
					.withSignalEmitter(ownBulletObject)
					.build(), "battleFieldWidth"))
			.build());
		myBullet.addSensor(new Sensor.Builder("y")
			.withVariableName("myBulletY")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("y")
					.withSignalEmitter(ownBulletObject)
					.build(), "battleFieldHeight"))
			.build());
		eventObjects.add(myBullet);

		String enemyBulletObject = "enemyBullet";
		EventObject enemyBullet = new EventObject("Bullet", enemyBulletObject);
		enemyBullet.addSensor(new Sensor.Builder("heading")
			.withVariableName("enemyBulletHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter(enemyBulletObject)
					.build(), "359d"))
			.build());
		enemyBullet.addSensor(new Sensor.Builder("power")
			.withVariableName("enemyBulletPower")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("power")
					.withSignalEmitter(enemyBulletObject)
					.build(), "3d"))
			.build());
		enemyBullet.addSensor(new Sensor.Builder("velocity")
			.withVariableName("enemyBulletVelocity")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("velocity")
					.withSignalEmitter(enemyBulletObject)
					.build(), "8d"))
			.build());
		enemyBullet.addSensor(new Sensor.Builder("x")
			.withVariableName("enemyBulletX")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("x")
					.withSignalEmitter(enemyBulletObject)
					.build(), "battleFieldWidth"))
			.build());
		enemyBullet.addSensor(new Sensor.Builder("y")
			.withVariableName("enemyBulletY")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("y")
					.withSignalEmitter(enemyBulletObject)
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
		status.addEventObject("getStatus", robotStatus);
		methods.add(status);

		EvocodeSettings settings = new EvocodeSettings();
		int numberOfHiddenNeurons = settings.getHiddenNeurons();
		intermitters = new ArrayList<>();
		for(int i = 0; i < numberOfHiddenNeurons; i++) {
			intermitters.add(new Intermitter(i));
		}

		actors = new ArrayList<>();
		// TODO
	}

	public List<Sensor> sensors() {
		List<Sensor> allKnownSensors = new ArrayList<>();
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
