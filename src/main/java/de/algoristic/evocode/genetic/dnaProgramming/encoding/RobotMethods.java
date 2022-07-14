package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RobotMethods implements Iterable<RobotMethod> {

	private static final Map<String, RobotMethod> ALL_METHODS;

	static {
		ALL_METHODS = new HashMap<>();
		ALL_METHODS.put("g", new RobotMethod("onBulletHit", "BulletHitEvent"));
		ALL_METHODS.put("h", new RobotMethod("onBulletHitBullet", "BulletHitBulletEvent"));
		ALL_METHODS.put("i", new RobotMethod("onBulletMissed", "BulletMissedEvent"));
		ALL_METHODS.put("j", new RobotMethod("onHitByBullet", "HitByBulletEvent"));
		ALL_METHODS.put("k", new RobotMethod("onHitRobot", "HitRobotEvent"));
		ALL_METHODS.put("l", new RobotMethod("onHitWall", "HitWallEvent"));
		ALL_METHODS.put("m", new RobotMethod("onScannedRobot", "ScannedRobotEvent"));
//		ALL_METHODS.put("n", new RobotMethod("onStatus", "StatusEvent"));
	}

	private final Map<String, RobotMethod> methods;
	
	{
		methods = new HashMap<>();
		for(String terminatorChar : ALL_METHODS.keySet()) {
			RobotMethod method = ALL_METHODS.get(terminatorChar).clone();
			methods.put(terminatorChar, method);
		}
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

	public static String randomTerminatorChar() {
		List<String> ls = terminatorChars();
		int random = (new Random()).nextInt(ls.size());
		return ls.get(random);
	}

	public static List<String> terminatorChars() {
		return new ArrayList<>(ALL_METHODS.keySet());
	}
}
