package net.UltimaCraft;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import net.UltimaCraft.logger.LoggerU;

public class Traitement implements Runnable {

	private boolean principal = false;
	private boolean libraries = false;
	private boolean mods = false;
	private boolean jars = false;
	private int nbEtape = 0;
	private Verification verif = null;
	private int etape = 1;

	private LauncherV2 launcher = null;

	private String[][] lien = {
			{ Configuration.Name + ".zip", Link.URL_UltimaCraft_ZIP, "principal" },
			{ "Libraries.zip", Link.URL_Libraries_ZIP, "des bibliothèques" },
			{ "mods.zip", Link.URL_Mods_ZIP, "des mods" },
			{"versions" + File.separator + Configuration.VersionName + File.separator + Configuration.jarVersionName,
					Link.URL_jar, "du .jar" } };

	private final static Logger logger = Logger.getLogger(LoggerU.class .getName());

	public Traitement(Verification verif, LauncherV2 launcher) {
		this.principal = verif.isPrincipal();
		this.libraries = verif.isLibraries();
		this.mods = verif.isMods();
		this.jars = verif.isJar();
		this.nbEtape = verif.getNbEtape();
		this.verif = verif;
		this.launcher = launcher;
	}

	public void run() {
		try {
			if (principal) {
				launcher.state.setText("Etape " + etape + "/" + nbEtape);
				Download object = new Download(this.lien[0][0],
						this.lien[0][1], this.lien[0][2], false, launcher, launcher.pb); // Init Download object for the download of all ".folder"
				object.downloadFile(); // Download File
				if (object.isFinish()) {
					object.extraireDossiers(Util.getWorkingDirectory()
							+ File.separator + this.lien[0][0], "zip"); // And Extract all
					verif.getPrincipalCheck().setMainDownload(); // Set Download Finish
					verif.getPrincipalCheck().saveVersionInFile(); // Save all in version.json
					logger.info("Téléchargement " + lien[0][2] + " terminé");
				} else
					logger.info("Erreur téléchargement " + lien[0][2]
							+ " non terminé");
				this.etape++;
			}
			/**
			 * Vérification Libraries
			 */
			if (libraries) {
				launcher.state.setText("Etape " + etape + "/" + nbEtape);
				Download object = new Download(this.lien[1][0],
						this.lien[1][1], this.lien[1][2], false, launcher, launcher.pb); // Init Download object for the download of libraries
				object.downloadFile(); // Download File
				if (object.isFinish()) {
					object.extraireDossiers(Util.getWorkingDirectory()
							+ File.separator + this.lien[1][0], "zip"); // And Extract all
					verif.getLibsCheck().saveVersionInFile(); // Save all in version.json
					logger.info("Téléchargement " + lien[1][2] + " terminé");
				} else
					logger.info("Erreur téléchargement " + lien[1][2]
							+ " non terminé");
				this.etape++;
			}
			/**
			 * Vérification Mods
			 */
			if (mods) {
				launcher.state.setText("Etape " + etape + "/" + nbEtape);
				Download object = new Download(this.lien[2][0],
						this.lien[2][1], this.lien[2][2], false, launcher, launcher.pb); // Init Download object for the download of libraries
				object.downloadFile(); // Download File
				if (object.isFinish()) {
					object.extraireDossiers(Util.getWorkingDirectory()
							+ File.separator + this.lien[2][0], "zip"); // And Extract all
					verif.getModsCheck().saveVersionInFile(); // Save all in version.json
					logger.info("Téléchargement " + lien[2][2] + " terminé");
				} else
					logger.info("Erreur téléchargement " + lien[2][2]
							+ " non terminé");
				this.etape++;
			}
			/**
			 * Vérification Jar
			 */
			if (jars) {
				launcher.state.setText("Etape " + etape + "/" + nbEtape);
				Download object = new Download(this.lien[3][0],
						this.lien[3][1], this.lien[3][2], false, launcher, launcher.pb); // Init Download object for the download of libraries
				object.downloadFile(); // Download File
				if (object.isFinish()) {
					verif.getJarCheck().saveVersionInFile(); // Save all in version.json
					logger.info("Téléchargement " + lien[3][2] + " terminé");
				} else
					logger.info("Erreur téléchargement " + lien[3][2]
							+ " non terminé");
			}
		} catch (Exception e) {
			logger.severe("Téléchargement échoué");
			logger.severe(e.getMessage());
			JOptionPane
					.showMessageDialog(
							null,
							"Erreur Fatale Lors du Téléchargement\nEnvoyez les logs à un admin",
							"Erreur", JOptionPane.ERROR_MESSAGE);
			launcher.down();
			return;
		} finally {
			logger.info("Lancement du jeu :)");
			launcher.launchGame();
		}
	}
}