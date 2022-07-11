package de.algoristic.evocode.run;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.algoristic.evocode.FitnessValue;

public final class GenerationCSV extends RunningFile {

	public GenerationCSV(final File file) {
		super(file);
	}

	@Override
	public boolean createIfNotExists() throws IOException {
		boolean created = super.createIfNotExists();
		if(created) writeLine("individual;fitness;rank;firsts;seconds;thirds;score;survival;bulletDamage;bulletDamageBonus;ramDamage;ramDamageBonus;lastSurvivorBonus");
		return created;
	}

	public void writeFitness(final int individual, final FitnessValue wrapper) {
		double preciseFitness = wrapper.getValue();
		int fitness = Double.valueOf(preciseFitness).intValue();
		int rank = wrapper.getRank();
		int firsts = wrapper.getFirsts();
		int seconds = wrapper.getSeconds();
		int thirds = wrapper.getThirds();		
		int score = wrapper.getScore();
		int survival = wrapper.getSurvival();
		int bulletDamage = wrapper.getBulletDamage();
		int bulletDamageBonus = wrapper.getBulletDamageBonus();
		int ramDamage = wrapper.getRamDamage();
		int ramDamageBonus = wrapper.getRamDamageBonus();
		int lastSurvivorBonus = wrapper.getLastSurvivorBonus();
		String line = IntStream.of(
			individual, fitness, rank, firsts, seconds, thirds, score, survival, bulletDamage, bulletDamageBonus, ramDamage, ramDamageBonus, lastSurvivorBonus)
			.mapToObj(String::valueOf)
			.collect(Collectors.joining(";"))
			.replace(".", ",");
		writeLine(line);
	}
}
