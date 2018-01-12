package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import controller.ViewListener;

public class PlayView extends JFrame implements IView {
	
	private List<JButton> buttonList;
	private int sizeX = 605;
	private int sizeY = 570;
	
	public PlayView(){
		buttonList = new ArrayList();
	}
	
	@Override
	public void createButtons() {
		JButton startButton = new JButton("Start");
		startButton.setBounds(0, 0, 75, 30);
		JButton pauseButton = new JButton("Pause");
		pauseButton.setBounds(0, 30, 75, 30);
		buttonList.add(startButton);
		buttonList.add(pauseButton);
	}
	
	public JPanel createPanel(){
		JPanel developmentPanel = new JPanel();
		developmentPanel.setBounds(510, 25, 75, 505);
		return developmentPanel;
	}
	

	@Override
	public List<JButton> getJButtons() {
		return buttonList;
	}
	
	@Override
	public List<JLabel> getJLabel() {
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
	public List<JRadioButton> getJRadioButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JTextField> getInputField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JSlider> getSlider() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
