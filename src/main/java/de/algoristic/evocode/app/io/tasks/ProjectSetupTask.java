package de.algoristic.evocode.app.io.tasks;

import java.io.IOException;

import de.algoristic.evocode.app.conf.FilesystemContext;
import de.algoristic.evocode.app.io.files.IslandsCSV;
import de.algoristic.evocode.app.io.files.ProjectCSV;

public class ProjectSetupTask {

	private final FilesystemContext context;

	public ProjectSetupTask() {
		this(new FilesystemContext());
	}

	private ProjectSetupTask(final FilesystemContext context) {
		this.context = context;
	}

	public void prepareProject() {
		ProjectCSV projectCSV = context.getProjectCSV();
		IslandsCSV islandsCSV = context.getIslandsCSV();
		try {
			projectCSV.createIfNotExists();
			islandsCSV.createIfNotExists();
		} catch (IOException e) {
			throw new RuntimeException("Could not setup project files", e);
		}
	}

}
