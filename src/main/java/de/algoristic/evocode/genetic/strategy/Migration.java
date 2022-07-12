package de.algoristic.evocode.genetic.strategy;

public interface Migration {

	int getEpoch();
	double getChance();
	int getTargetIsland(int currentIslandId, int numberOfIslands);

	default boolean isApplicable(int generation) {
		return ((generation % getEpoch()) == 0);
	}

	default boolean migrate() {
		double p = Math.random();
		boolean doMigrate = (p <= getChance());
		return doMigrate;
	}
}
