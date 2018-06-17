package net.UltimaCraft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import net.UltimaCraft.cryption.Data;
import net.UltimaCraft.logger.LoggerU;
import net.UltimaCraft.ui.Book;
import net.UltimaCraft.ui.Border;
import net.UltimaCraft.ui.Options;
import net.UltimaCraft.ui.down;
import net.UltimaCraft.ui.launchPane;
import net.UltimaCraft.ui.nicknamePane;
import net.UltimaCraft.ui.optionsPane;

@SuppressWarnings("serial")
public class LauncherV2 extends JFrame implements ActionListener {
	private final static Logger logger = Logger.getLogger(LoggerU.class
			.getName());
	private JPanel launchButton = new launchPane();
	private JPanel nicknamePanel;
	private JPanel optionsPanel = new optionsPane();

	private JLabel titre;
	public JLabel state = new JLabel("...", SwingConstants.CENTER);
	public JButton reduce;
	public JButton quit;

	public JProgressBar pb;
	private Thread t;
	private static LauncherV2 instance;

	private String pseudoIG;
	private nicknamePane nickname = null;

	JPanel down = new down();
	JPanel up = new JPanel();
	JPanel Center = new JPanel();
	File workingDirectory = Util.getWorkingDirectory();

	Font police = new Font("Arial", Font.BOLD, 26);
	Font police2 = new Font("Arial", Font.BOLD, 20);

	File session = new File(workingDirectory + File.separator + "session");

	private JButton playButton;
	private JButton profil;
	private JButton deco;
	private JButton avatar;

	private String local_version;
	private Verification verif = new Verification();
	private Data data = null;

	public LauncherV2(String pseudoIG, String local_version, Data data) {
		this.pseudoIG = pseudoIG;
		this.setTitle("UltimaCraft Beta v" + local_version);
		this.setSize(927, 542);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.DARK_GRAY);
		setIconImage(new ImageIcon(this.getClass().getResource(
				"/ressources/favicon.png")).getImage());
		this.setLayout(new BorderLayout());
		this.local_version = local_version;
		this.nickname = new nicknamePane(pseudoIG);
		this.nicknamePanel = this.nickname;
		this.data = data;

		this.playButton = launchPane.play;
		this.profil = nickname.profil;
		this.deco = nickname.deco;
		this.avatar = nicknamePane.avatar;

		this.setupUi();

		playButton.addActionListener(this);
		this.reduce.addActionListener(this);
		this.quit.addActionListener(this);
		profil.addActionListener(this);
		deco.addActionListener(this);
		avatar.addActionListener(this);

		MouseMover mover = new MouseMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);

		this.repaint();
		this.setVisible(true);
	}

	private void center() {
		Center.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.ipady = 500;
		c.gridx = 0;
		c.gridy = 0;
		Center.add(new Book(), c);
	}

	private void up() {
		up.setBackground(Color.DARK_GRAY);
		up.setLayout(new BorderLayout());
		up.add(reduce);
		up.add(quit);
		up.add(titre);
	}

	public void down() {
		down.removeAll();
		down.setLayout(new GridLayout(0, 3));
		down.add(nicknamePanel, BorderLayout.WEST);
		down.add(launchButton, BorderLayout.CENTER);
		down.add(optionsPanel, BorderLayout.EAST);
		this.add(down, BorderLayout.SOUTH);
		this.repaint();
	}

	private void initUi() {
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

		titre = new JLabel(Configuration.Name, SwingConstants.CENTER);
		titre.setForeground(Color.orange);
		titre.setFont(titre.getFont().deriveFont(60f));
		titre.setBounds(0, 20, 850, 100);

		t = new Thread(new Traitement(verif, this));
		pb = new JProgressBar();
		pb.setStringPainted(true);
		pb.setPreferredSize(new Dimension(927, 20));
		pb.setVisible(false);
		state.setFont(police2);
	}

	private void setupUi() {
		this.initUi();
		this.up();
		this.add(up, BorderLayout.NORTH);
		this.center();
		this.add(Center, BorderLayout.CENTER);
		this.add(new Border(), BorderLayout.LINE_END);
		this.add(new Border(), BorderLayout.LINE_START);
		this.down();
		this.add(down, BorderLayout.SOUTH);
	}

	/** A MODIFIER **/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			// Lister.run(workingDirectory);
			// Téléchargement des fichiers du jeu 1ère fois + Libs + vérif mods
			// + Téléchargement mods
			this.initDownload();
			t.start();
		} else if (e.getSource() == quit) {
			System.exit(0);
		} else if (e.getSource() == avatar) {
			new Options(instance);
		} else if (e.getSource() == reduce) {
			this.setState(JFrame.ICONIFIED);
		} else if (e.getSource() == deco) {
			dispose();
			new PanelV2(this.local_version);
		}
	}

	private void initDownload() {
		down.removeAll();
		down.setLayout(new BorderLayout());
		down.add(state, BorderLayout.CENTER);
		down.add(pb, BorderLayout.PAGE_END);
		pb.setVisible(true);
	}

	protected void launchGame() {
		GameLauncher gl = new GameLauncher(Configuration.gameVersion,
				Util.getWorkingDirectory(), "UltimaCraft", data.getUsername(),
				"UltimaCraft", data.getUUID(), new String[] { "-Xms512M",
						data.getMemory() }, true, false, false, true);
		this.setVisible(false);
		try {
			Process p = gl.launchMinecraft();
			gl.printProcessOutput(p);
			// On attend que minecraft quitte
			p.waitFor();
			// On quitte tout
			System.exit(0);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Impossible de lancer Minecraft !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			logger.severe(e.toString());
			this.setVisible(true);
		}
	}

	/**
	 * Get Instance of Object LauncherV2
	 */
	public static LauncherV2 getInstance() {
		return instance;
	}

	/**
	 * @return pseudoIG
	 */
	public String getPseudoIG() {
		return pseudoIG;
	}
}