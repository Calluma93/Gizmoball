package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Absorber extends Gizmo{

	private int xL;
	private int yL;
	
	private Ball savedBall;
	
	public Absorber(int xPos, int yPos, int xPos2, int yPos2, Color color) {
		super(xPos, yPos, color);
		this.xL = xPos2 - xPos;
		this.yL = yPos2 - yPos;
	}

	@Override
	public List<Circle> getCircles() {
		List<Circle> cl = new ArrayList<Circle>();
		cl.add(new Circle(getxPos(), getyPos(), 0));
		cl.add(new Circle(getxPos() + xL * Global.L, getyPos(), 0));
		cl.add(new Circle(getxPos(), getyPos() + yL * Global.L, 0));
		cl.add(new Circle(getxPos() + xL * Global.L, getyPos() + yL * Global.L, 0));
		return cl;
	}

	@Override
	public List<LineSegment> getLines() {
		List<LineSegment> ll = new ArrayList<LineSegment>();
		ll.add(new LineSegment(getxPos(), getyPos(), getxPos() + xL * Global.L, getyPos()));
		ll.add(new LineSegment(getxPos(), getyPos(), getxPos(), getyPos()  + yL * Global.L));
		ll.add(new LineSegment(getxPos() + xL * Global.L, getyPos(), getxPos() + xL * Global.L, getyPos()  + yL * Global.L));
		ll.add(new LineSegment(getxPos(), getyPos() + yL * Global.L, getxPos() + xL * Global.L, getyPos()  + yL * Global.L));
		return ll;
	}

	public int getxL() {return xL;}
	public int getyL() {return yL;}
	
	public void setxL(int xL) { this.xL = xL;}
	public void setyL(int yL) { this.yL = yL;}
	
	public boolean releaseBall() {
		
		if(savedBall.isAbsorbed()) {
			savedBall.setVelo(new Vect(0,-850));
			savedBall.setxPos(getxPos() + xL * Global.L - savedBall.getRadius());
			savedBall.setyPos(getyPos());
			savedBall.setColor(Color.BLUE);
			savedBall.setAbsorbed(false);
			savedBall = null;
			return true;
		}
		return false;
	}
	
	public boolean containsBall() {
		if(savedBall != null) 
			return true;
		else 
		    return false;
	}

	public void onCollision(Gizmo other, Vect vel){
		super.onCollision(other, vel);
		
		if(other instanceof Ball){
			savedBall = (Ball) other;
			savedBall.setAbsorbed(true);
		}
	}
	
}
