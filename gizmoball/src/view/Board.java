package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import physics.Circle;
import physics.LineSegment;
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

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public  class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected Model gm;
	private boolean gridLinesEnabled = false;
	private int renderMode;
	
	public Board(int w, int h, Model m) {
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		gm = m;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.renderMode = 0;
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	long timeStart = System.currentTimeMillis();
	
	//This is very temporary and needs updated
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);

		Graphics2D g2 = (Graphics2D) g;
		
		switch (renderMode){
		case 0:
			renderNormal(g2);
			break;
			
		case 1: 
			renderRealEstate(g2);
			break;
			
		case 2:
			renderCollidables(g2);
		}
		
		if(gridLinesEnabled) {
			//draw gridlines
			g2.setColor(Color.WHITE);
			for(int i = 1; i < Global.L; i++) { 
				int x = i * Global.L;
				g2.drawLine(x, 0, x, getSize().height);
			}
			
			for(int i = 1; i < Global.L; i++) {
				int y = i * Global.L;
				g2.drawLine(0, y, getSize().width, y);
			}
		}
		
		
	}
	
	public void renderNormal(Graphics2D g2){
		
		// Draw all the vertical lines
			for (Line vl : gm.getLines()) {
				g2.setColor(vl.getColor());
				g2.drawLine((int)vl.getxPos(),(int)vl.getyPos(),(int)(vl.getxPos() + vl.getXL() * Global.L),(int) (vl.getyPos() + vl.getYL() * Global.L));
			}
			
			//Draw all balls
			for(Ball b : gm.getBalls()){
				if (b != null && !b.isAbsorbed()) {
					g2.setColor(b.getColor());
					int x = (int) (b.getxPos() - b.getRadius());
					int y = (int) (b.getyPos() - b.getRadius());
					int width = (int) (2 * b.getRadius());
					g2.fillOval(x, y, width, width);
				}
			}
			
			//Draw all squares
			for(Square sq : gm.getSquares()){
				g2.setColor(sq.getColor());
				g2.fillRect((int)sq.getxPos(), (int)sq.getyPos(), Global.L, Global.L);
			}
			
			//Draw all triangles
			for(Triangle tri : gm.getTriangles()){
				g2.setColor(tri.getColor());
				tri.setDrawablePoints();
				g2.fillPolygon(tri.getDrawablePointsX(), tri.getDrawablePointsY(), 3);
			}
			
			//Draw flippers
			for(Flipper flip : gm.getFlippers()){

				g2.setColor(flip.getColor());
				Ellipse2D base = flip.getDrawableBaseCircle();
				Ellipse2D out = flip.getDrawableOuterCircle();

				flip.setDrawablePoints();
				
				g2.fillOval((int)base.getX(), (int)base.getY(), (int) base.getWidth(), (int)base.getHeight());
				g2.fillOval((int)out.getX(), (int)out.getY(), (int)base.getWidth(), (int) base.getWidth());
				g2.fillPolygon(flip.getDrawablePointsX(), flip.getDrawablePointsY(), 4);
				
			}
			
			//Draw Absorber
			for(Absorber ab: gm.getAbsorbers()){
				g2.setColor(ab.getColor());
				g2.fillRect((int)ab.getxPos(), (int)ab.getyPos(), ab.getxL() * Global.L , ab.getyL() * Global.L);
			}
			
			for(CircleG cg : gm.getCircles()){
				g2.setColor(cg.getColor());
				g2.fillOval((int)cg.getxPos(), (int)cg.getyPos(), Global.L, Global.L);
			}
				
	}
	
	public void renderRealEstate(Graphics2D g2){
		
		g2.setColor(Color.cyan);
		Gizmo[][] grid = gm.getRealEstate().getGrid();
		
		for(int i =0; i < Global.gridSize; i++)
			for(int j=0; j < Global.gridSize; j++)
				if(grid[i][j] != null)	g2.fillRect(i * Global.L, j * Global.L, Global.L, Global.L);
	}
	
	public void renderCollidables(Graphics2D g2){
		
		g2.setColor(Color.white);
		
		for(Gizmo gizmo: gm.getAllGizmos()){
			
			for(Circle cir: gizmo.getCircles()){
				g2.drawOval((int)(cir.getCenter().x() - cir.getRadius()), (int)(cir.getCenter().y() - cir.getRadius()), (int)cir.getRadius() * 2, (int)cir.getRadius() * 2);
			}
			
			for(LineSegment ls: gizmo.getLines()){
				g2.drawLine((int)ls.p1().x(), (int)ls.p1().y(), (int)ls.p2().x(), (int)ls.p2().y());
			}
		}
	}
	
	public void setMode(int mode){
		this.renderMode = mode;
		System.out.println(mode);
	}

	public void enableGridLines() {
		gridLinesEnabled = true;
	}
	
	public void disableGridLines() {
		gridLinesEnabled = false;
	}
	
	public void update(Observable arg0, Object arg1) {
			repaint();
	}
	
}
