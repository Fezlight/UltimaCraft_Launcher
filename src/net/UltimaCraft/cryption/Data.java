package net.UltimaCraft.cryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import net.UltimaCraft.Link;
import net.UltimaCraft.Util;
import net.UltimaCraft.logger.LoggerU;

public class Data {
	private static Data instance;
	private final static Logger logger = Logger.getLogger(LoggerU.class
			.getName());

	private String pseudo = null;
	private String password = null;
	private String memory = "-Xmx1024M";
	private String uuid = null;
	private boolean recupInfos = false;
	private File session = new File(Util.getWorkingDirectory() + File.separator
			+ "session");

	public Data() {
		String[] infos = this.getInfos();
		this.recupInfos = true;
		this.pseudo = infos[0];
		this.password = infos[1];
		this.uuid = infos[2];
		this.memory = infos[3];
	}

	public Data(String pseudo, String password) {
		this.pseudo = pseudo;
		this.password = password;
		this.uuid = getUUIDs();
	}

	public Data(String pseudo, String password, String memory) {
		this.pseudo = pseudo;
		this.password = password;
		this.memory = memory;
		this.uuid = getUUIDs();
	}

	/**
	 * Get Instance of Object Data
	 */
	public static Data getInstance() {
		return instance;
	}

	private String[] getInfos() {
		String[] line = new String[4];
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.session));
			for (int i = 0; i < 4; i++)
				line[i] = in.readLine();
			in.close();
		} catch (Exception e1) {
			logger.severe(e1.toString());
		}
		return line;
	}

	/**
	 * Get UUID of player
	 * 
	 * @return UUID
	 */
	public String getUUIDs() {
		try {
			URL url = new URL(
					"https://api.mojang.com/users/profiles/minecraft/"
							+ this.pseudo);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String Line;
			while ((Line = in.readLine()) != null) {
				String uuid = Line.substring(7, 39);
				return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-"
						+ uuid.substring(12, 16) + "-" + uuid.substring(16, 20)
						+ "-" + uuid.substring(20, 32);
			}
			in.close();
		} catch (Exception ex) {
			logger.severe(ex.toString());
		}
		return null;
	}

	/**
	 * Saving all player data
	 */
	public void saveData() {
		String newPassword = null;
		try {
			newPassword = getCryptedPassword();
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					this.session));
			String NewLine = System.getProperty("line.separator");
			writer.write(this.pseudo + NewLine + newPassword + NewLine
					+ this.uuid + NewLine + this.memory);
			writer.flush();
			writer.close();
		} catch (Exception e2) {
			logger.severe(e2.toString());
		}
	}

	/**
	 * Get Encrypted Password for security
	 * 
	 * @return Encrypted Password
	 */
	public String getCryptedPassword() {
		if (this.recupInfos)
			return this.password;
		return Encrypt.Crypt(this.password);
	}

	/**
	 * Get Username of player
	 * 
	 * @return pseudo
	 */
	public String getUsername() {
		return this.pseudo;
	}

	/**
	 * Get Memory of launcher
	 * 
	 * @return memory
	 */
	public String getMemory() {
		return this.memory;
	}

	/**
	 * Get UUID of player
	 * 
	 * @return UUID
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 * Modify the allocated Memory of the launcher
	 */
	public void setMemory(String memory) {
		this.memory = memory;
	}

	/**
	 * Reset default allocated Memory of the launcher
	 */
	public void resetMemory() {
		this.setMemory("-Xmx1024M");
	}

	/**
	 * Checking username and password by the server<br/>
	 * The server return a (boolean)response
	 * 
	 * @return response
	 */
	public boolean authentification() {
		HttpURLConnection connexion = null;
		String line = null;
		try {
			// Envoie de la requête au site avec les identifiants saisis
			URL url2 = new URL(Link.URL_BASE_LOCALHOST
					+ "connexion/Auth.php?pseudo=" + this.pseudo + "&mdp="
					+ this.password);

			logger.info("Authentification en cours ...");
			connexion = (HttpURLConnection) url2.openConnection();
			connexion.setAllowUserInteraction(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connexion.getInputStream()));
			line = in.readLine();
			line = in.readLine();
			in.close();
		} catch (Exception e1) {
			logger.severe(e1.toString());
			logger.severe("Authentification Echouée");
		} finally {
			connexion.disconnect();
		}
		if (line.contains("TRUE")) {
			return true;
		}
		return false;
	}
}
