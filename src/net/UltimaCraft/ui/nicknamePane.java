package net.UltimaCraft.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class nicknamePane extends JPanel {
	private static final long serialVersionUID = 6238968207458709623L;
	private static nicknamePane instance;
	private Font police2 = new Font("Arial", Font.BOLD, 16);

	public static JButton avatar = new JButton();
	private JLabel nickname;
	public JButton profil = new JButton("Profil");
	public JButton deco = new JButton("Déconnexion");
	private ImageIcon imageIcon;

	public nicknamePane(String pseudo) {
		this.setOpaque(false);
		avatar.setBorderPainted(false);
		avatar.setContentAreaFilled(false);
		avatar.setFocusPainted(false);
		deco.setFocusPainted(false);
		deco.setBorderPainted(false);
		deco.setOpaque(false);
		profil.setFocusPainted(false);
		profil.setBorderPainted(false);
		profil.setOpaque(false);
		nickname = new JLabel(pseudo);
		nickname.setFont(police2);
		try {
			imageIcon = new ImageIcon(new ImageIcon(new URL(
					"https://minotar.net/avatar/" + pseudo + "/64.png"))
					.getImage().getScaledInstance(64, 64,
							java.awt.Image.SCALE_SMOOTH));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(0, 5, 0, 0));
		avatar.setIcon(imageIcon);
		this.add(avatar, BorderLayout.LINE_START);
		final JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
		buttonPanel.add(this.nickname);
		buttonPanel.add(profil);
		buttonPanel.add(deco);
		buttonPanel.setBorder(new EmptyBorder(2, 0, 8, 100));
		buttonPanel.setOpaque(false);
		this.add(buttonPanel);
	}

	/**
	 * Get Instance of Object nickname
	 */
	public static nicknamePane getInstance() {
		return instance;
	}
}
