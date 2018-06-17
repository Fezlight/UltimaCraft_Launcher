package net.UltimaCraft;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JProgressBar;

import net.UltimaCraft.logger.LoggerU;

public class Download {
	// Link of distant main directory
	private String link = Link.URL_BASE_LOCALHOST;
	private String type;
	// Main Directory
	private String fileDir = Util.getWorkingDirectory() + File.separator;
	// State of Download
	private boolean finish = false;
	// Type of Download(If it is the launcher download, progress bar is not
	// useful)
	private boolean launcherDownload = false;
	private JProgressBar pb = null;
	private final static Logger logger = Logger.getLogger(LoggerU.class
			.getName());
	private File file = null;
	private URL url = null;
	private HttpURLConnection connexion = null;
	private FileOutputStream fos = null;
	private BufferedReader reader = null;
	private static Download instance;

	public Download(String fileName, String urlToFile, String type,
			boolean mainDownload, LauncherV2 launcher, JProgressBar pb) {
		this.type = type;
		logger.info("Téléchargement " + type);
		this.setFile(fileName);
		this.setUrl(this.getLink() + urlToFile);
		this.launcherDownload = mainDownload;
		this.pb = pb;
	}

	/**
	 * Edit (File)file
	 * 
	 * @param fileName
	 */
	public void setFile(String fileName) {
		this.fileDir += fileName;
		this.file = new File(this.fileDir);
	}

	/**
	 * Edit (URL)url
	 * 
	 * @param link
	 */
	public void setUrl(String link) {
		try {
			this.url = new URL(link);
		} catch (MalformedURLException e) {
			logger.severe(e.getStackTrace().toString());
		}
	}

	/**
	 * Edit state of download
	 * 
	 * @param state
	 */
	public void setFinish(boolean state) {
		this.finish = state;
	}

	/**
	 * Check if download is finish
	 * 
	 * @return (boolean)finish
	 */
	public boolean isFinish() {
		return finish;
	}

	/**
	 * Get (URL)url
	 * 
	 * @return (URL)url
	 */
	public URL getUrl() {
		return this.url;
	}

	/**
	 * Get (String)link
	 * 
	 * @return (String)link
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * Get (File)file
	 * 
	 * @return (File)file
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * Edit the HttpUrlConn Parameters<br/>
	 * --> connexion = (HttpURLConnection) this.getUrl().openConnection();<br/>
	 * --> connexion.setAllowUserInteraction(true)
	 * 
	 * @throws IOException
	 */
	public void setHttpUrlConnexion() throws IOException {
		this.connexion = (HttpURLConnection) this.getUrl().openConnection();
		this.connexion.setAllowUserInteraction(true);
	}

	/**
	 * Download File from ftp
	 * 
	 * @throws IOException
	 */
	public void downloadFile() throws IOException {
		try {
			logger.info(this.getUrl() + " --> " + this.getFile());

			this.setHttpUrlConnexion();
			InputStream in = this.connexion.getInputStream();
			this.reader = new BufferedReader(new InputStreamReader(in));
			int fileLength = this.connexion.getContentLength();

			if (this.pb != null) {
				if (fileLength == -1)
					throw new IOException("Fichier non valide.");
				else
					this.pb.setMaximum(fileLength);
				this.pb.setValue(0);
			}

			this.fos = new FileOutputStream(this.getFile());

			byte[] buff = new byte[1024];
			int n;
			while ((n = in.read(buff)) != -1) {
				this.fos.write(buff, 0, n);
				if (!this.launcherDownload)
					this.pb.setValue(this.pb.getValue() + n);
			}

			if (this.pb != null) {
				if (this.pb.getValue() == fileLength)
					this.setFinish(true);
			} else
				this.setFinish(true);
		} catch (Exception e) {
			logger.severe(e.toString());
			logger.severe("Téléchargement " + this.type + " échoué");
			e.printStackTrace();
			return;
		} finally {
			this.fos.flush();
			this.fos.close();
			this.reader.close();
		}
	}

	/**
	 * Extraction of file
	 */
	public void extraireDossiers(String fileDir, String extension) {
		try {
			logger.info("Extraction de fichier " + fileDir + ".....");
			int BUFFER = 2048;
			File fichier = new File(fileDir);

			ZipFile zip = new ZipFile(fichier);

			Enumeration<?> fichierZipEntries = zip.entries();

			while (fichierZipEntries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) fichierZipEntries.nextElement();
				String currentEntry = entry.getName();
				File fichierDest = new File(Util.getWorkingDirectory(),
						currentEntry);
				File destinationParent = fichierDest.getParentFile();

				destinationParent.mkdirs();

				if (!entry.isDirectory()) {
					BufferedInputStream is = new BufferedInputStream(
							zip.getInputStream(entry));
					int currentByte;
					byte data[] = new byte[BUFFER];

					FileOutputStream fichier_out = new FileOutputStream(
							fichierDest);
					BufferedOutputStream dest = new BufferedOutputStream(
							fichier_out, BUFFER);
					while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, currentByte);
					}
					dest.flush();
					dest.close();
					is.close();
				}
				if (currentEntry.endsWith(extension)) {
					this.extraireDossiers(fichierDest.getAbsolutePath(),
							extension);
				}
			}
			zip.close();
		} catch (IOException err) {
			logger.severe(err.toString());
			err.printStackTrace();
			logger.info("Extraction échoué");
			return;
		} finally {
			logger.info("Extraction Terminé");
		}
	}

	/**
	 * Get Instance of Object Download
	 */
	public static Download getInstance() {
		return instance;
	}
}
