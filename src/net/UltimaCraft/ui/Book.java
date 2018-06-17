package net.UltimaCraft.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.UltimaCraft.Link;
import net.UltimaCraft.Util;
import net.UltimaCraft.logger.LoggerU;

public class Book extends JPanel {
	private final static Logger logger = Logger.getLogger(LoggerU.class
			.getName());
	private static final long serialVersionUID = 1L;
	private Image bg;
	private Image bg2;
	private Image bg3;
	private int y = 75;

	private HttpURLConnection connexion = null;
	private String readLine = null;

	String title = "A Venir";
	Font police = new Font("Calibri", Font.BOLD, 25);
	Font police2 = new Font("Calibri", Font.TRUETYPE_FONT, 16);

	File session = new File(Util.getWorkingDirectory() + File.separator
			+ "session");
	BufferedReader in = null;

	public Book() {
		try {
			bg = ImageIO.read(Panel.class
					.getResource("/ressources/fondLauncher.png"));
			bg2 = ImageIO.read(Panel.class
					.getResource("/ressources/parchemin.png"));
			bg3 = ImageIO.read(Panel.class
					.getResource("/ressources/Halloween.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNbLine() {
		HttpURLConnection connexion = null;
		String line[] = new String[10], readLine;
		int nb_line = 0;
		try {
			URL url = new URL(Link.URL_BASE_LOCALHOST + Link.URL_Maj_File);
			connexion = (HttpURLConnection) url.openConnection();
			connexion.setAllowUserInteraction(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connexion.getInputStream()));
			while ((readLine = br.readLine()) != null) {
				line[nb_line] = readLine;
				nb_line++;
			}
			br.close();
		} catch (Exception e1) {
			logger.severe("Fichier maj.txt introuvable ou corrompu");
		} finally {
			connexion.disconnect();
		}
		return nb_line;
	}

	public String getLine(int nb_line) {
		int i = 0;
		try {
			URL url = new URL(Link.URL_BASE_LOCALHOST + Link.URL_Maj_File);
			connexion = (HttpURLConnection) url.openConnection();
			connexion.setAllowUserInteraction(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connexion.getInputStream()));
			while ((readLine = br.readLine()) != null && i < nb_line)
				i++;
			br.close();
		} catch (Exception e1) {
			logger.severe("Fichier maj.txt introuvable ou corrompu");
		} finally {
			connexion.disconnect();
		}
		return readLine;
	}

	public void paintComponent(Graphics g) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int nb_line = getNbLine();
		super.paintComponent(g);
		g.drawImage(this.bg, 0, 0, this.getWidth(), this.getHeight(), this);
		g.drawImage(this.bg2, 0, 0, 280, 380, this);
		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
			g.drawImage(this.bg3, 410, 0, 500, 380, this);
		}
		g.setFont(police);
		g.drawString(title, 110, 50);
		g.setFont(police2);

		for (int i = 0; i < nb_line; i++)
			g.drawString("- " + getLine(i), 23, y + (25 * i));
	}

}
