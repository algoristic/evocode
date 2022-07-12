package de.algoristic.evocode.genetic.strategy;

public abstract class AbstractMigration implements Migration {

	private final int epoch;
	private final double chance;

	protected AbstractMigration(final int epoch, final double chance) {
		this.epoch = epoch;
		this.chance = chance;
	}

	@Override
	public int getEpoch() {
		return epoch;
	}

	@Override
	public double getChance() {
		return chance;
	}
}
