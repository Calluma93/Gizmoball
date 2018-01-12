package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Model;

public class RenderListener implements ActionListener{

	private Model model;
	private View view;
	
	public RenderListener(Model model, View view){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()){
		
			case "Normal":
				view.setRenderMode(0);
				model.draw();
				break;
				
			case "Real Estate":
				view.setRenderMode(1);
				model.draw();
				break;
				
			case "Collidables":
				view.setRenderMode(2);
				model.draw();
				break;
		}
	}

}
