package trigger;

public class TriggerKey extends Trigger{
	
	private int keyCode;
	
	public TriggerKey(int keyCode){
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
