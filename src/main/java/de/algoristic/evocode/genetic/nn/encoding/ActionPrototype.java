package de.algoristic.evocode.genetic.nn.encoding;

public enum ActionPrototype {

	ahead("ahead", ActionCategory.move),
	back("back", ActionCategory.move),
	fire("fire", ActionCategory.fire),
	turnLeft("turnLeft", ActionCategory.turn),
	turnRight("turnRight", ActionCategory.turn),
	turnGunLeft("turnGunLeft", ActionCategory.turn),
	turnGunRight("turnGunRight", ActionCategory.turn),
	turnRadarLeft("turnRadarLeft", ActionCategory.turn),
	turnRadarRight("turnRadarRight", ActionCategory.turn),
	raiseTurnAwareness("raiseTurnAwareness", ActionCategory.awareness),
	reduceTurnAwareness("reduceTurnAwareness", ActionCategory.awareness);

	private String name;
	private ActionCategory category;

	private ActionPrototype(String name, ActionCategory category) {
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}
	
	public String getCategory() {
		return category.name();
	}

	public double getAbsMin() {
		return category.getAbsMin();
	}

	public double getAbsMax() {
		return category.getAbsMax();
	}
}
