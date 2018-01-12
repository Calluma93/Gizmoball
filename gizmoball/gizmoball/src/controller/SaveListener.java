package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import file.Save;
import model.Model;

public class SaveListener implements ActionListener {

	Model m;
	
	public SaveListener(Model m) {
		this.m = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Save":
			// save the game!
			System.out.println("Saving current board ...");
			Save s = new Save(m);
			s.saveCurrentGame();
			break;
		}
		
	}
	
	
	
	
}
