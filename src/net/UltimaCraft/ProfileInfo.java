package net.UltimaCraft;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProfileInfo extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton texturepacks = new JButton("Télécharger");
	public JButton test = new JButton("Test");
	public JButton folder = new JButton("Open");
	public JButton reset = new JButton("Reset");
	public JLabel online = new JLabel("Online");
	public JLabel offline = new JLabel("Offline");
	public JLabel finish = new JLabel("Terminé");
	static String[] ramList = { "2G", "3G", "4G", "5G" };
	public static JComboBox ram = new JComboBox(ramList);

	static GridBagConstraints gridBagConstraints2 = null;
	static GridBagConstraints gridBagConstraints3 = null;
	static GridBagConstraints gridBagConstraints4 = null;
	static GridBagConstraints gridBagConstraints5 = null;

	File session = new File(Util.getWorkingDirectory() + File.separator
			+ "session");

	private static ProfileInfo instance;

	public ProfileInfo() {
		super();
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder("Options"));
		this.createInterface();
	}

	protected void createInterface() {
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.anchor = 17;
		constraints.gridy = 0;
		final GridBagConstraints gridBagConstraints = constraints;
		++gridBagConstraints.gridy;
		texturepacks.setVisible(true);
		this.add(new JLabel("Ressource Packs:"), constraints);
		constraints.fill = 2;
		constraints.weightx = 1.0;
		this.add(this.texturepacks, constraints);
		this.add(this.finish, constraints);
		constraints.weightx = 0.0;
		constraints.fill = 0;
		gridBagConstraints2 = constraints;
		++gridBagConstraints2.gridy;
		this.add(new JLabel("Etat Du Serveur:"), constraints);
		constraints.fill = 2;
		constraints.weightx = 1.0;
		this.add(test, constraints);
		this.add(online, constraints);
		this.add(offline, constraints);
		test.setVisible(true);
		online.setVisible(false);
		offline.setVisible(false);
		finish.setVisible(false);
		constraints.weightx = 0.0;
		constraints.fill = 0;
		gridBagConstraints3 = constraints;
		++gridBagConstraints3.gridy;
		this.add(new JLabel("Dossier:"), constraints);
		constraints.fill = 2;
		constraints.weightx = 1.0;
		this.add(folder, constraints);
		constraints.weightx = 0.0;
		constraints.fill = 0;
		gridBagConstraints4 = constraints;
		++gridBagConstraints4.gridy;
		this.add(new JLabel("Ram:"), constraints);
		constraints.fill = 2;
		constraints.weightx = 1.0;
		final JPanel ramPanel = new JPanel();
		ramPanel.setLayout(new BoxLayout(ramPanel, 0));
		ramPanel.add(ram, constraints);
		ramPanel.add(Box.createHorizontalStrut(5));
		ramPanel.add(reset, constraints);
		this.add(ramPanel, constraints);
		constraints.weightx = 0.0;
		constraints.fill = 0;
		gridBagConstraints4 = constraints;
		++gridBagConstraints4.gridy;
		offline.setForeground(new Color(255, 0, 0));
		online.setForeground(new Color(1, 255, 43));
	}

	public static ProfileInfo getInstance() {
		return instance;
	}
}
