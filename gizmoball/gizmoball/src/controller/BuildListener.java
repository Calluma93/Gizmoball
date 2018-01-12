package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;
import view.Board;
import view.View;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class BuildListener implements ActionListener , ChangeListener {

	private Model m;
	private View view;
	private Board b;
	private AddSquareListener addSquare;
	private AddTriangleListener addTriangle;
	private AddCircleListener addCircle;
	private AddFlipperListener addFlipper;
	private AddAbsorberListener addAbsorber;
	private EditGizmoListener editGizmo;
	private DeleteGizmoListener deleteGizmo;
	private AddBallListener addBall;

	public BuildListener(View tv, Model model) {
		this.view = tv;
		this.m = model;
	}

	public final void actionPerformed(final ActionEvent e) {
		b = view.getBoard();
		
		switch (e.getActionCommand()) {
		case "Ball":
			System.out.println("Ball");
			removeListeners();
			addBall = new AddBallListener(m, b);
			b.addMouseListener(addBall);
			break;
			
		case "Delete":
			System.out.println("Delete");
			removeListeners();
			deleteGizmo = new DeleteGizmoListener(m, b);
			b.addMouseListener(deleteGizmo);
			break;
			
		case "Edit":
			break;
			
		case "Clear Board":
			System.out.println("Clearing...");
			m.clearAllGizmos();
			m.getRealEstate().removeAll();
			b.repaint();
			break;
			
		case "Move":
		case "Rotate":
			System.out.println("Edit");
			removeListeners();
			editGizmo = new EditGizmoListener(m, b, view, e.getActionCommand());
			b.addMouseListener(editGizmo);
			b.addMouseMotionListener(editGizmo);
			break;
			
		case "Gizmo Connect":
			System.out.println("Gizmo Connect");
			removeListeners();
			editGizmo = new EditGizmoListener(m, b, view, e.getActionCommand());
			b.addMouseListener(editGizmo);
			break;
			
		case "Square":
			System.out.println("Square");
			removeListeners();
			addSquare = new AddSquareListener(m, b);
			b.addMouseListener(addSquare);
			break;
			
		case "Triangle":
			System.err.println("Triangle");
			removeListeners();
			addTriangle = new AddTriangleListener(m, b);
			b.addMouseListener(addTriangle);
			break;
			
		case "Absorber":
			System.out.println("Absorber");
			removeListeners();
			addAbsorber = new AddAbsorberListener(m, b);
			b.addMouseListener(addAbsorber);
			b.addMouseMotionListener(addAbsorber);
			break;
			
		case "Circle":
			System.out.println("Circle");
			removeListeners();
			addCircle = new AddCircleListener(m, b);
			b.addMouseListener(addCircle);
			break;
			
		case "Left Flipper":
			System.out.println("Left Flipper");
			removeListeners();
			addFlipper = new AddFlipperListener(m, b, "left");
			b.addMouseListener(addFlipper);
			break;
			
		case "Right Flipper":
			System.out.println("Right Flipper");
			removeListeners();
			addFlipper = new AddFlipperListener(m, b, "right");
			b.addMouseListener(addFlipper);
			break;
			
		case "Key Connect":
			System.out.println("Key Connect");
			removeListeners();
			editGizmo = new EditGizmoListener(m, b, view, e.getActionCommand());
			b.addMouseListener(editGizmo);
			break;
		}
	}

	private void removeListeners() {
		for (MouseListener ml : b.getMouseListeners()) {
			b.removeMouseListener(ml);
		}

		for (MouseMotionListener mml : b.getMouseMotionListeners()) {
			b.removeMouseMotionListener(mml);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		JSlider grav = view.getGravSlider();
		JSlider fric = view.getFricSlider();
		System.out.println("gravity: "+grav.getValue());
		System.out.println("friction: "+fric.getValue());
		m.setGravity(grav.getValue());
		m.setMU(fric.getValue());
	}

}
