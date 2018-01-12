package model;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import physics.Vect;
import trigger.TriggerCollision;
import trigger.TriggerKeyPressed;
import trigger.TriggerKeyReleased;

public abstract class Gizmo implements Collidable{
	
	//Postion in pixels
	private double xPos;
	private double yPos;
	
	//Basic render colour
	private Color color;
	
	//List of gizmo's triggers
	private List<TriggerCollision> collisionTriggers;
	
	public Gizmo(int xPos, int yPos, Color color) {
		this.xPos = xPos * Global.L;
		this.yPos = yPos * Global.L;
		this.color = color;

		collisionTriggers = new ArrayList<TriggerCollision>();
	}
	
	public void addCollisionTrigger(TriggerCollision tc){
		collisionTriggers.add(tc);
	}
	
	public List<TriggerCollision> getTriggerCollisions() {
		return collisionTriggers;
	}

	//Standard collision
	public void onCollision(Gizmo other, Vect vel){
		for(TriggerCollision tc: collisionTriggers)
			tc.checkAndDo(other);
	}

	public double getxPos() {return xPos;}
	public void setxPos(double xPos) {this.xPos = xPos;}
	public void incxPos(double i) {this.xPos += i;}
	
	public double getyPos() {return yPos;}
	public void setyPos(double yPos) {this.yPos = yPos;}
	public void incyPos(double i) {this.yPos += i;}

	public Color getColor() {return color;}
	public void setColor(Color color) {	this.color = color;}
}
