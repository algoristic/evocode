package de.algoristic.evocode.genetic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import de.algoristic.evocode.run.EvocodeSettings;

public class GenomeTranslator {

	private final JavaCompilerAdaptor compiler;
	private final EvocodeSettings settings;

	public GenomeTranslator() {
		compiler = JavaCompilerAdaptor.getInstance();
		settings = new EvocodeSettings();
	}

	public Phaenotype translate(final Genotype genotype) {
		final Path javaFileSource = genotype.getJavaFile().toPath();
		final Path classFileSource = compiler.compile(javaFileSource).toPath();

		final Path robotLocation = settings.getRobocodeLocation().toPath().resolve("robots");
		final Path targetPath = robotLocation.resolve(genotype.getCompilationTargetPathName());
		
		File targetDir = targetPath.toFile();
		if(!targetDir.exists()) targetDir.mkdirs();
		
		final Path javaFileTarget = targetPath.resolve(javaFileSource.getFileName());
		final Path classFileTarget = targetPath.resolve(classFileSource.getFileName());
		
		try {
			Files.copy(javaFileSource, javaFileTarget);
			Files.copy(classFileSource, classFileTarget);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new Phaenotype(
			javaFileTarget.toFile(),
			classFileTarget.toFile());
	}

	private static class JavaCompilerAdaptor {
		
		private static JavaCompiler JAVA_COMPILER = null;
		
		private final JavaCompiler internal;
		
		private JavaCompilerAdaptor(JavaCompiler internal) {
			this.internal = internal;
		}

		public File compile(Path javaFilePath) {
			File javaFile = javaFilePath.toFile();
			String javaFileName = javaFile.getAbsolutePath();
			int result = internal.run(null, null, null, javaFileName);
			if(result != 0) {
				throw new RuntimeException("file compiled with errors: " + javaFile.getName());
			}
			File classFile = new File(javaFileName.replace(".java", ".class"));
			return classFile;
		}

		public static JavaCompilerAdaptor getInstance() {
			if(JAVA_COMPILER == null) {
				JAVA_COMPILER = ToolProvider.getSystemJavaCompiler();
			}
			return new JavaCompilerAdaptor(JAVA_COMPILER);
		}
	}
}
