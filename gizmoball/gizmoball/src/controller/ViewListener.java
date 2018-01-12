package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Model;
import view.Board;
import view.View;

public class ViewListener implements ActionListener {

	private View view;
	private Model m;
	
	public ViewListener(View view, Model m) {
		this.view = view;
		this.m = m;
	}

	public final void actionPerformed(final ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Build":
				if(view.getCurrentMode())
					view.createBuildView();
				break;
			case "Play":
				if(!view.getCurrentMode()) {
					view.createPlayView();
					view.getBoard().addKeyListener(new GizmoKeyListener(view, m));
					view.getBoard().requestFocus();
					removeListeners();
				}
				break;
			case "Flipper":
				break;
			case "Exit":
				System.exit(0);
				break;
			}
	}
	
	private void removeListeners() {
		
		Board b = view.getBoard();
		
		for(MouseListener ml : b.getMouseListeners()) {
			b.removeMouseListener(ml);
		}
		
		for(MouseMotionListener mml : b.getMouseMotionListeners()) {
			b.removeMouseMotionListener(mml);
		}
	}
}