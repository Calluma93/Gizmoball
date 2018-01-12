package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class FlipperView implements IView {

	private List<JButton> buttonList;
	private int sizeX = 700;
	private int sizeY = 570;
	
	public FlipperView() {
		buttonList = new ArrayList();
	}
	
	public void createButtons() {
		JButton trigger = new JButton("Activate Trigger");
		JButton key = new JButton("Assign Key");
		buttonList.add(trigger);
		buttonList.add(key);
	}

	@Override
	public List<JButton> getJButtons() {
		return buttonList;
	}

	@Override
	public List<JRadioButton> getJRadioButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getListSize() {
		return buttonList.size();
	}

	@Override
	public int getFrameSizeX() {
		return sizeX;
	}

	@Override
	public int getFrameSizeY() {
		return sizeY;
	}

	@Override
	public JPanel createPanel() {
		JPanel developmentPanel = new JPanel();
		developmentPanel.setBounds(510, 25, 200, 505);
		return developmentPanel;
	}

	@Override
	public List<JTextField> getInputField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JLabel> getJLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JSlider> getSlider() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
