package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Triangle extends Gizmo{

	//indicates the orientation of the triangle
	//Perhaps an enum is better
	// 0 - topLeft
	// 1 - topRight
	// 2 - bottomRight
	// 3 - bottomLeft
	private int corner;
	
	//Points used for drawing the polygon that is the triangle
	private int[] pointsX;
	private int[] pointsY;
	
	//Constructor w/o velocity
	public Triangle(int xPos, int yPos, int corner, Color color) {
		super(xPos, yPos, color);
		this.corner = corner;
		pointsX = new int[3];
		pointsY = new int[3];
		setDrawablePoints();
	}

	public void rotate(){
		System.out.println("rotate");
		corner = ++corner == 4 ? 0 : corner;
		setDrawablePoints();
	}
	
	public void setDrawablePoints(){
		for(int i =0; i< 3; i++){
			pointsX[i] = (int)getCircles().get(i).getCenter().x();
			pointsY[i] = (int)getCircles().get(i).getCenter().y();
		}
	}

	
	@Override
	public void onCollision(Gizmo other, Vect vel) {
		super.onCollision(other, vel);
	}
	
	public List<Circle> getCircles() {
		
		Vect topLeft = new Vect(getxPos(), getyPos());
		Vect topRight = new Vect(getxPos() + Global.L, getyPos());
		Vect bottomLeft = new Vect(getxPos(), getyPos() + Global.L);
		Vect bottomRight = new Vect(getxPos() + Global.L, getyPos() + Global.L);
		
		List<Circle> cl = new ArrayList<Circle>();
		if(corner != 2)	cl.add(new Circle(topLeft, 0));
		if(corner != 3)	cl.add(new Circle(topRight, 0));
		if(corner != 1)	cl.add(new Circle(bottomLeft, 0));
		if(corner != 0)	cl.add(new Circle(bottomRight, 0));
		return cl;
	}

	public List<LineSegment> getLines() {
		
		Vect topLeft = new Vect(getxPos(), getyPos());
		Vect topRight = new Vect(getxPos() + Global.L, getyPos());
		Vect bottomLeft = new Vect(getxPos(), getyPos() + Global.L);
		Vect bottomRight = new Vect(getxPos() + Global.L, getyPos() + Global.L);
		
		List<LineSegment> ll = new ArrayList<LineSegment>();
		if(corner == 0 || corner == 1)	ll.add(new LineSegment(topLeft, topRight));				
		if(corner == 2 || corner == 3)	ll.add(new LineSegment(bottomLeft, bottomRight));		
		if(corner == 0 || corner == 3)	ll.add(new LineSegment(topLeft, bottomLeft));			
		if(corner == 1 || corner == 2)	ll.add(new LineSegment(topRight, bottomRight));			
		
		if(corner == 0 || corner == 2) 	ll.add(new LineSegment(bottomLeft, topRight));
		else							ll.add(new LineSegment(topLeft, bottomRight));
		return ll;
	}
	
	public int[] getDrawablePointsX(){return pointsX;}
	public int[] getDrawablePointsY(){return pointsY;}
	public int getCorner() { return corner; }
}
