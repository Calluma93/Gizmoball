package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Global;
import physics.Circle;
import physics.LineSegment;

public class CircleG extends Gizmo{

	public CircleG(int xPos, int yPos, Color color) {
		super(xPos, yPos, color);
	}

	@Override
	public List<physics.Circle> getCircles() {
		List<Circle> cl = new ArrayList<Circle>();
		cl.add(new Circle(getxPos() + Global.L / 2.0, getyPos() + Global.L / 2.0, Global.L / 2.0));
		return cl;
	}

	@Override
	public List<LineSegment> getLines() {
		return Collections.emptyList();
	}

}
