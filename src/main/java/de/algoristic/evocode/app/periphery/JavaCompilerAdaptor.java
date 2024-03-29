package de.algoristic.evocode.app.periphery;

import java.io.File;
import java.nio.file.Path;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class JavaCompilerAdaptor {

	private static JavaCompiler JAVA_COMPILER = null;

	private final JavaCompiler internal;

	private JavaCompilerAdaptor(JavaCompiler internal) {
		this.internal = internal;
	}

	public File compile(Path javaFilePath) {
		File javaFile = javaFilePath.toFile();
		String javaFileName = javaFile.getAbsolutePath();
		int result = internal.run(null, null, null, javaFileName);
		if (result != 0) {
			throw new RuntimeException("file compiled with errors: " + javaFile.getName());
		}
		File classFile = new File(javaFileName.replace(".java", ".class"));
		return classFile;
	}

	public static JavaCompilerAdaptor getInstance() {
		if (JAVA_COMPILER == null) {
			JAVA_COMPILER = ToolProvider.getSystemJavaCompiler();
		}
		return new JavaCompilerAdaptor(JAVA_COMPILER);
	}
}