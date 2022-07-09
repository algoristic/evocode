package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RobotMethods implements Iterable<RobotMethod> {

	private final Map<String, RobotMethod> methods;
	
	{
		methods = new HashMap<>();
		methods.put("g", new RobotMethod("onBulletHit", "BulletHitEvent"));
		methods.put("h", new RobotMethod("onBulletHitBullet", "BulletHitBulletEvent"));
		methods.put("i", new RobotMethod("onBulletMissed", "BulletMissedEvent"));
		methods.put("j", new RobotMethod("onHitByBullet", "HitByBulletEvent"));
		methods.put("k", new RobotMethod("onHitRobot", "HitRobotEvent"));
		methods.put("l", new RobotMethod("onHitWall", "HitWallEvent"));
		methods.put("m", new RobotMethod("onScannedRobot", "ScannedRobotEvent"));
		methods.put("n", new RobotMethod("onStatus", "StatusEvent"));
	}

	public RobotMethod getMethod(StartCodon startCodon) {
		String code = startCodon.getCode();
		return methods.get(code);
	}

	@Override
	public Iterator<RobotMethod> iterator() {
		return methods.values().iterator();
	}

	public String resolve() {
		StringBuffer buffer = new StringBuffer();
		for(RobotMethod method : this) {
			List<ControlStructure> controlStructures = method.getControlStructures();
			if(controlStructures.size() == 0) continue;
			String methodHead = "\t@Override\n\tpublic void [methodName]([eventName] event) {\n"
				.replace("[methodName]", method.getName())
				.replace("[eventName]", method.getEvent());
			buffer = buffer.append(methodHead);
			
			for(ControlStructure controlStructure : controlStructures) {
				String structureHead = "\t\t" + controlStructure.getStart() + "\n";
				String structureEnd = "\t\t" + controlStructure.getEnd() + "\n";
				String structureIndent = "\t\t" + controlStructure.getIndent();
				String structureBody = controlStructure.resolve(structureIndent);
				buffer = buffer.append(structureHead)
					.append(structureBody)
					.append(structureEnd);
			}
			
			buffer = buffer.append("\t}\n\n");
		}
		return buffer.toString();
	}

}
