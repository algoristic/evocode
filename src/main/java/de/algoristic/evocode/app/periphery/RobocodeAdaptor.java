package de.algoristic.evocode.app.periphery;

import java.io.File;
import java.nio.file.Path;

import de.algoristic.evocode.app.conf.EvocodeSettings;
import de.algoristic.evocode.app.conf.EvolutionSettings;
import de.algoristic.evocode.app.io.FitnessValue;
import de.algoristic.evocode.app.io.Logger;
import de.algoristic.evocode.genetic.FitnessFunction;
import de.algoristic.evocode.genetic.Phaenotype;
import de.algoristic.evocode.util.FileSystemUtils;
import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattleMessageEvent;
import robocode.control.events.BattlePausedEvent;
import robocode.control.events.BattleResumedEvent;
import robocode.control.events.BattleStartedEvent;
import robocode.control.events.IBattleListener;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.events.TurnStartedEvent;

public class RobocodeAdaptor {

	private static RobocodeEngine ENGINE_SINGLETON;

	private final RobocodeEngine engine;
	private final EvolutionSettings settings;
	private final EvocodeSettings projectSettings;

	private Logger logger = Logger.getLogger(getClass());

	private RobocodeAdaptor(final RobocodeEngine engine) {
		this.engine = engine;
		settings = new EvolutionSettings();
		projectSettings = new EvocodeSettings();
	}
	
	public static RobocodeAdaptor getInstance() {
		EvocodeSettings settings = new EvocodeSettings();
		if(ENGINE_SINGLETON == null) {
			File engineLocation = settings.getRobocodeLocation();
			ENGINE_SINGLETON = new RobocodeEngine(engineLocation);
		}
		return new RobocodeAdaptor(ENGINE_SINGLETON);
	}

	public FitnessValue eval(Phaenotype phaenotype, FitnessFunction fitnessFunction, boolean visualize) {
		int generation = phaenotype.getGeneration();

		String enemies = settings.getEnemies(generation);
		String robotName = phaenotype.getCompetitionName();
		String selectedRobots = (robotName.concat(", ").concat(enemies));
		RobotSpecification[] robots = engine.getLocalRepository(selectedRobots);

		int width = settings.getBattlefieldWidth(generation);
		int height = settings.getBattlefieldHeight(generation);
		BattlefieldSpecification battlefield = new BattlefieldSpecification(width, height);

		int rounds = settings.getBattleRounds(generation);

		BattleSpecification spec = new BattleSpecification(rounds, battlefield, robots);
		FitnessValue fitnessValue = new FitnessValue();
		engine.addBattleListener(new IBattleListener() {
			@Override public void onTurnStarted(TurnStartedEvent event) { }
			@Override public void onTurnEnded(TurnEndedEvent event) { }
			@Override public void onRoundStarted(RoundStartedEvent event) { }
			@Override public void onRoundEnded(RoundEndedEvent event) { }
			@Override public void onBattleStarted(BattleStartedEvent event) { }
			@Override public void onBattleResumed(BattleResumedEvent event) { }
			@Override public void onBattlePaused(BattlePausedEvent event) { }
			@Override public void onBattleMessage(BattleMessageEvent event) { }
			@Override public void onBattleFinished(BattleFinishedEvent event) { }
			@Override public void onBattleError(BattleErrorEvent event) { }
			@Override public void onBattleCompleted(BattleCompletedEvent event) {
				for(BattleResults results : event.getIndexedResults()) {
					if(results.getTeamLeaderName().equalsIgnoreCase(robotName)) {
						double fitness = fitnessFunction.evaluate(results);
						fitnessValue.setValue(fitness);
						fitnessValue.setRawData(results);
						break;
					}
				}
			}
		});
		engine.setVisible(visualize);
		engine.runBattle(spec);
		engine.waitTillBattleOver();
		logger.write("Ran battle: " + robotName + ", eval: " + fitnessValue);
		return fitnessValue;
	}

	public void cleanupBattlefield() {
		File robocodeLocation = projectSettings.getRobocodeLocation();
		Path robotsLocation = robocodeLocation.toPath().resolve("robots");
		String packageName = projectSettings.getPackageName();
		String packagePath = FileSystemUtils.resolvePackageNameToPath(packageName);
		File directory = robotsLocation.resolve(packagePath).toFile();
		File[] dismissableContent = directory.listFiles();
		for(File dismissableFile : dismissableContent) {
			dismissableFile.delete();
		}
	}
}
