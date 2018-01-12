package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import model.Gizmo;
import model.Model;
import trigger.TriggerKeyPressed;
import trigger.TriggerKeyReleased;
import view.View;

public class GizmoKeyListener implements KeyListener {

	View v;
	Model m;
	
	public GizmoKeyListener(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	@Override
	public final void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed KeyCode: " + e.getKeyCode());

        //Loop through all Key pressed triggers and call checkAndDO
		for(TriggerKeyPressed tkp : m.getTriggerPressed()) tkp.checkAndDo(e.getKeyCode());
    }

	@Override
	public final void keyReleased(KeyEvent e) {
		System.out.println("Key Released KeyCode: " + e.getKeyCode());
		
		//Loop through all Key released triggers and call checkAndDO
		for(TriggerKeyReleased tkr : m.getTriggerReleased()) tkr.checkAndDo(e.getKeyCode());
	}

	@Override
	public final void keyTyped(KeyEvent e) {}

}
