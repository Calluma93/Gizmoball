package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import model.Flipper;
import model.Gizmo;
import model.Model;
import view.Board;

public class AddFlipperListener implements MouseListener  {
	
	Model m;
	String orientation;
	
	public AddFlipperListener(Model m, Board b, String o) {
		this.m = m;
		this.orientation = o;
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
		
		Gizmo g;
		List<Point> points = new ArrayList<Point>();
		
		if(orientation.equals("left")){
			g = new Flipper(x, y, 3, false, Color.red);
			points.add(new Point(x, y));
			points.add(new Point(x + 1, y));
			points.add(new Point(x, y + 1));
			points.add(new Point(x + 1, y + 1));
		}
		else{
			g = new Flipper(x, y, 2, true, Color.red);
			points.add(new Point(x, y));
			points.add(new Point(x - 1, y));
			points.add(new Point(x, y + 1));
			points.add(new Point(x - 1, y + 1));
		}
		
		System.out.println(points);
		
		if(m.getRealEstate().addGizmo(g, points)){
			m.addGizmo(g);
			System.out.println("Placed Flipper at: " + e.getX() / Global.L + "L" + "," + e.getY() /Global.L + "L");
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) { }
}
