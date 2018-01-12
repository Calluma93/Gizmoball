package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import file.Load;
import model.Model;
import view.View;

public class LoadListener implements ActionListener {

	Model m;
	View v;
	
	public LoadListener(Model m, View v) {
		this.m = m;
		this.v = v;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Load":
			System.out.println("Loading board from file...");
			Load l = new Load(m);
			String s = v.FileChooser();
			m.clearAllGizmos();
			l.loadFile(s);
			v.getBoard().addKeyListener(new GizmoKeyListener(v, m));
			v.getBoard().repaint();
			break;
		}
		
	}
}
