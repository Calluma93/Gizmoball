package trigger;

public class TriggerKeyReleased extends Trigger{
	
	private int keyCode;
	
	public TriggerKeyReleased(int keyCode){
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
