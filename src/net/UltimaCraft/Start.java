package net.UltimaCraft;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.UltimaCraft.VersionCheck.VersionType;
import net.UltimaCraft.logger.LoggerU;

public class Start extends JFrame {
	private static final long serialVersionUID = -3394414964198551069L;
	private final static Logger logger = Logger.getLogger(LoggerU.class
			.getName());
	static JOptionPane maj;
	static JOptionPane OutDated;

	public static void main(String args[]) throws IOException,
			InterruptedException {
		setLookAndFeel();
		LoggerU.init();
		VersionCheck version = new VersionCheck(VersionType.MAIN);
		// Checking two version file and compare them
		if (version.versionOK()) {
			logger.info("Version OK");
			new PanelV2(Double.toString(version.getLocalVersion()));
		}
		// If MAJ
		else {
			// Show alert message
			logger.warning("Mise à jour Requise");
			// Download Update from ftp ...
			Download download = new Download(Configuration.jarLauncherName,
					Link.URL_jar_exec, "du launcher", true, null, null);
			download.downloadFile();
			// Modify version in local file
			version.saveVersionInFile();
			// Launch Game by command line
			launch();
		}
	}

	/**
	 * Set the look of the window
	 */
	public static void setLookAndFeel() {
		try {
			Color colr = new Color(0, 204, 0);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("ProgressBar.foreground", colr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Launch PanelV2 by command line
	 * 
	 * @return (Process)process
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static Process launch() throws InterruptedException, IOException {
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(getLaunchCommand());
		Process process = pb.start();
		return process;
	}

	/** EN TRAVAUX **/
	/*
	 * public void OutdatedLauncher() { OutDated = new JOptionPane();
	 * OutDated.showMessageDialog(null, "Launcher Dépassé", "Erreur",
	 * JOptionPane.ERROR_MESSAGE); try { Util.openLink(new
	 * URI("http://ultimacraftpvp.livehost.fr/Launcher.php")); } catch
	 * (URISyntaxException e) { e.printStackTrace(); } }
	 */

	private static ArrayList<String> getLaunchCommand() throws IOException {
		ArrayList<String> commands = new ArrayList<String>();

		if (System.getProperty("os.name").toLowerCase().contains("win"))
			commands.add("\"" + System.getProperty("java.home") + "/bin/java"
					+ "\"");
		else
			commands.add(System.getProperty("java.home") + "/bin/java");

		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			commands.add("-Xdock:name=" + "UltimaCraft");
			commands.add("-XX:+UseConcMarkSweepGC");
			commands.add("-XX:+CMSIncrementalMode");
			commands.add("-XX:-UseAdaptiveSizePolicy");
		}
		commands.add("-jar");
		commands.add(Util.getWorkingDirectory() + File.separator
				+ Configuration.jarLauncherName);
		return commands;

	}
}
