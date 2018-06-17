package net.UltimaCraft.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class launchPane extends JPanel {
	private static final long serialVersionUID = 1L;
	public static JButton play;

	public launchPane() {
		this.setOpaque(false);
		ImageIcon jouer_I = new ImageIcon(this.getClass().getResource(
				"/ressources/launch.png"));
		ImageIcon jouer_a = new ImageIcon(this.getClass().getResource(
				"/ressources/launch2.png"));
		play = new JButton(jouer_I);
		play.setRolloverIcon(jouer_a);
		play.setBorderPainted(false);
		play.setContentAreaFilled(false);
		play.setFocusPainted(false);

		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridy = 0;
		constraints.gridx = 0;
		this.add(play);
	}

}
