package net.UltimaCraft.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class optionsPane extends JPanel {
	private static final long serialVersionUID = 1L;
	public static JButton options;

	public optionsPane() {
		this.setOpaque(false);
		options = new JButton("    Options    ");
		options.setBorderPainted(false);
		options.setContentAreaFilled(true);
		options.setFocusPainted(false);
		options.setOpaque(false);

		this.setLayout(new BorderLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = 1;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.gridwidth = 2;
		// this.add(options, BorderLayout.LINE_END);
		this.setBorder(new EmptyBorder(20, 25, 20, 50));
	}
}
