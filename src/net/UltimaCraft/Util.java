package net.UltimaCraft;

import java.io.File;
import java.net.URI;

public class Util {
	public static final String APPLICATION_NAME = Configuration.Name;
	public static final String DIRECTORY = APPLICATION_NAME + "_2.0";

	public static OS getPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win"))
			return OS.WINDOWS;
		if (osName.contains("mac"))
			return OS.MACOS;
		if (osName.contains("linux"))
			return OS.LINUX;
		if (osName.contains("unix"))
			return OS.LINUX;
		return OS.UNKNOWN;
	}

	public static File getWorkingDirectory() {
		String customName = DIRECTORY;
		String userHome = System.getProperty("user.home", ".");
		File workingDirectory;

		switch (getPlatform()) {
		case SOLARIS:
		case LINUX:
			workingDirectory = new File(userHome, "." + customName + "/");
			break;
		case WINDOWS:
			String applicationData = System.getenv("APPDATA");
			String folder = applicationData != null ? applicationData
					: userHome;

			workingDirectory = new File(folder, "." + customName + "/");
			break;
		case MACOS:
			workingDirectory = new File(userHome,
					"Library/Application Support/" + customName + "/");
			break;
		default:
			workingDirectory = new File(userHome, customName + "/");
		}
		
		if(!workingDirectory.exists())
			workingDirectory.mkdir();

		return workingDirectory;
	}

	public static void openLink(final URI link) {
		try {
			final Class<?> desktopClass = Class.forName("java.awt.Desktop");
			final Object o = desktopClass.getMethod("getDesktop",
					(Class<?>[]) new Class[0]).invoke(null, new Object[0]);
			desktopClass.getMethod("browse", URI.class).invoke(o, link);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void rmdir(final File folder) {
		// Vérifier si le dossier existe
		if (folder.isDirectory()) {
			File[] list = folder.listFiles();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					File tmpF = list[i];
					if (tmpF.isDirectory()) {
						rmdir(tmpF);
					}
					tmpF.delete();
				}
			}
			if (!folder.delete()) {
				System.out.println("Impossible de supprimer le dossier : "
						+ folder);
			}
		}
	}

	public static enum OS {
		WINDOWS, MACOS, SOLARIS, LINUX, UNKNOWN;
	}
}