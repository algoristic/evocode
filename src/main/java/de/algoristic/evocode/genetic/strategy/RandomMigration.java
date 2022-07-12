package de.algoristic.evocode.genetic.strategy;

import java.util.Random;

public class RandomMigration extends AbstractMigration {

	RandomMigration(int epoch, double chance) {
		super(epoch, chance);
	}

	@Override
	public int getTargetIsland(int currentIslandId, int numberOfIslands) {
		int target = (new Random()).nextInt(numberOfIslands);
		return target;
	}
}
