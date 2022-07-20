package de.algoristic.evocode._poc;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobocodeFileOutputStream;
import robocode.RobotDeathEvent;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;
import robocode.WinEvent;

public class StatusBot extends AdvancedRobot {

	PrintStream printer;

	@Override
	public void run() {
		try {
			long identifier = System.currentTimeMillis();
			String fileName = "StatusBot." + identifier + ".log";
			File logFile = getDataFile(fileName);
			printer = new PrintStream(new RobocodeFileOutputStream(logFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			this.fire(3d);
		}
	}

	@Override
	public void onStatus(StatusEvent e) {
		printStatus("heat="+getGunHeat()+" cooling="+getGunCoolingRate());
		super.onStatus(e);
	}

	private void printStatus(String status) {
		long timestamp = System.currentTimeMillis();
		int round = this.getRoundNum();
		long turn = this.getTime();
		status = new StringBuffer()
			.append(timestamp).append(" [")
			.append(" round: ").append(round)
			.append(" turn: ").append(turn).append(" ] ")
			.append(status)
			.toString();
		try {
			printer.println(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
