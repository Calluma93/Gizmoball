package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Line extends Gizmo{

	//In L
	private int yL;
	private int xL;
	
	public Line(int xPos, int yPos, int xL, int yL, Color color) {
		super(xPos, yPos, color);
		this.xL = xL;
		this.yL = yL;
	}
	
	public int getXL() {
		return xL;
	}
	
	public int getYL() {
		return yL;
	}

	@Override
	public void onCollision(Gizmo other, Vect vel) {
		super.onCollision(other, vel);
	}
	
	public List<Circle> getCircles() {
		List<Circle> cl = new ArrayList<Circle>();
		cl.add(new Circle(getxPos(), getyPos(), 0));
		cl.add(new Circle(getxPos() + xL * Global.L, getyPos() + yL * Global.L, 0));
		return cl;
	}

	public List<LineSegment> getLines() {
		List<LineSegment> ll = new ArrayList<LineSegment>();
		ll.add(new LineSegment(getxPos(), getyPos(), getxPos() + xL * Global.L, getyPos() + yL * Global.L));
		return ll;
	}
}
