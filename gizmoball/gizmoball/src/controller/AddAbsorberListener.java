package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import model.Gizmo;
import model.Model;
import model.Square;
import model.Absorber;
import view.Board;

public class AddAbsorberListener implements MouseListener, MouseMotionListener {

	Model m;
	
	private Absorber g;
	
	public AddAbsorberListener(Model m, Board b) {
		this.m = m;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		int x = e.getX() / Global.L;
		int y = e.getY() / Global.L;
		
		g = new Absorber(x, y, x + 1, y + 1, Color.magenta);
		
		if(m.getRealEstate().addGizmoSingle(g, x, y)){
			m.addGizmo(g);
			System.out.println("Placed Absorber at: " + e.getX() / Global.L + "L" + "," + e.getY() /Global.L + "L");
		}	
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		int newX = e.getX() / Global.L;
		int newY = e.getY() / Global.L;
		int orgX = (int)g.getxPos() / Global.L;
		int orgY = (int)g.getyPos() / Global.L;
		
		List<Point> points = new ArrayList<Point>();
		
		//drag right
		if(newX - orgX >= g.getxL()){
			
			for(int i =0; i < g.getxL() + 1; i++){
				for(int j=0; j < g.getyL(); j++)
					points.add(new Point(orgX + i, orgY + j));
			}
			
			if(m.getRealEstate().isClear(points, g)){
				g.setxL(g.getxL() + 1);
				
				m.getRealEstate().removeGizmo(g);
				m.getRealEstate().addGizmo(g, points);
				
				m.draw();
			}
		}
		
		//drag down
		if(newY - orgY >= g.getyL()){
			
			for(int i =0; i < g.getxL(); i++){
				for(int j=0; j < g.getyL() + 1; j++)
					points.add(new Point(orgX + i, orgY + j));
			}
			
			if(m.getRealEstate().isClear(points, g)){
				g.setyL(g.getyL() + 1);
				
				m.getRealEstate().removeGizmo(g);
				m.getRealEstate().addGizmo(g, points);
				
				m.draw();
			}
		}
		
		//no need to test if up/left is clear as we jsuut came that way
		
		//drag left
		if(newX - orgX < g.getxL() - 1  && newX >= orgX){
			
			for(int i =0; i < g.getxL() - 1; i++){
				for(int j=0; j < g.getyL(); j++)
					points.add(new Point(orgX + i, orgY + j));
			}
			
			g.setxL(g.getxL() - 1);
			m.getRealEstate().addGizmo(g, points);
			m.draw();
		}
		
		//drag up
		if(newY - orgY < g.getyL() - 1  && newY >= orgY){
			
			for(int i =0; i < g.getxL(); i++){
				for(int j=0; j < g.getyL() - 1; j++)
					points.add(new Point(orgX + i, orgY + j));
			}
			
			g.setyL(g.getyL() - 1);
			m.getRealEstate().removeGizmo(g);
			
			m.getRealEstate().addGizmo(g, points);
			m.draw();
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) {
		
		System.out.println("released");
	}
	
	
}
