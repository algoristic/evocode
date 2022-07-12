package de.algoristic.evocode.genetic.strategy;

public class RingMigration extends AbstractMigration {

	RingMigration(int epoch, double chance) {
		super(epoch, chance);
	}

	@Override
	public int getTargetIsland(int currentIslandId, int numberOfIslands) {
		if (currentIslandId == (numberOfIslands - 1)) {
			return 0;
		} else {
			return (currentIslandId + 1);
		}
	}
}
