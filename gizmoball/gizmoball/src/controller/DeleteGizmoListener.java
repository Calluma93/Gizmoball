package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Global;
import model.Gizmo;
import model.Model;
import view.Board;

public class DeleteGizmoListener implements MouseListener {

	Model m;
	Board b;
	
	public DeleteGizmoListener(Model m, Board b) {
		this.m = m;
		this.b = b;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX() / Global.L;
		int mouseY = e.getY() / Global.L;
		
		Gizmo g = m.getRealEstate().getGizmoAt(mouseX, mouseY);
		if(g == null){
			
			g = m.getMovingGizmo(mouseX, mouseY);
		}
		
		if(g != null) {
			m.getRealEstate().removeGizmo(g);
			m.removeGizmo(g);
		} else {
			System.out.println("No Gizmo at location: " + mouseX + "L," + mouseY + "L");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }
}
