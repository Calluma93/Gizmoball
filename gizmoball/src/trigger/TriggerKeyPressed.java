package trigger;

public class TriggerKeyPressed extends Trigger{
	
	private int keyCode;
	
	public TriggerKeyPressed(int keyCode){
		super();
		this.keyCode = keyCode;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public void checkAndDo(int key){
		if(key == keyCode)
			super.doActions();
	}
	
}
