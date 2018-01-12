package trigger;

import model.Gizmo;

public abstract class Action implements ActionI{

	private Gizmo gizmo;

	public Action(Gizmo gizmo) {
		this.gizmo = gizmo;
	}

	public Gizmo getGizmo() {return gizmo;}
	public void setGizmo(Gizmo gizmo) {this.gizmo = gizmo;}
	
}
