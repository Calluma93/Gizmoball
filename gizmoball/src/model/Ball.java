package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Ball extends MovingGizmo{

	private double radius;
	private boolean isAbsorbed;
	
	public Ball(int xPos, int yPos, double radius, Vect velo, Color color) {
		super(xPos, yPos, velo, color);
		
		this.radius = radius;
	}
	
	@Override
	public void onCollision(Gizmo other, Vect vel) {
		super.onCollision(other, vel);
	}

	public List<Circle> getCircles() {
		List<Circle> cl = new ArrayList<Circle>();
		cl.add(new Circle(getxPos(), getyPos(), radius));
		return cl;
	}

	public List<LineSegment> getLines() {
		return Collections.emptyList();
	}
	
	public void setAbsorbed(boolean isAbsorbed) {
		
		this.isAbsorbed = isAbsorbed;
		if(isAbsorbed){
			setxPos(-50);
			setyPos(-50);
			setVelo(new Vect(0, 0));
		}
	}
	public boolean isAbsorbed() {return isAbsorbed;}
	
	public void setRadius(double radius) {this.radius = radius;}
	public double getRadius() {return radius;}

}
