package net.UltimaCraft.ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.UltimaCraft.LauncherV2;
import net.UltimaCraft.ProfileInfo;
import net.UltimaCraft.Util;
import net.UltimaCraft.bar;
import net.UltimaCraft.cryption.Data;

public class Options extends JDialog implements ActionListener {
	private static final long serialVersionUID = 7992003769732945865L;
	private final ProfileInfo profileInfo;
	private final JButton saveButton;
	private final JButton cancelButton;

	private JButton texturepacks;
	private JButton test;
	private JButton folder;
	private JButton reset;

	private JLabel online;
	private JLabel offline;

	private JButton avatar;

	private Options options;

	File sessionFile = new File(Util.getWorkingDirectory() + File.separator
			+ "session");

	public Options(final LauncherV2 launcher) {
		super(launcher);
		this.avatar = nicknamePane.avatar;
		this.setTitle("Options");
		this.setModal(true);
		this.setSize(400, 250);
		this.setLocationRelativeTo(avatar);
		this.setLocation(
				(int) this.getLocation().getX()
						+ ((int) this.getWidth() - (int) avatar.getWidth()) / 2
						+ 9,
				(int) this.getLocation().getY()
						- ((int) this.getHeight() + (int) avatar.getHeight())
						/ 2);
		this.saveButton = new JButton("          Ok          ");
		this.cancelButton = new JButton("Annuler");
		this.profileInfo = new ProfileInfo();
		this.test = profileInfo.test;
		this.texturepacks = profileInfo.texturepacks;
		this.online = profileInfo.online;
		this.offline = profileInfo.offline;
		this.folder = profileInfo.folder;
		this.reset = profileInfo.reset;
		this.reset.addActionListener(this);
		this.test.addActionListener(this);
		this.folder.addActionListener(this);
		this.texturepacks.addActionListener(this);
		this.saveButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.setLayout(new BorderLayout(0, 5));
		this.createInterface();
		this.setVisible(true);
		this.setResizable(false);
	}

	protected void createInterface() {
		final JPanel standardPanels = new JPanel(true);
		standardPanels.setLayout(new BoxLayout(standardPanels, 1));
		standardPanels.add(this.profileInfo);
		this.add(standardPanels, "Center");
		final JPanel buttonPannel = new JPanel();
		buttonPannel.setLayout(new BoxLayout(buttonPannel, 0));
		buttonPannel.add(this.cancelButton);
		buttonPannel.add(Box.createGlue());
		buttonPannel.add(Box.createHorizontalStrut(5));
		buttonPannel.add(this.saveButton);
		this.add(buttonPannel, "South");
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.cancelButton)
			this.dispose();
		else if (arg0.getSource() == this.saveButton) {
			String args1 = ProfileInfo.ram.getSelectedItem().toString();
			String memory = "-Xmx" + args1;
			Data data = Data.getInstance();
			data.setMemory(memory);
			data.saveData();
			this.dispose();
		} else if (arg0.getSource() == this.test) {
			test.setVisible(false);
			SocketAddress sockaddr = new InetSocketAddress("5.196.97.102",
					25565);
			Socket socket = new Socket();
			try {
				socket.connect(sockaddr, 3000);
				online.setVisible(true);
			} catch (SocketTimeoutException stex) {
				stex.printStackTrace();
				online.setVisible(false);
				offline.setVisible(true);
			} catch (ConnectException ce) {
				ce.printStackTrace();
				online.setVisible(false);
				offline.setVisible(true);
			} catch (IOException iOException) {
				iOException.printStackTrace();
				online.setVisible(false);
				offline.setVisible(true);
			} finally {
				try {
					socket.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		} else if (arg0.getSource() == this.texturepacks)
			new bar(options, profileInfo);
		else if (arg0.getSource() == this.folder) {
			try {
				Desktop.getDesktop().open(Util.getWorkingDirectory());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (arg0.getSource() == this.reset) {
			Data data = Data.getInstance();
			data.resetMemory();
			data.saveData();
			this.dispose();
		}

	}

}
