package model;

import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public interface Collidable {

	public List<Circle> getCircles();
	public List<LineSegment> getLines();
	public void onCollision(Gizmo other, Vect vel);
}
