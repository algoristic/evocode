package de.algoristic.evocode.run;

import java.io.IOException;

import de.algoristic.evocode.context.FilesystemContext;

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
		try {
			projectCSV.createIfNotExists();
		} catch (IOException e) {
			throw new RuntimeException("Could not setup project files", e);
		}
	}

}
