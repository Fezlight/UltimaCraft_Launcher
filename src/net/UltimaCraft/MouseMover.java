package net.UltimaCraft;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class MouseMover extends MouseAdapter {
	private Point click;
	private JFrame window;

	public MouseMover(JFrame window) {
		this.window = window;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (click != null) {
			Point draggedPoint = MouseInfo.getPointerInfo().getLocation();
			window.setLocation(new Point((int) draggedPoint.getX()
					- (int) click.getX(), (int) draggedPoint.getY()
					- (int) click.getY()));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		click = e.getPoint();
	}

}
