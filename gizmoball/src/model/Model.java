package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import main.Global;
import physics.Circle;
import physics.Geometry;
import physics.Geometry.VectPair;
import physics.LineSegment;
import physics.Vect;
import trigger.TriggerKeyPressed;
import trigger.TriggerKeyReleased;

/**
 * Model represents a number of different gizmos together. In addition,
 * it implements the collisions which also takes into account friction
 * and gravity. It also extends the Observable class
 *
 */

public class Model extends Observable {

	//All gizmo lists
	private List<Gizmo> gizmos;
	private List<MovingGizmo> movingGizmos;
	private List<Line> lines;
	private List<Ball> balls;
	private List<Square> squares;
	private List<Triangle> triangles;
	private List<Flipper> flippers;
	private List<Absorber> absorbers;
	private List<CircleG> circles;
	
	//Gravity/ Friction Variables
	private double time = 1;
	private double MU = 5;
	private double MU2 = 0.001;
	private double grav = 25;
	
	//KeyTriggers
	private List<TriggerKeyPressed> triggerPressed;
	private List<TriggerKeyReleased> triggerReleased;

	//Real Estate holds location data for Stationary Gizmos
	private RealEstate realEstate;
	
	
	/**
	 * The constructor for the Model class
	 * 
	 * @requires: every type of gizmo, triggers, realestate
	 * @effects: adds/removes/moves gizmos
	 * 			 checks for collisions while taking account friction and gravity
	 * 			 trigger effects
	 * @modifies: this
	 *
	 */

	public Model() {

		gizmos 			= new ArrayList<Gizmo>();	
		movingGizmos 	= new ArrayList<MovingGizmo>();
		balls 			= new ArrayList<Ball>();
		lines 			= new ArrayList<Line>();
		squares 		= new ArrayList<Square>();
		triangles 		= new ArrayList<Triangle>();
		flippers 		= new ArrayList<Flipper>();
		absorbers 		= new ArrayList<Absorber>();
		circles			= new ArrayList<CircleG>();
		
		triggerPressed = new ArrayList<TriggerKeyPressed>();
		triggerReleased = new ArrayList<TriggerKeyReleased>();
		
		realEstate = new RealEstate();
		
		setStartingObjects();	//REPLACE with load
	}
	
	public void setStartingObjects(){
		
		
		lines.add(new Line(0, 0, 20, 0, Color.black));
		lines.add(new Line(0, 0, 0, 20, Color.black));
		lines.add(new Line(20, 0, 0, 20, Color.black));
		lines.add(new Line(0, 20, 20, 0, Color.black));
		
		gizmos.addAll(balls);
		gizmos.addAll(lines);
		gizmos.addAll(squares);
		gizmos.addAll(triangles);
		gizmos.addAll(flippers);
		gizmos.addAll(absorbers);
		gizmos.addAll(circles);
		
		movingGizmos.addAll(balls);
	}

	public void update(){
		moveGizmos();
		draw();
	}
	
	public void draw(){
		this.setChanged();
		this.notifyObservers();
	}

	
	/**
	 * Loops through all moving gizmos and checks collisions
	 * Special collision methods are used for moving gizmos and flippers
	 */
	private void moveGizmos(){
		boolean collided;
		for(MovingGizmo mg : movingGizmos){
			
			collided = false;
			
			//If the gizmo is is on the floor check if it still is
			if(mg.isTouchingfloor()){
				
				if(1 + mg.getyPos() / Global.L < Global.gridSize){
					
					Gizmo below = realEstate.getGizmo((int)mg.getxPos() / Global.L, 1 + (int)mg.getyPos() / Global.L);
					if(below == null || !(below instanceof Square))		mg.setTouchingfloor(false);
				}
			}
			
			for(Gizmo other : gizmos){
				
				if(other.equals(mg) || other instanceof Flipper || other instanceof MovingGizmo)	continue;
				collided |= checkCollision(mg, other);
			}	
			
			for(MovingGizmo other : movingGizmos){
				
				if(other.equals(mg)) continue;
				collided |= checkMovingCollision(mg, other);
			}
			
			for(Flipper other : flippers){
				if(!checkFlipperCollision(mg, other))	other.pivot();		//If checkFlipperCollision return false rotate flipper
			}
			
			//If there was no collisions increase position based on FPS
			if(!collided){ 

				mg.setRecentCollision(null);
				
				if(!mg.isTouchingfloor())	applyGravity(mg, Global.MOVE_TIME);
				applyFriction(mg, Global.MOVE_TIME);
				
				mg.incxPos(mg.getVelo().x() * Global.MOVE_TIME);
				mg.incyPos(mg.getVelo().y() * Global.MOVE_TIME);
			} 
			
		}	
	}
	
	/**
	//Takes a moving gizmo and and another gizmo and checks if they will collide
	//If they collide mg is moved to the collision point and its velocity is updated
	//Returns true if there was a collision and false otherwise
	//other is not a MovingGizmo or a Flipper
	 * 
	 * @param mg
	 * @param other
	 * @return Return true if there is a collision, otherwise False
	 */
	public boolean checkCollision(MovingGizmo mg, Gizmo other){
		
		boolean collided = false;
		double time;
		
		circleLoop:
		for(Circle mgCir: mg.getCircles()){
			
			//Check all the lines of g 
			for(LineSegment otherLine : other.getLines()){
				
				time = Geometry.timeUntilWallCollision( otherLine, mgCir, mg.getVelo());
				
				if (time < Global.MOVE_TIME) {
					
					collided = true;
					
					Vect newVect = Geometry.reflectWall(otherLine, mg.getVelo(), 0.8);
					
					if(grav != 0 && other.getyPos() > mg.getyPos()){
						if(newVect.y() > -20){
							mg.setTouchingfloor(true);
							newVect = new Vect(newVect.x(), 0);
						}
					}
					
					mg.incxPos(mg.getVelo().x() * time);
					mg.incyPos(mg.getVelo().y() * time);
					
					mg.onCollision(other ,newVect);
					other.onCollision(mg, null);	//G is static so its new velocity is null
					
					break circleLoop;
				}
			}
				
			//Check all the circles of g iff no line collisions
			for(Circle otherCir: other.getCircles()){
	
				time = Geometry.timeUntilCircleCollision(otherCir, mgCir, mg.getVelo());
				
				if (time < Global.MOVE_TIME) {	

					collided = true;
					
					Vect newVect = Geometry.reflectCircle(otherCir.getCenter(), mgCir.getCenter(), mg.getVelo(), 0.8);

					if(grav != 0 && other.getyPos() > mg.getyPos())
						if(newVect.y() > -20){
							mg.setTouchingfloor(true);
							newVect = new Vect(newVect.x(), 0);
						}
					
					
					mg.incxPos(mg.getVelo().x() * time);
					mg.incyPos(mg.getVelo().y() * time);
					
					mg.onCollision(other ,newVect);
					other.onCollision(mg, null);	//G is static so its new velocity is null
					
					break circleLoop;
				}
			}
		}	
		
		return collided;
	}
	
	/**
	//Takes a moving gizmo and and another moving gizmo and checks if they will collide
	//If they collide mg is moved to the collision point and the velocity of mg and other is updated
	//Returns true if there was a collision and false otherwise
	 * 
	 * @param mg
	 * @param other
	 * @return Return true if there is a collision, otherwise False
	 */
	public boolean checkMovingCollision(MovingGizmo mg, MovingGizmo other){
		
		boolean collided = false;
		double time;
		
		circleLoop:
		for(Circle mgCir: mg.getCircles()){
			
			for(Circle otherCir: other.getCircles()){
				
				time = Geometry.timeUntilCircleCollision(otherCir, mgCir, mg.getVelo());
				if (time < Global.MOVE_TIME){

					collided = true;
					

					mg.incxPos(mg.getVelo().x() * time);
					mg.incyPos(mg.getVelo().y() * time);
					
					VectPair vectP = Geometry.reflectBalls(mgCir.getCenter(), mgCir.getRadius(), mg.getVelo(), otherCir.getCenter(), otherCir.getRadius(), other.getVelo());
					mg.onCollision(other , vectP.v1);
					other.onCollision(mg, vectP.v2);
					
					break circleLoop;
				}
			}
		}
		
		return collided;
	}
	
	/**
	//Checks if MovingGizmo mg will be hit by any of the flippers this tick
	//If so flippers will move to the collision point and a new velocity is given to mg
	//Returns true if there was a collision and false otherwise
	 * 
	 * @param mg
	 * @param other
	 * @return Return true if there is a collision, otherwise False
	 */
	public boolean checkFlipperCollision(MovingGizmo mg, Flipper other){
		
		boolean collided = false;
		double time;

		circleLoop:
		for(Circle mgCir: mg.getCircles()){
			
			for(LineSegment flipLine : other.getLines()){
				
				time = Geometry.timeUntilRotatingWallCollision( flipLine, flipLine.p1(),  Math.toRadians(other.getCurrentRotateSpeed()) * Global.FPS, mgCir, mg.getVelo());
				if (time < Global.MOVE_TIME) {
					
					collided = true;
					
					other.pivot(time / Global.MOVE_TIME);
					

					mg.incxPos(mg.getVelo().x() * time);
					mg.incyPos(mg.getVelo().y() * time);
					
					mg.onCollision(other, Geometry.reflectRotatingWall(flipLine, flipLine.p1(), Math.toRadians(other.getCurrentRotateSpeed())  * Global.FPS, mgCir, mg.getVelo(), 0.95));
					other.onCollision(mg, null);
					break circleLoop;
				}
			}	
			
			for(Circle flipCir : other.getCircles()){
				
				time = Geometry.timeUntilRotatingCircleCollision( flipCir, other.getBaseCircle().getCenter(),  Math.toRadians(other.getCurrentRotateSpeed())  * Global.FPS, mgCir, mg.getVelo());
				if (time < Global.MOVE_TIME) {
					
					collided = true;
					
					other.pivot(time / Global.MOVE_TIME);

					mg.incxPos(mg.getVelo().x() * time);
					mg.incyPos(mg.getVelo().y() * time);
					
					mg.onCollision(other, Geometry.reflectRotatingCircle(flipCir, other.getBaseCircle().getCenter(), Math.toRadians(other.getCurrentRotateSpeed())  * Global.FPS, mgCir, mg.getVelo(), 0.95));
					other.onCollision(mg, null);
					break circleLoop;
				}
			}		
			
		}
		
		return collided;
	}
	
	/**
	 * 
	 * Apply Gravity to passed gizmo
	 * 
	 * @param mg
	 * @param time
	 */
	
	public void applyGravity(MovingGizmo mg, double time){
		
		double currentX = mg.getVelo().x();
		double currentY = mg.getVelo().y();
		
		double gravity = (-grav * Global.L) * time;
					
		double finalY = (currentY - gravity);
		
		Vect finalV = new Vect(currentX, finalY);
		
		mg.setVelo(finalV);
	}
	
	/**
	 * 
	 * Apply Friction to passed gizmo
	 * 
	 * @param mg
	 * @param time
	 */
	public void applyFriction(MovingGizmo mg, double time){
		
		double currentX = mg.getVelo().x();
		double currentY = mg.getVelo().y();
		
		double friction = (1 - (MU / 1000) * time - MU2 * currentX * time);
		
		double finalX = (currentX * friction);			
		double finalY = (currentY * friction);
		
		Vect finalV = new Vect(finalX, finalY);
		
		mg.setVelo(finalV);
	}
	
	public void setMU(double newMU){
		MU = newMU;
	}
	
	public void setMU2(double newMU2){
		MU2 = newMU2;
	}
	
	public void setGravity(double newGrav){
		grav = newGrav;
	}
	
	public double getMU() {
		return MU;
	}

	public double getMU2() {
		return MU2;
	}
	
	public double getGravity() {
		return grav;
	}
	
	public void test(){
		removeGizmo(balls.get(0));
	}
	
	public List<Flipper> getFlippers(){return this.flippers;}
	public List<Ball> getBalls() {return this.balls;}
	public List<Line> getLines() {return this.lines;}
	public List<Square> getSquares() {return this.squares;}
	public List<Triangle> getTriangles() {return this.triangles;}
	public List<Absorber> getAbsorbers() {return this.absorbers;}
	public List<CircleG> getCircles() {return this.circles;}
	public List<Gizmo> getAllGizmos() {return this.gizmos;} 
	
	public List<TriggerKeyPressed> getTriggerPressed() {return triggerPressed;}
	public List<TriggerKeyReleased> getTriggerReleased() {return triggerReleased;}

	public RealEstate getRealEstate() {return realEstate;}
	
	public void addTriggerPressed(TriggerKeyPressed tkp) { triggerPressed.add(tkp);} 
	public void addTriggerReleased(TriggerKeyReleased tkr) { triggerReleased.add(tkr);} 
	
	/**
	 * 
	 * Adds Gizmo g to the board and updates all lists to add it
	 * 
	 * @param g
	 */
	public void addGizmo(Gizmo g) {
		if(g instanceof MovingGizmo)		movingGizmos.add((MovingGizmo)g);
		
		if(g instanceof Flipper)			flippers.add((Flipper)g);
		else if(g instanceof Ball)			balls.add((Ball)g); 
		else if(g instanceof Line)			lines.add((Line)g);
		else if(g instanceof Square)		squares.add((Square)g);
		else if(g instanceof Triangle)		triangles.add((Triangle)g);
		else if(g instanceof Absorber)		absorbers.add((Absorber)g);
		else if(g instanceof CircleG)		circles.add((CircleG)g);
		
		gizmos.add(g);
		
		draw();
	}
	
	/**
	 * Deletes specific gizmo from the board
	 * 
	 * @requires: gizmos
	 * @effects: Deletes passed gizmo
	 * @modfies: this
	 * 
	 */
	public void removeGizmo(Gizmo badEgg){
		
		gizmos.remove(badEgg);
		if(badEgg instanceof MovingGizmo)		movingGizmos.remove(badEgg);
		
		if(badEgg instanceof Flipper)			flippers.remove(badEgg);
		else if(badEgg instanceof Ball)			balls.remove(badEgg);
		else if(badEgg instanceof Line)			lines.remove(badEgg);
		else if(badEgg instanceof Square)		squares.remove(badEgg);
		else if(badEgg instanceof Triangle)		triangles.remove(badEgg);
		else if(badEgg instanceof Absorber)		absorbers.remove(badEgg);
		else if(badEgg instanceof CircleG)		circles.remove(badEgg);
		
		draw();
	}
	
	/**
	 * Deletes all the gizmos on the board
	 * 
	 * @requires: gizmos
	 * @effects: Loops through every gizmo and removes them from the list,
	 * 			 deleting all of it off the board
	 * @modfies: this
	 * 
	 */
	public void clearAllGizmos(){
		int temp = gizmos.size();
		int count = 0;
		int removeCount = 0;
		while(count < temp){
			if(gizmos.get(removeCount) instanceof Line){
				removeCount++;
			}else{
				removeGizmo(gizmos.get(removeCount));
			}
			count++;
		}
		
		realEstate.removeAll();
	}
	
	/**
	 * 
	 * Returns any gizmo at location x, y
	 * 
	 * @param x
	 * @param y
	 * @return Gizmo at X, Y
	 */
	public Gizmo getMovingGizmo(int x, int y) {
		for(Gizmo g : movingGizmos) {
			// cast as integers for L 
			if(((int)g.getxPos() / Global.L) == x && ((int)g.getyPos() / Global.L) == y) {
				return g;
			}
		}
		return null;
	}
}
