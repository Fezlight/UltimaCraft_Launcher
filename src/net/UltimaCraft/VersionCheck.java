package net.UltimaCraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.UltimaCraft.logger.LoggerU;

public class VersionCheck {
	// Check of two variable version
	private boolean verification = false;
	// Directory to version.json
	private String fileDir = Util.getWorkingDirectory() + File.separator
			+ "version.json";
	// Variable for local and distant version
	private double local_version = -1, distant_version = -1;
	// Link of distant version.json
	private String link = Link.URL_BASE_LOCALHOST + Link.URL_version;
	private final static Logger logger = Logger.getLogger(LoggerU.class
			.getName());
	private static boolean mainDownload = false;
	private File file = new File(fileDir);
	private URL url = null;
	private int num = 0;
	private String type;
	private String gameVersion = Configuration.gameVersion;
	private static VersionCheck instance;
	private static Double[] version = new Double[4];
	private String[] element = { "versionLauncher", "versionLibs",
			"versionJar", "versionMods" };

	/**
	 * Constructor of class VersionCheck
	 * 
	 * @param type
	 *            -> ex: "Libraries"
	 */
	public VersionCheck(VersionType version) {
		this.setType(version);
		this.setUrl();
		this.getVersionInFile();
		this.execVerif();
	}

	private void setVersionInGlobalVar() {
		if (this.num != 4)
			version[this.num] = this.distant_version;
	}

	public void printCheck() {
		logger.info("[Vérification] de la version " + this.type);
		logger.info(this.url + " --> " + this.file);
		logger.info("Version " + this.type + " Locale: " + this.local_version);
		logger.info("Version " + this.type + " Distante: "
				+ this.distant_version);
	}

	private void printCheckPrincipal() {
		logger.info("[Vérification] " + this.type);
		logger.info("Initialisation principale du dossier: " + mainDownload);
	}

	public void setType(VersionType version) {
		this.type = this.getVersionType(version);
	}

	/**
	 * Get print for check and set num
	 * 
	 * @param version
	 * @return
	 */
	public String getVersionType(VersionType version) {
		switch (version) {
		case MAIN:
			this.num = 0;
			return "du launcher";
		case LIBS:
			this.num = 1;
			return "des bibliothèques";
		case JAR:
			this.num = 2;
			return "du .jar";
		case INIT_FOLDER:
			this.num = 4;
			return "principale";
		default:
			this.num = 3;
			return "des mods";
		}
	}

	/**
	 * Save the float distant_version and local_version in the file
	 * "version.json"
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void saveVersionInFile() throws IOException {
		JSONObject obj = new JSONObject();

		this.setVersionInGlobalVar();

		for (int i = 0; i < 4; i++)
			if (version[i] != null)
				obj.put(element[i], new Float(version[i]));
		obj.put("mainDownload", new Boolean(mainDownload));
		obj.put("gameVersion", new String(this.gameVersion));

		try (FileWriter file = new FileWriter(this.fileDir)) {
			file.write(obj.toJSONString());
		}
	}

	/**
	 * Get distant_version and local_version in the file "version.json"
	 */
	public void getVersionInFile() {
		JSONParser parser = new JSONParser();
		JSONObject local_file = this.checkVersionLocal(parser);
		JSONObject distant_file = this.checkVersionDistante(parser);

		this.setVersion(local_file, distant_file);
	}

	/**
	 * Set variable with data in version.json
	 * 
	 * @param local_file
	 * @param distant_file
	 */
	public void setVersion(JSONObject local_file, JSONObject distant_file) {
		if (local_file != null) {
			if (this.num != 4 && local_file.get(element[this.num]) != null)
				this.local_version = (double) local_file.get(element[this.num]);
			mainDownload = (boolean) local_file.get("mainDownload");
			this.gameVersion = (String) local_file.get("gameVersion");
		}
		if (distant_file != null && this.num != 4)
			this.distant_version = (double) distant_file.get(element[this.num]);
	}

	/**
	 * Set Main Download to finish
	 */
	public void setMainDownload() {
		mainDownload = true;
	}

	/**
	 * Check if verification is true
	 * 
	 * @return boolean
	 */
	public boolean versionOK() {
		if (this.verification) {
			this.setVersionInGlobalVar();
			return true;
		}
		return false;
	}

	/**
	 * Edit (URL)url
	 */
	public void setUrl() {
		try {
			this.url = new URL(this.link);
		} catch (MalformedURLException e) {
			logger.severe(e.toString());
		}
	}

	/**
	 * Execute verification of 2 files(local, distant)
	 */
	public void execVerif() {
		if (this.num == 4) {
			if (mainDownload)
				this.setVerification(true);
			this.printCheckPrincipal();
			return;
		}
		this.printCheck();
		if (this.getLocalVersion() == (double) -1
				|| this.getDistantVersion() == (double) -1)
			return;
		if (this.getDistantVersion() == this.getLocalVersion())
			this.setVerification(true);
	}

	/**
	 * Check Distant version file and create (JsonObject)<br/>
	 * return jsonObject
	 * 
	 * @throws IOException
	 */
	private JSONObject checkVersionDistante(JSONParser parser) {
		JSONObject jsonObject = null;
		try {
			InputStream is = this.url.openStream();
			BufferedReader d = new BufferedReader(new InputStreamReader(is));
			Object obj = parser.parse(d);
			jsonObject = (JSONObject) obj;
			d.close();
		} catch (Exception e) {
			logger.severe(e.toString());
			logger.severe("Vérification de version distante échouée");
		}
		return jsonObject;
	}

	/**
	 * Check Local version file and create (JsonObject)<br/>
	 * return jsonObject
	 */
	private JSONObject checkVersionLocal(JSONParser parser) {
		JSONObject jsonObject = null;
		if (this.file.exists()) {
			try {
				Object obj = parser.parse(new FileReader(this.file));
				jsonObject = (JSONObject) obj;
			} catch (Exception e1) {
				logger.severe(e1.toString());
				logger.severe("Vérification de version locale échouée");
			}
		}
		return jsonObject;
	}

	/**
	 * Edit (boolean)verification
	 * 
	 * @param verification
	 */
	public void setVerification(boolean verification) {
		this.verification = verification;
	}

	/**
	 * Get local version
	 * 
	 * @return (String)local_version
	 */
	public double getLocalVersion() {
		return local_version;
	}

	/**
	 * Get distant version
	 * 
	 * @return (String)distant_version
	 */
	public double getDistantVersion() {
		return distant_version;
	}

	/**
	 * Get Instance of Object VersionCheck
	 */
	public static VersionCheck getInstance() {
		return instance;
	}

	/**
	 * 0:MAIN || 1:LIBS || 2:JAR || 3:MODS || 4:INIT_FOLDER
	 */
	public static enum VersionType {
		MAIN, LIBS, JAR, MODS, INIT_FOLDER;
	}
}
