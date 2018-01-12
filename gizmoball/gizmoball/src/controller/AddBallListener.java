package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Global;
import model.Ball;
import model.Gizmo;
import model.Model;
import model.Square;
import physics.Vect;
import view.Board;

public class AddBallListener implements MouseListener {

	private Model m;
	
	public AddBallListener(Model m, Board b){
		this.m = m;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent e) { 
		
		int x = e.getX() / Global.L;
		int y = e.getY() / Global.L;
		
		Gizmo g = new Ball(x, y, 7, new Vect(100, 100), Color.RED);
		g.incxPos(Global.L / 2);
		g.incyPos(Global.L / 2);
		
		if(m.getRealEstate().isClearSingle(x, y)){
			m.addGizmo(g);
			System.out.println("Placed Ball at: " + e.getX() / Global.L + "L" + "," + e.getY() /Global.L + "L");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) { }
}
