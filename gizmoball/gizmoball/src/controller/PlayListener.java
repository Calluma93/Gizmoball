package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.Global;
import model.Model;
import view.IView;
import view.PlayView;
import view.View;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class PlayListener implements ActionListener {

	private Model model;
	private Timer timer;
	private View view;

	public PlayListener(Model model, View view) {
		this.model = model;
		this.view = view;
		timer = new Timer(Global.WAIT_TIME, this);
	}

	public final void actionPerformed(final ActionEvent e) {

		if (e.getSource() == timer) {
			model.update();
		} else
			switch (e.getActionCommand()) {
			case "Start":
				timer.start();
				view.getBoard().requestFocus();
				break;
			case "Pause":
				timer.stop();
				break;
			case "Tick":
				model.update();
				break;
			case "Build":
				timer.stop();
				break;
			}
	}
}
