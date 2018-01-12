package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class RealEstate {

	private final int gridX = 20;
	private final int gridY = 20;
	private Gizmo[][] gizmoGrid;
	
	public RealEstate(){
		gizmoGrid = new Gizmo[gridX][gridY];
	}
	
	public boolean addGizmo(Gizmo g, List<Point> points){
		
		if(!isClear(points, null))	return false;
		
		for(Point p : points){
			gizmoGrid[p.x][p.y] = g;
		}
		
		return true;
	}
	
	//g is the gizmo you don't want to check for eg self
	// leave g null toignore
	public boolean isClear(List<Point> points, Gizmo g){
		
		for(Point p : points){
			
			if(p.x >= gridX || p.y >= gridY || p.x < 0 || p.y < 0){
				
				System.out.println("RealEstate: Point out of bounds");
				return false;
			}
			
			if(gizmoGrid[p.x][p.y] != null && gizmoGrid[p.x][p.y] != g){
				
				//System.out.println("RealEstate: Point already occupied");
				return false;
			}
		}
		
		return true;
	}
	
	public boolean addGizmoSingle(Gizmo g, int x, int y){
		
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(x, y));
		
		return addGizmo(g, points);
	}

	public boolean isClearSingle(int x, int y){
	
	List<Point> points = new ArrayList<Point>();
	points.add(new Point(x, y));
	
	return isClear(points, null);
}
	
	public void removeGizmo(Gizmo g){
		
		for(int i=0; i < gridX; i++)
			for(int j=0; j < gridY; j++){
				
				if(gizmoGrid[i][j] == g)
					gizmoGrid[i][j] = null;
			}		
	}
	
	public Gizmo getGizmoAt(int x, int y){
		return gizmoGrid[x][y];
	}
	
	public void removeAll(){
		for(int i=0; i < gridX; i++)
			for(int j=0; j < gridY; j++){
				gizmoGrid[i][j] = null;
			}
	}
	
	public Gizmo[][] getGrid(){
		return gizmoGrid;
	}
	
	//x and y bounds check done else where
	public Gizmo getGizmo(int x, int y){
		return gizmoGrid[x][y];
	}

}


















