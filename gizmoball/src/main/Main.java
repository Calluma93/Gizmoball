package main;

import javax.swing.UIManager;

import model.Model;
import view.View;


public class Main {

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}

		Model model = new Model();
		
		View tv = new View(model);
		tv.setVisible(true);
		tv.createPlayView();
	}
}
