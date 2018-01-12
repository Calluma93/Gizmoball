package file;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Absorber;
import model.Ball;
import model.CircleG;
import model.Flipper;
import model.Gizmo;
import model.Model;
import model.Square;
import model.Triangle;
import physics.Vect;
import trigger.TriggerCollision;
import trigger.TriggerKeyPressed;
import trigger.TriggerKeyReleased;

public class Load {

	private Map<String, Gizmo> aliases = new HashMap<>();
	private List<Gizmo> gizmos = new ArrayList<>();
	private Gizmo g;
	private Model m;
	private Ball b;
	private List<TriggerCollision> triggerCollisions;
	private String[] line = { };
	
	public Load(Model m) {
		this.m = m;
		this.triggerCollisions = new ArrayList<TriggerCollision>();
	}
	
	public void loadFile(String fileLocation) { 
		BufferedReader inputFile;
		int lineNumber = 0;
		String fileName = fileLocation;
	
		try {
			inputFile = new BufferedReader(new FileReader(fileName));
			String currentLine = inputFile.readLine();
		
			while(currentLine != null) {
				line = currentLine.split("[ ]"); // seperated by space
				lineNumber++;
				
				if(!checkOpCode(line)) {
					System.out.println("Error parsing line: " + lineNumber);
				}
				
				currentLine = inputFile.readLine();
			}
		
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
		}
		
		for(TriggerCollision tc: triggerCollisions)
			b.addCollisionTrigger(tc);
			
		for(Gizmo g: gizmos) 
			m.addGizmo(g);
		
	}
	
	/* Due to our collision system we have to make sure we have access to the ball first
	 * before we start assigning collisions to other gizmos
	 */
	public void getballs(String fileLocation) throws IOException {
		BufferedReader inputFile;
		String fileName = fileLocation;
		
		inputFile = new BufferedReader(new FileReader(fileName));
		String currentLine = inputFile.readLine();
		
		while(currentLine != null) {
			line = currentLine.split("[ ]");
			if(line[0].equals("Ball")) { 
				checkOpCode(line);
			}
			currentLine = inputFile.readLine();
		}
		inputFile.close();
	}
	
	private boolean hasAlias(String[] line) {
		return Character.isLetter(line[1].charAt(0));
	}
	
	private boolean checkOpCode(String[] line) {

		//used for adding absorber/ flipper
		List<Point> points = new ArrayList<Point>();
		
		switch(line[0]) {
			case "OuterWalls": // reserved identifier so check for this first.
				break;
			case "Triangle":
				g = new Triangle(Integer.parseInt(line[2]), Integer.parseInt(line[3]), 0, Color.BLUE);
				
				if(hasAlias(line)) {
					aliases.put(line[1].toString(), g);		
				} 
				System.out.println("Creating a triangle with x: " + g.getxPos() + " y: " + g.getyPos());
				gizmos.add(g);
				m.getRealEstate().addGizmoSingle(g, Integer.parseInt(line[2]), Integer.parseInt(line[3]));
				break;
			case "Square":
				g = new Square(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Color.RED);
				
				if(hasAlias(line)) {
					aliases.put(line[1], g);
				}
				System.out.println("Creating a square with x: " + g.getxPos() / 25 + " y: " + g.getyPos() / 25);
				gizmos.add(g);
				m.getRealEstate().addGizmoSingle(g, Integer.parseInt(line[2]), Integer.parseInt(line[3]));
				break;
			case "Circle":
				g = new CircleG(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Color.GREEN);
				
				if(hasAlias(line)) {
					aliases.put(line[1],g);
				}
				System.out.println("Creating a circle with x: " + g.getxPos() + " y: " + g.getyPos());
				gizmos.add(g);
				m.getRealEstate().addGizmoSingle(g, Integer.parseInt(line[2]), Integer.parseInt(line[3]));
				break;
			case "Ball":
				g = new Ball(Math.round(Float.parseFloat(line[2])), Math.round(Float.parseFloat(line[3])), 7,
						new Vect(100,100), Color.BLUE);
				
				if(hasAlias(line)) {
					aliases.put(line[1],g);
				}
				System.out.println("Creating ball");
				gizmos.add(g);
				m.getRealEstate().addGizmoSingle(g, (int) Float.parseFloat(line[2]), (int) Float.parseFloat(line[3]));
				b = (Ball) g;
				break;
			case "Absorber":
				
				int xPos = Integer.parseInt(line[2]);
				int yPos = Integer.parseInt(line[3]);
				int xL = Integer.parseInt(line[4]) - xPos;
				int yL = Integer.parseInt(line[5]) - yPos;
				
				g = new Absorber(xPos, yPos, xL + xPos , yL + yPos, Color.MAGENTA);
				
				if(hasAlias(line)) {
					aliases.put(line[1], g);
				}
				
				gizmos.add(g);
				
				for(int i =0; i < xL; i++)
					for(int j=0; j < yL; j++){
						points.add(new Point(xPos + i, yPos + j));
					}
						
				
				m.getRealEstate().addGizmo(g, points);
				
				System.out.println("Creating an absorber with x: " + g.getxPos() + " y: " + g.getyPos());
				
				break;
			case "Rotate":
				if(hasAlias(line)) {
					g = aliases.get(line[1]);
					System.out.println("rotating: " + line[1]);
					if(g instanceof Triangle) {
						((Triangle) g).rotate();
					} else if(g instanceof Flipper) {
						((Flipper) g).rotate();
					} // etc
				}
				break;
			case "Move":
				if(hasAlias(line)) {
					// update coordinates
					g = aliases.get(line[1]);
					System.out.println("Moving gizmo to location x: " + g.getxPos() + " y: " + g.getyPos());
					g.setxPos(Double.parseDouble(line[2]));
					g.setyPos(Double.parseDouble(line[3]));
				}
				break;
			case "Delete":
				if(hasAlias(line)) {
					System.out.println("Removing gizmo");
					gizmos.remove(aliases.get(line[1]));
				}
				break;
			case "LeftFlipper":
				g = new Flipper(Integer.parseInt(line[2]), Integer.parseInt(line[3]), 3, false, Color.YELLOW);
				System.out.println("Making left flipper at x: " + g.getxPos() / 25+ " y: " + g.getyPos());
				
				if (hasAlias(line)) {
					aliases.put(line[1], g);
				} 
				gizmos.add(g);
				
				int x = Integer.parseInt(line[2]);
				int y = Integer.parseInt(line[3]);
				points.add(new Point(x, y));
				points.add(new Point(x + 1, y));
				points.add(new Point(x, y + 1));
				points.add(new Point(x + 1, y + 1));
				m.getRealEstate().addGizmo(g, points);
				break;
				
			case "RightFlipper":
				g = new Flipper(Integer.parseInt(line[2]) + 1, Integer.parseInt(line[3]), 2, true, Color.YELLOW);
				System.out.println("Making right flipper at x: " + g.getxPos() / 25 + " y: " + g.getyPos());
				if (hasAlias(line)) {
					
					aliases.put(line[1], g);
				} 
				gizmos.add(g);
				
				points = new ArrayList<Point>();
				
				int xR = Integer.parseInt(line[2]) + 1;
				int yR = Integer.parseInt(line[3]);
				points.add(new Point(xR, yR));
				points.add(new Point(xR - 1, yR));
				points.add(new Point(xR, yR + 1));
				points.add(new Point(xR - 1, yR + 1));

				m.getRealEstate().addGizmo(g, points);
				break;
			case "KeyConnect":
				g = aliases.get(line[4].toString());
			
				int keyCode = Integer.parseInt(line[2].toString());
				
				//Key pressed
				if(line[3].equals("down")){
					
					TriggerKeyPressed  tkp = new TriggerKeyPressed(keyCode);
					
					if(g instanceof Flipper) {
						tkp.addActionFlip(g);
					}
					else if(g instanceof Absorber){
						tkp.addActionReleaseBall(g);
					}
					else {
						tkp.addActionChangeColour(g, Color.GREEN);
					}
					
					m.addTriggerPressed(tkp);
				}
				//Key Released
				else{
					
					TriggerKeyReleased tkr = new TriggerKeyReleased(keyCode);
					
					if(g instanceof Flipper) {
						tkr.addActionRetract(g);
					}
					else if(g instanceof Absorber){
						tkr.addActionReleaseBall(g);
					}
					else {
						tkr.addActionChangeColour(g, Color.GREEN);
					}
					
					m.addTriggerReleased(tkr);
				}
				
				
				break;
			case "Connect":
				
				Gizmo g1 = aliases.get(line[1].toString());
				Gizmo g2 = aliases.get(line[2].toString());
				System.out.println("Connecting..");
				
				TriggerCollision t = new TriggerCollision(g1);
				
				if(g2 instanceof Flipper) {
					t.addActionFlipperSwitch(g2);
				}
				else if(g2 instanceof Absorber){
					t.addActionReleaseBall(g2);
				}
				else {
					t.addActionChangeColour(g2, Color.GREEN);
				}
				
				triggerCollisions.add(t);
				
				break;
			case "Gravity":
				m.setGravity(Double.parseDouble(line[1].toString()));
				break;
			case "Friction":
				m.setMU(Double.parseDouble(line[1].toString()));
				m.setMU2(Double.parseDouble(line[2].toString()));
				break;
			default:
				return false;
		}
		return true;
	}
}
