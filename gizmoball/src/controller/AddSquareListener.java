package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Global;
import model.Gizmo;
import model.Model;
import model.Square;
import view.Board;

public class AddSquareListener implements MouseListener {

	Model m;
	
	public AddSquareListener(Model m, Board b) {
		this.m = m;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		
		int x = e.getX() / Global.L;
		int y = e.getY() / Global.L;
		
		Gizmo g = new Square(x, y, Color.RED);
		
		if(m.getRealEstate().addGizmoSingle(g, x, y)){
			m.addGizmo(g);
			System.out.println("Placed Square at: " + e.getX() / Global.L + "L" + "," + e.getY() /Global.L + "L");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) { }
}
