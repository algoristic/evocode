package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.HashMap;
import java.util.Map;

public class RobotMethods {

	private final Map<String, RobotMethod> methods;
	
	{
		methods = new HashMap<>();
		methods.put("g", new RobotMethod("onBulletHit", "BulletHitEvent"));
		methods.put("h", new RobotMethod("onBulletHitBullet", "BulletHitBulletEvent"));
		methods.put("i", new RobotMethod("onBulletMissed", "BulletMissedEvent"));
		methods.put("j", new RobotMethod("onHitByBullet", "HitByBulletEvent"));
		methods.put("k", new RobotMethod("onHitRobot", "HitRobotEvent"));
		methods.put("l", new RobotMethod("onHitWall", "HitWallEvent"));
		methods.put("m", new RobotMethod("HitWallEvent", "BulletHitEvent"));
		methods.put("n", new RobotMethod("onScannedRobot", "ScannedRobotEvent"));
		methods.put("o", new RobotMethod("onStatus", "StatusEvent"));
	}

	public RobotMethod getMethod(String startCodon) {
		return methods.get(startCodon);
	}
	
}
