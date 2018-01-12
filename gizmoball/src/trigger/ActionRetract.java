package trigger;

import model.Flipper;
import model.Gizmo;

public class ActionRetract extends Action{

	public ActionRetract(Gizmo gizmo){
		super(gizmo);
		if(!(gizmo instanceof Flipper))
			System.out.println("Warning: ActionFlip added to non flipper. doAction will do nothing.");
	}
	
	@Override
	public void doAction() {
		if(getGizmo() instanceof Flipper){
			Flipper flip = (Flipper) getGizmo();
			flip.setRetract();
		}	
	}
}