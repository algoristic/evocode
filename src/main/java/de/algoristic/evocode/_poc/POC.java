package de.algoristic.evocode._poc;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

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

public class POC {

	public static void main(String[] args) throws Throwable {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		String path = "C:\\Users\\male233\\Documents\\MEGA\\Archiv\\evocode\\de\\algoristic\\evocode\\TestingRobot";
		String targetPath = "C:\\robocode\\robots\\de\\algoristic\\evocode\\TestingRobot.class";
		String targetPathCU = "C:\\robocode\\robots\\de\\algoristic\\evocode\\TestingRobot.java";
		
		String compilationFile = (path + ".java");
		File compiledFile = new File(path + ".class");
		if(compiledFile.exists()) compiledFile.delete();
		int result = compiler.run(null, null, null, compilationFile);
		System.out.println(result);
		Files.copy(
				Paths.get(compiledFile.toURI()),
				Paths.get(targetPath),
				StandardCopyOption.REPLACE_EXISTING);
		Files.copy(
				Paths.get(compilationFile),
				Paths.get(targetPathCU),
				StandardCopyOption.REPLACE_EXISTING);
		
		RobocodeEngine engine = new RobocodeEngine(new File("C:\\robocode"));
		int numRounds = 10;
		BattlefieldSpecification battlefieldSize = new BattlefieldSpecification(800, 600);
		RobotSpecification[] robots = engine.getLocalRepository("de.algoristic.evocode.TestingRobot*, sample.Crazy");
		BattleSpecification battleSpecification = new BattleSpecification(numRounds, battlefieldSize, robots);
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
					System.out.println("battle result: " + results);
					System.out.println("firsts = " + results.getFirsts());
					System.out.println("leader = " + results.getTeamLeaderName());
				}
			}
		});
		engine.runBattle(battleSpecification);
	}
	
}
