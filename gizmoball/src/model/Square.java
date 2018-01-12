package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Square extends Gizmo{

	//xPos and yPos should be L-formatted
	public Square(int xPos, int yPos, Color color) {
		super(xPos, yPos, color);
	}

	@Override
	public void onCollision(Gizmo other, Vect vel) {
		super.onCollision(other, vel);
	}
	
	public List<Circle> getCircles() {
		
		Vect topLeft = new Vect(getxPos() + 2, getyPos() + 2);
		Vect topRight = new Vect(getxPos() - 2 + Global.L, getyPos() + 2);
		Vect bottomLeft = new Vect(getxPos() + 2, getyPos() + Global.L -2);
		Vect bottomRight = new Vect(getxPos() - 2 + Global.L, getyPos() - 2 + Global.L);
		
		List<Circle> cl = new ArrayList<Circle>();
		cl.add(new Circle(topLeft, 0));
		cl.add(new Circle(topRight, 0));
		cl.add(new Circle(bottomLeft, 0));
		cl.add(new Circle(bottomRight, 0));
		return cl;
	}

	public List<LineSegment> getLines() {
		
		Vect topLeft = new Vect(getxPos(), getyPos());
		Vect topRight = new Vect(getxPos() + Global.L, getyPos());
		Vect bottomLeft = new Vect(getxPos(), getyPos() + Global.L);
		Vect bottomRight = new Vect(getxPos() + Global.L, getyPos() + Global.L);
		
		List<LineSegment> ll = new ArrayList<LineSegment>();
		ll.add(new LineSegment(topLeft, topRight));				//Top
		ll.add(new LineSegment(bottomLeft, bottomRight));			//Bottom
		ll.add(new LineSegment(topLeft, bottomLeft));				//Left
		ll.add(new LineSegment(topRight, bottomRight));			//Right
		return ll;
	}

}
