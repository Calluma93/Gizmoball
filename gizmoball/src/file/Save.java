package file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Global;
import model.Absorber;
import model.Ball;
import model.CircleG;
import model.Flipper;
import model.Gizmo;
import model.Line;
import model.Model;
import model.Square;
import model.Triangle;
import trigger.Action;
import trigger.TriggerCollision;
import trigger.TriggerKeyPressed;
import trigger.TriggerKeyReleased;

public class Save {

	Model m;
	List<Gizmo> gizmos;
	List<String> printCommands;
	List<String> endCommands;	//Commands that relyon other commands already being done
	Map<Gizmo, String> alias;
	
	public Save(Model m) {
		this.m = m;
		gizmos = new ArrayList<>();
		printCommands = new ArrayList<String>();
		endCommands = new ArrayList<String>();
		alias = new HashMap<>();
	}
	
	public boolean saveCurrentGame() {
		fillGizmos();
		String currentLine; 
		
		for(Gizmo g : gizmos) {
			currentLine = setCurrentLine(g);
			if(currentLine != null)
				printCommands.add(currentLine);
		}
		
		for(String end : endCommands){
			printCommands.add(end);
		}
		
		setConnections();
		setGravityandFriction();
		
		try {
			writeToDisk();
		} catch (IOException e) {
			System.out.println("Failed to save game!");
			return false;
		}
		
		System.out.println("Game successfully saved!");
		return true;
	}
	
	private String setCurrentLine(Gizmo g) {
		
		String gXPos = Integer.toString((int)g.getxPos() / Global.L);
		String gYPos = Integer.toString((int)g.getyPos() / Global.L);
		DecimalFormat df = new DecimalFormat(".#");
		
		if(g instanceof Ball) {
			String velX = df.format(((Ball) g).getVelo().x());
			String velY = df.format(((Ball) g).getVelo().y());
			alias.put(g, "B" + gXPos + gYPos);
			return "Ball B" + gXPos + gYPos + " " + gXPos + " " + gYPos + " " + velX + " " + velY;
			
		} else if(g instanceof Absorber) {
			
			Absorber ab = (Absorber)g;
			
			alias.put(g, "A" + gXPos + gYPos);
			return "Absorber A" + gXPos + gYPos + " " + gXPos + " " + gYPos + " " + (int)(ab.getxL() + (g.getxPos() / Global.L)) + " " + (int)(ab.getyL() + (g.getyPos() / Global.L));
			
		} else if (g instanceof CircleG) {
			alias.put(g, "C" + gXPos + gYPos);
			return "Circle C" + gXPos + gYPos + " " + gXPos + " "+ gYPos;
			
		} else if(g instanceof Flipper) {
			// Check for left & right flippers
			
			Flipper flip = (Flipper)g;
			int targetRotation = flip.getPoint(); 
			int rotation;
			
			if(flip.getOrientation()) {
				
				//offset right flipper by 1
				gXPos = "" + (Integer.parseInt(gXPos) - 1);
				
				rotation = 2;
				
				while(rotation != targetRotation){
					rotation = ++rotation == 4 ? 0 : rotation;
					endCommands.add("Rotate RF"  + gXPos + gYPos);
				}
				
				alias.put(g, "RF" + gXPos + gYPos);
				return "RightFlipper RF" + gXPos + gYPos + " " + gXPos + " " + gYPos;
			} else { 
				
				rotation = 3;
				
				while(rotation != targetRotation){
					rotation = ++rotation == 4 ? 0 : rotation;
					endCommands.add("Rotate RF"  + gXPos + gYPos);
				}
				
				alias.put(g, "LF" + gXPos + gYPos);
				return "LeftFlipper LF" + gXPos + gYPos + " " + gXPos + " " + gYPos;
			}
			
		} else if(g instanceof Line) {
			// Line is not part of the standard spec, to include line in the save file
			// uncomment:
			//return "Line L" + gXPos + gYPos + " " + gXPos + " " + gYPos;
		} else if(g instanceof Square) {
			alias.put(g, "S" + gXPos + gYPos);
			return "Square S" + gXPos + gYPos + " " + gXPos + " " + gYPos;
		} else if(g instanceof Triangle) {
			
			Triangle tri = (Triangle)g;
			
			int targetRotation = tri.getCorner(); 
			int rotation = 0;
			
			while(rotation != targetRotation){
				rotation = ++rotation == 4 ? 0 : rotation;
				endCommands.add("Rotate T"  + gXPos + gYPos);
			}
			
			alias.put(g, "T" + gXPos + gYPos);
			return "Triangle T" + gXPos + gYPos + " " + gXPos + " " + gYPos;
			
		} else {
			return "nonvalid gizmo";
		}
		return null;
	}
	
	private void setConnections() {
		
		for(TriggerKeyPressed tkp: m.getTriggerPressed()){
			for(Action act: tkp.getActions()){
				String line = "KeyConnect key " + tkp.getKeyCode() + " up " + alias.get(act.getGizmo());
				printCommands.add(line);
			}
		}
		
		for(TriggerKeyReleased tkr: m.getTriggerReleased()){
			for(Action act: tkr.getActions()){
				String line = "KeyConnect key " + tkr.getKeyCode() + " down " + alias.get(act.getGizmo());
				printCommands.add(line);
			}
		}
		
		for(Gizmo g: gizmos){
			for(TriggerCollision tc: g.getTriggerCollisions()){
				for(Action act : tc.getActions()){
					
					String line = "Connect " + alias.get(tc.getGizmo()) +" "+ alias.get(act.getGizmo());
					printCommands.add(line);
				}
			}
		}
		
		/*
		
		for(Gizmo g : gizmos) {
			List<TriggerKeyPressed> triggersKeys = g.getKeyPressedTriggers();
			List<TriggerCollision> triggerCollisions = g.getTriggerCollisions();
			
			for(int i = 0; i < triggersKeys.size(); i++) {
				String keycode = Integer.toString(triggersKeys.get(i).getKeyCode());
				String line = "KeyConnect key " + keycode +  " down " + alias.get(g);
				printCommands.add(line);
			}
			
			for(int j = 0; j < triggerCollisions.size(); j++) {
				//Connect C1110 RF137
				//String line = "Connect " + triggerCollisions.get(j).;
				//System.out.println(triggerCollisions.get(j);
			}
		}
		
		*/
	}
	
	private void setGravityandFriction() {
		printCommands.add("Gravity " + m.getGravity());
		printCommands.add("Friction " + m.getMU() + " " + m.getMU2());
	}
	
	
	private void fillGizmos() {
		if(!m.getAbsorbers().isEmpty()) { gizmos.addAll(m.getAbsorbers());	}
		if(!m.getCircles().isEmpty()) 	{ gizmos.addAll(m.getCircles()); 	}
		if(!m.getLines().isEmpty()) 	{ gizmos.addAll(m.getLines()); 		}
		if(!m.getSquares().isEmpty()) 	{ gizmos.addAll(m.getSquares()); 	}
		if(!m.getTriangles().isEmpty()) { gizmos.addAll(m.getTriangles()); 	}
		if(!m.getBalls().isEmpty()) 	{ gizmos.addAll(m.getBalls()); 		}
		if(!m.getFlippers().isEmpty())	{ gizmos.addAll(m.getFlippers());	}
	}
	
	private boolean writeToDisk() throws IOException {
		Date d = new Date();
		BufferedWriter outputFile = new BufferedWriter(new FileWriter("saves/gizmoball" + d.getTime() + ".txt"));
		
		for(int i = 0; i < printCommands.size(); i++) {
			outputFile.flush();
			outputFile.write(printCommands.get(i).toString() + "\n");
			outputFile.newLine();
		}
		
		outputFile.close();
		return true;
	}
}
