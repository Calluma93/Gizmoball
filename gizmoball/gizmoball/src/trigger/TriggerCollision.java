package trigger;

import model.Gizmo;

public class TriggerCollision extends Trigger{

	private Gizmo gizmo;
	
	public TriggerCollision(Gizmo gizmo){
		super();
		this.gizmo = gizmo;
	}
	
	public void checkAndDo(Gizmo other){
		if(gizmo.equals(other))
			super.doActions();
	}
	
	public Gizmo getGizmo() {return gizmo;}
}
