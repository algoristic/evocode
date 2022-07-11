package de.algoristic.evocode.util;

public abstract class FileSystemUtils {

	public static String resolvePackageNameToPath(String packageName) {
		return packageName.replace(".", "/");
	}

}
