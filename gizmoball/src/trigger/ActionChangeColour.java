package trigger;

import java.awt.Color;
import model.Gizmo;

public class ActionChangeColour extends Action{

	private Color colour;
	
	public ActionChangeColour(Gizmo gizmo, Color colour){
		super(gizmo);
		this.colour = colour;
	}
	
	@Override
	public void doAction() {
		getGizmo().setColor(colour);
	}

}
