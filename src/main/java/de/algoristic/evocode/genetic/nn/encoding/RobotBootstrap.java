package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.algoristic.evocode.app.conf.EvocodeSettings;

public class RobotBootstrap {

	private final EvocodeSettings settings;

	private List<RobotMethod> methods;
	private List<Intermitter> intermitters;
	private List<Actor> actors;

	private List<Sensor> localSensors;
	private List<EventObject> eventObjects;

	private List<Sensor> _lazy_connectedSensors;
	private List<Intermitter> _lazy_connectedIntermitters;
	private List<Actor> _lazy_connectedActors;

	public RobotBootstrap() {
		settings = new EvocodeSettings();
		init();
	}

	private void init() {
		eventObjects = new ArrayList<>();
		int sensorCounter = 0;
		String robotStatusObject = "status";
		EventObject robotStatus = new EventObject("RobotStatus", robotStatusObject);
		robotStatus.addSensor(new Sensor.Builder("positionX")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("x")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "battleFieldWidth"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("positionY")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("y")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "battleFieldHeight"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("otherPlayers")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("others")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "playersAtStart"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("round")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("roundNum")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "numberOfRounds"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("turn")
			.withObtainer(new PlainResolvable("Math.min(1, ((double) " + robotStatusObject +".getTime() / maxTurnAwareness))"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("heading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "359d"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("radarHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("radarHeading")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "359d"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("gunHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("gunHeading")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "359d"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("gunHeat")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("gunHeat")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "3.0d"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("velocity")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("velocity")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "8d"))
			.build(sensorCounter++));
		robotStatus.addSensor(new Sensor.Builder("energy")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("energy")
					.withSignalEmitter(robotStatusObject)
					.build(-1), "100d"))
			.build(sensorCounter++));
		eventObjects.add(robotStatus);

		String ownBulletObject = "myBullet";
		EventObject myBullet = new EventObject("Bullet", ownBulletObject);
		myBullet.addSensor(new Sensor.Builder("heading")
			.withVariableName("myBulletHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter(ownBulletObject)
					.build(-1), "359d"))
			.build(sensorCounter++));
		myBullet.addSensor(new Sensor.Builder("power")
			.withVariableName("myBulletPower")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("power")
					.withSignalEmitter(ownBulletObject)
					.build(-1), "3d"))
			.build(sensorCounter++));
		myBullet.addSensor(new Sensor.Builder("velocity")
			.withVariableName("myBulletVelocity")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("velocity")
					.withSignalEmitter(ownBulletObject)
					.build(-1), "8d"))
			.build(sensorCounter++));
		myBullet.addSensor(new Sensor.Builder("x")
			.withVariableName("myBulletX")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("x")
					.withSignalEmitter(ownBulletObject)
					.build(-1), "battleFieldWidth"))
			.build(sensorCounter++));
		myBullet.addSensor(new Sensor.Builder("y")
			.withVariableName("myBulletY")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("y")
					.withSignalEmitter(ownBulletObject)
					.build(-1), "battleFieldHeight"))
			.build(sensorCounter++));
		eventObjects.add(myBullet);

		String enemyBulletObject = "enemyBullet";
		EventObject enemyBullet = new EventObject("Bullet", enemyBulletObject);
		enemyBullet.addSensor(new Sensor.Builder("heading")
			.withVariableName("enemyBulletHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter(enemyBulletObject)
					.build(-1), "359d"))
			.build(sensorCounter++));
		enemyBullet.addSensor(new Sensor.Builder("power")
			.withVariableName("enemyBulletPower")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("power")
					.withSignalEmitter(enemyBulletObject)
					.build(-1), "3d"))
			.build(sensorCounter++));
		enemyBullet.addSensor(new Sensor.Builder("velocity")
			.withVariableName("enemyBulletVelocity")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("velocity")
					.withSignalEmitter(enemyBulletObject)
					.build(-1), "8d"))
			.build(sensorCounter++));
		enemyBullet.addSensor(new Sensor.Builder("x")
			.withVariableName("enemyBulletX")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("x")
					.withSignalEmitter(enemyBulletObject)
					.build(-1), "battleFieldWidth"))
			.build(sensorCounter++));
		enemyBullet.addSensor(new Sensor.Builder("y")
			.withVariableName("enemyBulletY")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("y")
					.withSignalEmitter(enemyBulletObject)
					.build(-1), "battleFieldHeight"))
			.build(sensorCounter++));
		eventObjects.add(enemyBullet);

		localSensors = new ArrayList<>(); 
		Sensor enemyEnergy = new Sensor.Builder("energy")
			.withVariableName("enemyEnergy")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("energy")
					.withSignalEmitter("event")
					.build(-1), "100d"))
			.build(sensorCounter++);
		Sensor enemyBearing = new Sensor.Builder("bearing")
			.withVariableName("enemyBearing")
			.withObtainer(new PlainResolvable("((event.getBearing() + 179d) / 359d)"))
			.build(sensorCounter++);
		Sensor enemyHeading = new Sensor.Builder("heading")
			.withVariableName("enemyHeading")
			.withObtainer(new SensorDivider(
				new Sensor.Builder("heading")
					.withSignalEmitter("event")
					.build(-1), "359d"))
			.build(sensorCounter++);
		Sensor enemyDistance = new Sensor.Builder("distance")
			.withVariableName("enemyDistance")
			.withObtainer(new PlainResolvable("(event.getDistance() / (Math.hypot(battleFieldWidth, battleFieldHeight)))"))
			.build(sensorCounter++);
		Sensor enemyBulletBearing = new Sensor.Builder("bearing")
			.withVariableName("enemyBulletBearing")
			.withObtainer(new PlainResolvable("((event.getBearing() + 179d) / 359d)"))
			.build(sensorCounter++);
		Sensor wallBearing = new Sensor.Builder("bearing")
			.withVariableName("wallBearing")
			.withObtainer(new PlainResolvable("((event.getBearing() + 179d) / 359d)"))
			.build(sensorCounter++);
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
		status.addBoilerplateCode("processStimuli()");
		methods.add(status);

		EvocodeSettings settings = new EvocodeSettings();
		int numberOfHiddenNeurons = settings.getHiddenNeurons();
		intermitters = new ArrayList<>();
		for(int i = 0; i < numberOfHiddenNeurons; i++) {
			intermitters.add(new Intermitter(i));
		}

		actors = new ArrayList<>();
		int actorCounter = 0;
		ValueRangeManager rangeManager = new ValueRangeManager(); 
		for(ActionPrototype prototype : ActionPrototype.values()) {
			String action = prototype.getName();
			double absMin = prototype.getAbsMin();
			double absMax = prototype.getAbsMax();
			double min = settings.getActionMin(action, absMin);
			double max = settings.getActionMax(action, absMax);
			List<ValueRange> ranges = rangeManager.getRanges(action);
			for(ValueRange range : ranges) {
				double pMin = range.getFromIncluded();
				double pMax = range.getToIncluded();
				Actor actor = Actor.of(action, min, max)
					.withRange(pMin, pMax)
					.build(actorCounter++);
				actors.add(actor);
			}
		}
	}

	public List<Sensor> sensors() {
		List<Sensor> allKnownSensors = new ArrayList<>();
		for(EventObject eventObject : eventObjects) {
			List<Sensor> eventObjectSensors = eventObject.getObjectSensors();
			allKnownSensors.addAll(eventObjectSensors);
		}
		allKnownSensors.addAll(localSensors);
		return filterAllowedSensors(allKnownSensors);
	}

	public List<Sensor> getConnectedSensors() {
		if(_lazy_connectedSensors == null) {
			_lazy_connectedSensors = getConnectedNeurons(sensors());
		}
		return _lazy_connectedSensors;
	}

	public List<Intermitter> intermitters() {
		return new ArrayList<Intermitter>(intermitters);
	}

	public List<Intermitter> getConnectedIntermitters() {
		if(_lazy_connectedIntermitters == null) {
			_lazy_connectedIntermitters = getConnectedNeurons(intermitters());
		}
		return _lazy_connectedIntermitters;
	}

	private <N extends SendingNeuron> List<N> getConnectedNeurons(List<N> ls) {
		List<N> connected = new ArrayList<>();
		for(N neuron : ls) {
			boolean foundConnection = false;
			List<Connection> connections = neuron.getReceivers();
			for(Connection connection : connections) {
				ReceivingNeuron receiver = connection.getSink();
				if(receiver instanceof Actor) {
					foundConnection = true;
					break;
				}
				if(receiver instanceof Intermitter) {
					Intermitter intermitter = (Intermitter) receiver;
					foundConnection = searchOutboundConnections(intermitter, new ArrayList<>());
					if(foundConnection) break;
				}
			}
			if(foundConnection) connected.add(neuron);
		}
		return connected;
	}

	public static boolean searchOutboundConnections(Intermitter intermitter, List<Intermitter> connectionChain) {
		connectionChain.add(intermitter);
		List<Connection> connections = intermitter.getReceivers();
		for(Connection connection : connections) {
			ReceivingNeuron receiver = connection.getSink();
			if(receiver instanceof Actor) return true;
			else {
				Intermitter next = (Intermitter) receiver;
				if(connectionChain.contains(next)) continue;
				boolean foundConnection = searchOutboundConnections(next, new ArrayList<>(connectionChain));
				if(foundConnection) return true;
			}
		}
		return false;
	}

	public List<Actor> actors() {
		return filterAllowedActors(actors);
	}

	public List<Actor> getConnectedActors() {
		if(_lazy_connectedActors == null) {
			List<Actor> connectedActors = new ArrayList<>();
			List<Sensor> sensors = getConnectedSensors();
			addConnectedActors(connectedActors, sensors);
			List<Intermitter> intermitters = getConnectedIntermitters();
			addConnectedActors(connectedActors, intermitters);
			connectedActors.sort(new Comparator<Actor>() {
				@Override public int compare(Actor a_1, Actor a_2) {
					return a_1.getUUID().compareTo(a_2.getUUID());
				}
			});
			_lazy_connectedActors = connectedActors;
		}
		return _lazy_connectedActors;
	}

	private <S extends SendingNeuron> void addConnectedActors(List<Actor> actors, List<S> list) {
		for(S sendingNeuron : list) {
			List<Connection> connections = sendingNeuron.getReceivers();
			for(Connection connection : connections) {
				ReceivingNeuron neuron = connection.getSink();
				if(neuron instanceof Actor) {
					if(actors.contains(neuron)) continue;
					actors.add((Actor) neuron);
				}
			}
		}
	}

	private List<Sensor> filterAllowedSensors(List<Sensor> allKnownSensors) {
		List<Sensor> sensors = new ArrayList<>();
		List<String> allowedSensors = settings.getAllowedSensors();
		for(Sensor sensor : allKnownSensors) {
			String identifier = sensor.getVariable();
			if(allowedSensors.contains(identifier)) sensors.add(sensor);
		}
		return sensors;
	}

	private List<Actor> filterAllowedActors(List<Actor> allKnownActors) {
		List<Actor> actors = new ArrayList<Actor>();
		List<String> allowedActors = settings.getAllowedActors();
		for(Actor actor : allKnownActors) {
			String name = actor.getName();
			if(allowedActors.contains(name)) actors.add(actor);
		}
		return actors;
	}

	public List<RobotMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<RobotMethod> methods) {
		this.methods = methods;
	}
}
