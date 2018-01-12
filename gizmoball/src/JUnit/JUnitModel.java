package JUnit;
import static org.junit.Assert.*;
import java.awt.Color;
import main.Global;
import model.Absorber;
import model.Ball;
import model.CircleG;
import model.Gizmo;
import model.Line;
import model.Model;
import model.Flipper;
import model.MovingGizmo;
import model.Square;
import model.Triangle;
import org.junit.Test;
import physics.Vect;
public class JUnitModel {
	
	Model mod = new Model();
	
	
	
	
	@Test
	public void getBallTest(){
		Ball ball = new Ball(0, 1, 6, new Vect(150, 1), Color.blue);
		mod.addGizmo(ball);
		assertFalse(mod.getBalls().isEmpty());
		assertTrue(mod.getBalls().size()==1);
	}
	@Test
	public void getFlippersTest(){
		Flipper flipper = new Flipper(2, 2, 3, true, Color.green);
		mod.addGizmo(flipper);
		assertFalse(mod.getFlippers().isEmpty());
		assertTrue(mod.getFlippers().size()==1);
	}
	
	@Test
	public void getCirclesTest(){
		
		CircleG circle = new CircleG (3, 7, Color.gray);
		mod.addGizmo(circle);
		assertFalse(mod.getCircles().isEmpty());
		assertTrue(mod.getCircles().size()==1);
	}
	
	@Test
	public void getSquaresTest(){
		Square square = new Square(1, 1, Color.red);
		mod.addGizmo(square);
		assertFalse(mod.getSquares().isEmpty());
		assertTrue(mod.getSquares().size()==1);
	}
	
	@Test
	public void getTrianglesTest(){
		Triangle triangle = new Triangle (1, 2, 3, Color.ORANGE);
		
		mod.addGizmo(triangle);
		assertFalse(mod.getTriangles().isEmpty());
		assertTrue(mod.getTriangles().size()==1);
	}
	
	@Test
	public void getLinesTest(){
		Line line = new Line(5, 6, 2, 2, Color.black);
		mod.addGizmo(line);
		assertFalse(mod.getLines().isEmpty());
		assertTrue(mod.getLines().size()==5);
	}
	
	@Test
	public void getAbsorbersTest(){
		Absorber absorber = new Absorber(1, 5, 5, 1, Color.yellow);
		mod.addGizmo(absorber);
		assertFalse(mod.getAbsorbers().isEmpty());
		assertTrue(mod.getAbsorbers().size()==1);
	}	
	
	
	@Test
	public void deleteBallTest(){
		Ball ball = new Ball(0, 1, 6, new Vect(150, 1), Color.blue);
		mod.addGizmo(ball);
		mod.removeGizmo(ball);
		assertTrue(mod.getBalls().isEmpty());
		assertTrue(mod.getBalls().size()==0);
	}
	@Test
	public void deleteFlippersTest(){
		Flipper flipper = new Flipper(2, 2, 3, true, Color.green);
		mod.addGizmo(flipper);
		mod.removeGizmo(flipper);
		assertTrue(mod.getFlippers().isEmpty());
		assertTrue(mod.getFlippers().size()==0);
	}
	
	@Test
	public void deleteCirclesTest(){
		CircleG circle = new CircleG (3, 7, Color.gray);
		mod.addGizmo(circle);
		mod.removeGizmo(circle);
		assertTrue(mod.getCircles().isEmpty());
		assertTrue(mod.getCircles().size()==0);
	}
	
	@Test
	public void deleteSquaresTest(){		
		Square square = new Square(1, 1, Color.red);
		mod.addGizmo(square);
		mod.removeGizmo(square);
		assertTrue(mod.getSquares().isEmpty());
		assertTrue(mod.getSquares().size()==0);
	}
	
	@Test
	public void deleteTrianglesTest(){
		Triangle triangle = new Triangle (1, 2, 3, Color.ORANGE);
		mod.addGizmo(triangle);
		mod.removeGizmo(triangle);
		assertTrue(mod.getTriangles().isEmpty());
		assertTrue(mod.getTriangles().size()==0);
	}
	
	@Test
	public void deleteLinesTest(){
		Line line = new Line(5, 6, 2, 2, Color.black);
		
		mod.addGizmo(line);
		mod.removeGizmo(line);		
		assertTrue(mod.getLines().size()==4);
	}
	
	@Test
	public void deleteAbsorbersTest(){
		Absorber absorber = new Absorber(1, 5, 5, 1, Color.yellow);
		mod.addGizmo(absorber);
		mod.removeGizmo(absorber);
		assertTrue(mod.getAbsorbers().isEmpty());
		assertTrue(mod.getAbsorbers().size()==0);
	}	
	
	@Test
	public void clearAllGizmosTest(){
		
		CircleG circle1 = new CircleG (1, 7, Color.gray);
		CircleG circle2 = new CircleG (13, 7, Color.gray);
		CircleG circle3 = new CircleG (3, 7, Color.gray);
		mod.addGizmo(circle1);
		mod.addGizmo(circle2);
		mod.addGizmo(circle3);
		mod.clearAllGizmos();
		assertTrue(mod.getCircles().isEmpty());
		assertTrue(mod.getCircles().size()==0);
	
	}
	
	@Test
	public void setGravityTest (){
	
		mod.setGravity(25);
		assertTrue(mod.getGravity() == 25);
	}
	
	@Test
	public void setMUTest (){
		
		mod.setMU(1);
		assertTrue(mod.getMU() == 1);
	}
		
	@Test
	public void setMU2Test (){
	
		mod.setMU2(1);
		assertTrue(mod.getMU2() == 1);
	}	
	
	@Test
	public void	checkFlipperCollisionTest(){
		
		MovingGizmo b1 = new Ball(2, 2, 6.25, new Vect(100,100), Color.RED);
		Flipper flipper = new Flipper(3, 4, 3, true, Color.green);
		mod.addGizmo(b1);
		mod.addGizmo(flipper);
		Boolean col = false;
		
		for(int i = 0; i < 50; i ++) {
			if(mod.checkFlipperCollision(b1, flipper)) {
				col = true;
			}
			mod.update();
		}
		assertTrue(col);
	}
	
	
	@Test
	public void	checkMovingCollisionTest(){
		
		MovingGizmo b1 = new Ball(0, 18, 6.25, new Vect(100,100), Color.RED);
		MovingGizmo b2 = new Ball(0, 19, 6.25, new Vect(100,-100), Color.RED);
		mod.addGizmo(b1);
		mod.addGizmo(b2);
		Boolean col = false;
		
		for(int i = 0; i < 100000; i ++) {
			if(mod.checkMovingCollision(b1, b2)) {
				col = true;
			}
			mod.update();
		}
		assertTrue(col);
	}
	
	
	@Test 
	public void checkSquareCollision() {
		
		MovingGizmo b = new Ball(2, 2, 6.25, new Vect(100,100), Color.RED);
		Square s = new Square(3, 4, Color.GREEN);
		mod.addGizmo(b);
		mod.addGizmo(s);
		Boolean col = false;
		
		for(int i = 0; i < 20; i ++) {
			if(mod.checkCollision(b, s)) {
				col = true;
			}
			mod.update();
		}
		assertTrue(col);
	}
	
	@Test 
	public void checkCircleCollision() {
		
		MovingGizmo b = new Ball(2, 2, 6.25, new Vect(100,100), Color.RED);
		CircleG circle = new CircleG (3, 4, Color.gray);
		mod.addGizmo(b);
		mod.addGizmo(circle);
		Boolean col = false;
		
		for(int i = 0; i < 20; i ++) {
			if(mod.checkCollision(b, circle)) {
				col = true;
			}
			mod.update();
		}
		assertTrue(col);
	}
	
	@Test 
	public void checkTriangleCollision() {
		
		MovingGizmo b = new Ball(2, 2, 6.25, new Vect(100,100), Color.RED);
		Triangle triangle = new Triangle (3, 4, 3, Color.ORANGE);
		mod.addGizmo(b);
		mod.addGizmo(triangle);
		Boolean col = false;
		
		for(int i = 0; i < 50; i ++) {
			if(mod.checkCollision(b, triangle)) {
				col = true;
			}
			mod.update();
		}
		assertTrue(col);
	}
	
	
	@Test
	public void getMovingGizmoTest (){
		
		MovingGizmo b = new Ball(2, 2, 6.25, new Vect(100,100), Color.RED);
		mod.addGizmo(b);

		Boolean equals = false;
		
		Gizmo g = mod.getMovingGizmo(2, 2);
		
		if(b == g) {
			equals = true;
		}
		
		assertTrue(equals);
		
		
	}
	
	
	
	
	



}
