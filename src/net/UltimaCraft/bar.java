package net.UltimaCraft;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import net.UltimaCraft.ui.Options;

public class bar extends JDialog {
	private Thread t;
	private JProgressBar bar;
	private JButton launch;
	static JButton texturepacks;
	static JLabel finish;
	private LauncherV2 launcher;

	File Dir = new File(Util.getWorkingDirectory() + "/resourcepacks");
	final String adresse = (Link.URL_BASE_LOCALHOST + Link.URL_Minecraft_Enhanced);
	final File dest = new File(Util.getWorkingDirectory() + File.separator
			+ "resourcepacks" + File.separator + "Minecraft_Enhanced v2.0.zip");

	public bar(final Options options, ProfileInfo profileInfo) {
		super(options);
		Dir.mkdir();
		this.setSize(300, 80);
		this.setTitle("Resource Packs");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(options);
		this.setAlwaysOnTop(true);

		this.texturepacks = profileInfo.texturepacks;
		this.finish = profileInfo.finish;

		bar = new JProgressBar();
		bar.setStringPainted(true);

		this.getContentPane().add(bar, BorderLayout.CENTER);
		t = new Thread(new Traitement());
		t.start();
		this.setVisible(true);
	}

	public class Traitement implements Runnable {
		public void run() {
			FileOutputStream fos = null;
			BufferedReader reader = null;
			try {
				System.out.println(dest + " | " + adresse);

				URL url = new URL(adresse);
				URLConnection conn = url.openConnection();
				conn.addRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				String FileType = conn.getContentType();

				InputStream in = conn.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in));

				int fileLength = conn.getContentLength();

				if (fileLength == -1)
					throw new IOException("Fichier non valide.");
				else
					bar.setMaximum(fileLength);

				bar.setValue(0);
				bar.setIndeterminate(false);

				if (dest == null) {
					String fileName = url.getFile();
					fileName = fileName
							.substring(fileName.lastIndexOf('/') + 1);
					fos = new FileOutputStream(new File(fileName));
				} else
					fos = new FileOutputStream(dest);

				byte[] buff = new byte[1024];
				int n;
				while ((n = in.read(buff)) != -1) {
					fos.write(buff, 0, n);
					bar.setValue(bar.getValue() + n);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fos.flush();
					fos.close();
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Téléchargement Terminé");
				texturepacks.setVisible(false);
				finish.setVisible(true);
				dispose();
			}

		}
	}
}