package model;

import java.awt.Color;

import physics.Vect;
import trigger.TriggerCollision;

public abstract class MovingGizmo extends Gizmo{

	private Vect velo;
	private boolean touchingfloor;
	private Gizmo recentCollision;
	
	public MovingGizmo(int xPos, int yPos, Vect velo, Color color) {
		super(xPos, yPos, color);
		this.velo = velo;
		this.touchingfloor = false;
		this.recentCollision = null;
	}

	public Vect getVelo() {return velo;}
	public void setVelo(Vect velo) {this.velo = velo;}	


	@Override
	public void onCollision(Gizmo other, Vect vel){
		super.onCollision(other, vel);
		
		double velX = vel.x();
		double velY = vel.y();
		
		setVelo(new Vect(velX, velY));
		
		//Possibly stuck
		if(recentCollision == other){
		
			if(other.getxPos() < getxPos()) incxPos(1);
			else							incxPos(-1);
			
			if(other.getyPos() < getyPos()) incyPos(1);
			else							incyPos(-1);
			
		}else{
			recentCollision = other;
		}
	}

	public boolean isTouchingfloor() {return touchingfloor;}
	public void setTouchingfloor(boolean touchingfloor) {this.touchingfloor = touchingfloor;}

	public void setRecentCollision(Gizmo recentCollision) {this.recentCollision = recentCollision;}
	
	
}