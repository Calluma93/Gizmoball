package trigger;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.Gizmo;

public class Trigger {

	private List<Action> actions;
	
	public Trigger(){
		actions = new ArrayList<Action>();
	}
	
	public void doActions(){
		for(Action action: actions)
			action.doAction();
	}
	
	public List<Action> getActions() {return actions;}
	
	public void addActionChangeColour(Gizmo gizmo, Color color){
		actions.add(new ActionChangeColour(gizmo, color));
	}
	
	public void addActionFlip(Gizmo gizmo){
		actions.add(new ActionFlip(gizmo));
	}
	
	public void addActionRetract(Gizmo gizmo){
		actions.add(new ActionRetract(gizmo));
	}
	
	public void addActionFlipperSwitch(Gizmo gizmo){
		actions.add(new ActionFlipperSwitch(gizmo));
	}
	
	public void addActionReleaseBall(Gizmo gizmo) {
		actions.add(new ActionReleaseBall(gizmo));
	}
}
