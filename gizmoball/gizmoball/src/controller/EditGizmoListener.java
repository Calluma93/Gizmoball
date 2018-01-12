package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import main.Global;
import model.Absorber;
import model.Ball;
import model.CircleG;
import model.Flipper;
import model.Gizmo;
import model.Model;
import model.Square;
import model.Triangle;
import trigger.Trigger;
import trigger.TriggerCollision;
import trigger.TriggerKeyPressed;
import trigger.TriggerKeyReleased;
import view.Board;
import view.View;

public class EditGizmoListener implements MouseListener, MouseMotionListener {
	
	//"Move" "Rotate" "Key Connect"
	String editType;
	
	Boolean moving;
	Model m;
	Board b;
	Gizmo g;
	View v;

	private int orgXOffset;
	private int orgYOffset;
	
	//Gizmo Connect stuff
	private boolean hasChosen;
	private Gizmo movingGizmo; 		//Technichally doesn't have to be moving but if not it'll never collide 
	
	//Handles MOVE ROTATE KEYCONNECT
	
	public EditGizmoListener(Model m, Board b, View v, String editType) {
		this.m = m;
		this.b = b;
		this.v = v;
		this.editType = editType;
		this.moving = false;
		this.orgXOffset = 0;
		this.orgYOffset = 0;
		this.hasChosen = false;
		this.movingGizmo = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		int newX = e.getX() / Global.L;
		int newY = e.getY() / Global.L;
		
		List<Point> points = new ArrayList<Point>();
		
		// In order to keep it with in the cells, x / y must be a multiple of 25
		if(editType.equals("Move") && moving) {
			
			//Mo0ves Ball
			if(g instanceof Ball) { 
				
				if(m.getRealEstate().isClearSingle(newX, newY)){
					
					m.getRealEstate().removeGizmo(g);
					m.getRealEstate().addGizmoSingle(g, newX, newY);
				
					g.setxPos(Global.L * newX + Global.L / 2);
					g.setyPos(Global.L * newY + Global.L / 2);
				}
			}
			
			//Moves Absorber
			else if(g instanceof Absorber){
				
				Absorber ab = (Absorber)g;

				for(int i =0; i < ab.getxL(); i++){
					for(int n =0; n < ab.getyL(); n++){
						points.add(new Point(newX + i - orgXOffset, newY + n - orgYOffset));
					}
				}
				
				if(m.getRealEstate().isClear(points, ab)){
					
					m.getRealEstate().removeGizmo(ab);
					m.getRealEstate().addGizmo(ab, points);
					
					ab.setxPos((newX - orgXOffset) * Global.L);
					ab.setyPos((newY - orgYOffset) * Global.L);
				}
			}
			
			//Moves Flipper
			else if(g instanceof Flipper){
				
				Flipper flip = (Flipper)g;
				
				int flipPoint = flip.getPoint();
				
				int xOffset = flipPoint == 0 || flipPoint == 3 ? 1 : -1;
				int yOffset = flipPoint == 0 || flipPoint == 1 ? -1 : 1;
				
				points.add(new Point(newX - orgXOffset, newY - orgYOffset));
				points.add(new Point(newX + xOffset - orgXOffset, newY - orgYOffset));
				points.add(new Point(newX - orgXOffset, newY + yOffset - orgYOffset));
				points.add(new Point(newX + xOffset - orgXOffset, newY + yOffset - orgYOffset));
				
				if(m.getRealEstate().isClear(points, flip)){
					
					m.getRealEstate().removeGizmo(flip);
					m.getRealEstate().addGizmo(flip, points);
					
					flip.setxPos((newX - orgXOffset) * Global.L);
					flip.setyPos((newY - orgYOffset) * Global.L);
				}
			}
			
			//Moves everything else
			else{
				
				if(m.getRealEstate().isClearSingle(newX, newY)){
					
					m.getRealEstate().removeGizmo(g);
					m.getRealEstate().addGizmoSingle(g, newX, newY);
					
					g.setxPos(Global.L * newX);
					g.setyPos(Global.L * newY);
				}
			}
			b.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) { moving = false; }
	
	@Override
	public void mouseMoved(MouseEvent e) { }
	
	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { 

		int mouseX = e.getX() / Global.L;
		int mouseY = e.getY() / Global.L;
		
		//Set clicked on gizmo to currnet gizmo
		g = m.getRealEstate().getGizmoAt(mouseX, mouseY);
		if(g == null){
			
			g = m.getMovingGizmo(mouseX, mouseY);
			if(g == null) return;
		}
		
		
		//If flipper or absorber find out which square was clicked
		if(g instanceof Absorber || g instanceof Flipper){
			orgXOffset = (int) (mouseX - (g.getxPos() / Global.L));
			orgYOffset = (int) (mouseY - (g.getyPos() / Global.L));
		}
		
		//Used to test for empty space kinda
		List<Point> points = new ArrayList<Point>();
		
		switch (editType){
		
			//Move
			case "Move":
				
				moving = true;
				break;
			
			//Rotate
			case "Rotate":
				if(g instanceof Flipper){
					
					Flipper flip = (Flipper) g;
					int flipPoint = flip.getPoint();
					flipPoint = ++flipPoint == 4 ? 0 : flipPoint;
					
					int xOffset = flipPoint == 0 || flipPoint == 3 ? 1 : -1;
					int yOffset = flipPoint == 0 || flipPoint == 1 ? -1 : 1;
					
					points.add(new Point(mouseX - orgXOffset, mouseY - orgYOffset));
					points.add(new Point(mouseX + xOffset - orgXOffset, mouseY - orgYOffset));
					points.add(new Point(mouseX - orgXOffset, mouseY + yOffset - orgYOffset));
					points.add(new Point(mouseX + xOffset - orgXOffset, mouseY + yOffset - orgYOffset));
		
					if(m.getRealEstate().isClear(points, flip)){
						
						System.out.println("yo");
						m.getRealEstate().removeGizmo(flip);
						m.getRealEstate().addGizmo(flip, points);
						flip.rotate();
					}
				}
				else if(g instanceof Triangle){
					
					Triangle tri = (Triangle) g;
					tri.rotate();
				}
				break;
			
			//Connect
			case "Key Connect":
				
				String input = v.openKeyDialog();
				int keycode = input.equalsIgnoreCase("space") ? 32 : KeyEvent.getExtendedKeyCodeForChar(input.charAt(0)); 
				
				TriggerKeyPressed tkp = new TriggerKeyPressed(keycode);
				
				if(g instanceof Flipper){
					
					tkp.addActionFlip(g);
					
					//Trigger released added to flipper 
					TriggerKeyReleased tkr = new TriggerKeyReleased(keycode);
					tkr.addActionRetract(g);
					m.addTriggerReleased(tkr);
				}
				else if(g instanceof Absorber){
					
					tkp.addActionReleaseBall(g);
				}
				else{
					//Default action for demonstration
					tkp.addActionChangeColour(g, Color.yellow);
				}
				

				m.addTriggerPressed(tkp);
				break;
				
			case "Gizmo Connect":
				if(movingGizmo == null){
					movingGizmo = g;
					return;
				}
				
				TriggerCollision tc = new TriggerCollision(g);
				
				if(g instanceof Flipper){
					
					tc.addActionFlip(g);
				}
				else if(g instanceof Absorber){
					
					tc.addActionReleaseBall(g);
				}
				else{
					//Default action for demonstration
					tc.addActionChangeColour(g, Color.yellow);
				}
				
				movingGizmo.addCollisionTrigger(tc);
				break;

			}
		
		m.draw();

	}
}




