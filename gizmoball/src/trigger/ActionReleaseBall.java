package trigger;

import model.Absorber;
import model.Gizmo;

public class ActionReleaseBall extends Action {

	public ActionReleaseBall(Gizmo gizmo) {
		super(gizmo);
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		if(getGizmo() instanceof Absorber) {
			if(((Absorber) getGizmo()).containsBall())
				((Absorber) getGizmo()).releaseBall();
		}
	}

	
}
