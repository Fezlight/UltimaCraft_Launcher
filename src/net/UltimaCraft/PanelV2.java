package net.UltimaCraft;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.UltimaCraft.cryption.Data;
import net.UltimaCraft.cryption.Encrypt;
import net.UltimaCraft.logger.LoggerU;

public class PanelV2 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 6750355712794659807L;
	private final static Logger logger = Logger.getLogger(LoggerU.class
			.getName());
	private static PanelV2 instance;
	private int y = 0; // -195
	private int x = 10; // 500
	private int ex = 450;
	private int mdpx = -85, mdpy = -180;

	private JLabel titre;
	private JLabel titre2;
	private JLabel Lpseudo;
	private JLabel Lmdp;
	JCheckBox Mdp;
	JButton reduce;
	JButton quit;
	JButton play;
	JButton register;
	JOptionPane erreur;

	private JTextField jtf = new JTextField("");
	private JPasswordField jtf1 = new JPasswordField("");

	private String pseudo;
	private String mdp;
	private String local_version = null;
	Font police = new Font("Arial", Font.BOLD, 17);

	static File session = new File(Util.getWorkingDirectory() + File.separator
			+ "session");

	public PanelV2(String local_version) {
		// Show Window
		this.setTitle(Configuration.Name + " Beta v" + local_version);
		this.setSize(927, 542);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setContentPane(new JLabel(new ImageIcon(this.getClass()
				.getResource("/ressources/fondPanel.png"))));
		this.local_version = local_version;

		/* Title */
		titre = new JLabel("UltimaCraft", SwingConstants.CENTER);
		titre.setForeground(Color.orange);
		titre.setFont(titre.getFont().deriveFont(60f));
		titre.setBounds(0, 20, 950, 50);
		this.add(titre);
		titre2 = new JLabel("v" + local_version, SwingConstants.CENTER);
		titre2.setForeground(Color.DARK_GRAY);
		titre2.setFont(titre2.getFont().deriveFont(20f));
		titre2.setBounds(0, 20, 1300, 85);
		this.add(titre2);

		/* Background */
		this.setIconImage(new ImageIcon(this.getClass().getResource(
				"/ressources/favicon.png")).getImage());

		// Up
		/* Buttons */
		Mdp = new JCheckBox();
		Mdp.setBounds(610 + mdpx, 500 + mdpy, 500, 50);
		Mdp.setBorderPainted(false);
		Mdp.setContentAreaFilled(false);
		Mdp.setFocusPainted(false);
		Mdp.setText("Se souvenir de moi ?");
		Mdp.setFont(police);
		Mdp.setForeground(Color.WHITE);
		this.add(Mdp);

		ImageIcon reduce_a = new ImageIcon(this.getClass().getResource(
				"/ressources/reduce_a2.png"));
		ImageIcon reduce_d = new ImageIcon(this.getClass().getResource(
				"/ressources/reduce_d2.png"));
		reduce = new JButton(reduce_d);
		reduce.setRolloverIcon(reduce_a);
		reduce.setLocation(846, 0);
		reduce.setSize(30, 25);
		reduce.setBorderPainted(false);
		reduce.setContentAreaFilled(false);
		reduce.setFocusPainted(false);

		ImageIcon quit_a = new ImageIcon(this.getClass().getResource(
				"/ressources/quit_a2.png"));
		ImageIcon quit_d = new ImageIcon(this.getClass().getResource(
				"/ressources/quit_d2.png"));
		quit = new JButton(quit_d);
		quit.setRolloverIcon(quit_a);
		quit.setBounds(877, 0, 50, 25);
		quit.setBorderPainted(false);
		quit.setContentAreaFilled(false);
		quit.setFocusPainted(false);

		ImageIcon register_a = new ImageIcon(this.getClass().getResource(
				"/ressources/button_a.png"));
		ImageIcon register_d = new ImageIcon(this.getClass().getResource(
				"/ressources/button.png"));
		register = new JButton(register_d);
		register.setRolloverIcon(register_a);
		register.setBounds(120, 328, 168, 62);
		register.setBorderPainted(false);
		register.setContentAreaFilled(false);
		register.setFocusPainted(false);

		ImageIcon play_a = new ImageIcon(this.getClass().getResource(
				"/ressources/button3_a.png"));
		ImageIcon play_d = new ImageIcon(this.getClass().getResource(
				"/ressources/button3.png"));
		play = new JButton(play_d);
		play.setRolloverIcon(play_a);
		play.setBounds(350, 328, 168, 62);
		play.setBorderPainted(false);
		play.setContentAreaFilled(false);
		play.setFocusPainted(false);

		this.add(reduce);
		this.add(quit);
		this.add(play);
		this.add(register);
		this.reduce.addActionListener(this);
		this.quit.addActionListener(this);
		this.play.addActionListener(this);
		this.register.addActionListener(this);

		// Center
		/* Labels */
		Lpseudo = new JLabel("Pseudo:", SwingConstants.LEFT);
		Lmdp = new JLabel("Mot de passe:", SwingConstants.LEFT);
		Lpseudo.setForeground(Color.WHITE);
		Lmdp.setForeground(Color.WHITE);
		Lpseudo.setFont(police);
		Lmdp.setFont(police);
		Lpseudo.setBounds(110 + x, -10 - y, 260, 472);
		Lmdp.setBounds(110 + x, -9 - y, 260, 472 + 106);
		this.add(Lpseudo);
		this.add(Lmdp);

		/* Fields */
		jtf.setFont(police);
		jtf1.setFont(police);
		jtf.setSize(200 + ex, 25);
		jtf1.setSize(200 + ex, 25);
		jtf.setLocation(110 + x, 235 - y);
		jtf1.setLocation(110 + x, 293 - y);
		this.add(jtf);
		this.add(jtf1);

		MouseMover mover = new MouseMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);

		if (session.exists()) {
			Data data = new Data();
			mdp = Encrypt.Decrypt(data.getCryptedPassword());
			jtf.setText(data.getUsername());
			jtf1.setText(mdp);
			Mdp.setSelected(true);
		}

		this.repaint();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.quit) {
			System.exit(0);
		} else if (e.getSource() == this.reduce) {
			this.setState(JFrame.ICONIFIED);
		} else if (e.getSource() == this.register) {
			try {
				Desktop.getDesktop().browse(
						new URL(Link.URL_BASE_LOCALHOST).toURI());
			} catch (Exception e1) {
				logger.severe(e.toString());
			}
		} else if (e.getSource() == this.play) {
			pseudo = jtf.getText();
			mdp = jtf1.getText();
			Data data = new Data(pseudo, mdp);

			// Vérification de la réponse du site
			if (data.authentification()) {
				logger.info("Le serveur a trouvé l'utilisateur: " + pseudo);
				logger.info("Le mot de passe correspond");
				if (Mdp.isSelected())
					data.saveData();
				logger.info("Authentification réussie");
				dispose();
				// Changement de Fenêtre
				new LauncherV2(this.pseudo, this.local_version, data);
			} else {
				JOptionPane.showMessageDialog(null,
						"Pseudo ou Mot de passe incorrect", "Attention",
						JOptionPane.INFORMATION_MESSAGE);
				logger.warning("Pseudo ou Mot de passe incorrect");
				logger.warning("Authentification échouée");
				jtf1.setText("");
			}
		}
	}

	/**
	 * Get Instance of Object PanelV2
	 */
	public static PanelV2 getInstance() {
		return instance;
	}
}
