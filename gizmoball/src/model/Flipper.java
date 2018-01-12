package model;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Flipper extends Gizmo implements Collidable{

	//The sublocation of a flipper in its block
	// 0 - bottomLeft
	// 1 - bottomRight
	// 2 - topRight
	// 3 - topLeft
	private int point;
	
	//Points used for drawing the polygon that is the body of the flipper
	private int[] pointsX;
	private int[] pointsY;
	
	//Default 0 max 90 in degrees
	private double angle;
	private double restingAngle;
	
	private boolean rotating;
	private boolean retracting;
	private double rotateSpeed;
	private double retractSpeed;
	private double currentRot;
	
	//The direction the flipper will flip
	private boolean flipClockwise;
	
	public Flipper(int xPos, int yPos, int point, boolean flipClockwise, Color color) {
		super(xPos, yPos, color);
		this.point = point;
		this.flipClockwise = flipClockwise;
		
		setAngle(); 
		
		rotating = false;
		retracting = true;
		rotateSpeed = 8.0;
		retractSpeed = 2.0;
		
		pointsX = new int[4];
		pointsY = new int[4];
		setDrawablePoints();
	}

	@Override
	public List<Circle> getCircles() {
		
		//offset added to circle to indicate location in square
		int offsetx = 0;
		int offsety = 0;
		 
		switch(point){
		case 2:
			offsetx = Global.L / 2;
			break;
		case 0:
			offsety = Global.L / 2;
			break;
		case 1:
			offsetx = Global.L / 2;
			offsety = Global.L / 2;
			break;
		}

		List<Circle> cl = new ArrayList<Circle>();
		cl.add(new Circle(getBaseX() + offsetx, getBaseY() + offsety, Global.L / 4));
		cl.add(new Circle(getOuterX() + offsetx, getOuterY() + offsety, Global.L / 4));
		return cl;
	}

	@Override
	public List<LineSegment> getLines() {

		//offset added to circle to indicate location in square
		int offsetx = 0;
		int offsety = 0;
		 
		switch(point){
		case 2:
			offsetx = Global.L / 2;
			break;
		case 0:
			offsety = Global.L / 2;
			break;
		case 1:
			offsetx = Global.L / 2;
			offsety = Global.L / 2;
			break;
		}
		
		List<LineSegment> ll = new ArrayList<LineSegment>();
		ll.add(new LineSegment(getBaseX() + offsetx + Global.L / 4 * Math.cos(Math.toRadians(angle + 90)), getBaseY() + offsety + Global.L / 4 * Math.sin(Math.toRadians(angle + 90) *-1),
				getOuterX() + offsetx +  Global.L / 4 * Math.cos(Math.toRadians(angle + 90)),
				getOuterY() + offsety + Global.L / 4 * Math.sin(Math.toRadians(angle + 90)) * -1));
		
		ll.add(new LineSegment(getBaseX() + offsetx + Global.L / 4 * Math.cos(Math.toRadians(angle - 90)), getBaseY() + offsety + Global.L / 4 * Math.sin(Math.toRadians(angle - 90) *-1),
				getOuterX() + offsetx +  Global.L / 4 * Math.cos(Math.toRadians(angle - 90)),
				getOuterY() + offsety + Global.L / 4 * Math.sin(Math.toRadians(angle - 90)) * -1));
		return ll;
	}

	//Set Flip
	public void setFlip(){
		rotating = true;
		retracting = false;
	}
	
	//Set Retract
	public void setRetract(){
		rotating = true;
		retracting = true;
	}
	
	//Switch
	public void setSwitch(){
		rotating = true;
		retracting = !retracting;
	}
	
	public void rotate(){
		point = ++point == 4 ? 0 : point;
		setDrawablePoints();
		setAngle();
	}
	
	public void setAngle(){
		
		angle = point * 90;
		if(flipClockwise){
			incAngle(90);
			if(angle  == -90)	angle = 270;
		}
		
		restingAngle = angle;
	}
	
	public void pivot(){
		pivot(1.0);
	}
	
	//RotateRatio is used if a flipper should not rotate fully this tick ie collision
	public void pivot(double rotateRatio){
		
		if(rotating){
			
			if(retracting){
				if(currentRot - retractSpeed <= 0){
					setAngle(restingAngle);
					rotating = false;
					currentRot =0;
				}
				else{
					incAngle(retractSpeed * (flipClockwise? 1 : -1)   * rotateRatio);
					currentRot -= retractSpeed;
				}
			}
			else{
				if(currentRot + rotateSpeed >= 90){
					setAngle(flipClockwise? restingAngle -90: restingAngle + 90);
					rotating = false;
					currentRot = 90;
				}
				else{
					incAngle(rotateSpeed * (flipClockwise? -1 : 1)  * rotateRatio);
					currentRot += rotateSpeed;
				}
			}
			
			setDrawablePoints();
		}
	}
	
	private void incAngle(double rotVel){
		angle += rotVel;
		if(angle < 0) angle += 360;
		if(angle >= 360) angle -= 360;
	}
	
	private void setAngle(double angle){
		this.angle = angle;
		if(angle < 0) angle += 360;
		if(angle >= 360) angle -= 360;
	}
	
	public double getCurrentRotateSpeed() {
		return (rotating? (retracting? retractSpeed * (flipClockwise? -1 : 1) : rotateSpeed) * (flipClockwise? 1 : -1)  : 0);
	}

	//return the x of the center of the Base circle
	private double getBaseX(){
		return getxPos() + Global.L / 4;
	}
	
	//return the y of the center of the Base circle
	private double getBaseY(){
		return getyPos() + Global.L / 4;
	}
	
	//return the x of the center of the Base circle
	private double getOuterX(){
		return getBaseX() + (1.5 * Global.L * Math.cos(Math.toRadians(angle)));
	}
	
	//return the y of the center of the Base circle
	private double getOuterY(){
		return getBaseY() + (-1 * 1.5 * Global.L * Math.sin(Math.toRadians(angle)));
	}
	
	public Circle getBaseCircle(){
		return getCircles().get(0);
	}
	
	public Ellipse2D getDrawableBaseCircle(){
		return getCircles().get(0).toEllipse2D();
	}
	
	public Ellipse2D getDrawableOuterCircle(){
		return getCircles().get(1).toEllipse2D();
	}
	
	public void setDrawablePoints(){
		
		List<LineSegment> lines = getLines();
		Line2D line1 = lines.get(0).toLine2D();
		Line2D line2 = lines.get(1).toLine2D();
		
		pointsX[0] = (int)line1.getX1();
		pointsX[1] = (int)line1.getX2();
		pointsX[2] = (int)line2.getX2();
		pointsX[3] = (int)line2.getX1();
		
		pointsY[0] = (int)line1.getY1();
		pointsY[1] = (int)line1.getY2();
		pointsY[2] = (int)line2.getY2();
		pointsY[3] = (int)line2.getY1();
	}
	
	public int getPoint() { return point; }
	public boolean getOrientation() { return flipClockwise; }
	
	public int[] getDrawablePointsX(){return pointsX;}
	public int[] getDrawablePointsY(){return pointsY;}

	@Override
	public void onCollision(Gizmo other, Vect vel) {
		super.onCollision(other, vel);
	}
}