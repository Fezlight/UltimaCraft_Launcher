package net.UltimaCraft.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Border extends JPanel {
	private static final long serialVersionUID = 1236134424697416823L;

	public void paintComponent(Graphics g) {
		// On choisit une couleur de fond pour le rectangle
		g.setColor(Color.DARK_GRAY);
		// On le dessine de sorte qu'il occupe toute la surface
		g.fillRect(0, 0, 0, 0);

	}
}