package view;

import java.util.ArrayList;
import java.util.List;
import model.Model;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class BuildView implements IView{

	
	private List<JButton> buttonList;
	private List<JRadioButton> radioList;
	private List<JTextField> inputList;
	private List<JLabel> labelList;
	private List<JSlider> sliderList;
	private JSlider gravSlider;
	private JSlider frictSlider;
	private int sizeX = 730;
	private int sizeY = 570;
	Model m;

	
	public BuildView(){
		buttonList = new ArrayList();
		radioList = new ArrayList();
		inputList = new ArrayList();
		labelList = new ArrayList();
		sliderList = new ArrayList();
	}
	
	@Override
	public List<JButton> getJButtons() {
		// TODO Auto-generated method stub
		return buttonList;
	}
	
	@Override
	public List<JRadioButton> getJRadioButtons() {
		// TODO Auto-generated method stub
		return radioList;
	}

	
	@Override
	public List<JTextField> getInputField() {
		// TODO Auto-generated method stub
		return inputList;
	}
	
	@Override
	public List<JSlider> getSlider() {
		// TODO Auto-generated method stub
		return sliderList;
	}
		
	@Override
	public int getListSize() {
		// TODO Auto-generated method stub
		return buttonList.size();
	}

	@Override
	public int getFrameSizeX() {
		// TODO Auto-generated method stub
		return sizeX;
	}

	@Override
	public int getFrameSizeY() {
		// TODO Auto-generated method stub
		return sizeY;
	}

	@Override
	public void createButtons() {
		// TODO Auto-generated method stub
		
		JRadioButton square = new JRadioButton("Square");
		square.setBounds(0, 0, 200, 30);
		radioList.add(square);
		
		JRadioButton triangle = new JRadioButton("Triangle");
		triangle.setBounds(0, 30, 200, 30);
		radioList.add(triangle);
		
		JRadioButton flipperLeft = new JRadioButton("Left Flipper");
		flipperLeft.setBounds(0, 60, 200, 30);
		radioList.add(flipperLeft);
		
		JRadioButton flipperRight = new JRadioButton("Right Flipper");
		flipperRight.setBounds(0, 90, 200, 30);
		radioList.add(flipperRight);
		
		JRadioButton circle = new JRadioButton("Circle");
		circle.setBounds(0, 120, 200, 30);
		radioList.add(circle);
		
		JRadioButton absorber = new JRadioButton("Absorber");
		absorber.setBounds(0, 150, 200, 30);
		radioList.add(absorber);
		
		JRadioButton ball = new JRadioButton("Ball");
		ball.setBounds(0, 180, 200, 30);
		radioList.add(ball);
		
		JButton btnMove = new JButton("Move");
		btnMove.setBounds(0, 210, 100, 30);
		buttonList.add(btnMove);
		
		JButton btnRotate = new JButton("Rotate");
		btnRotate.setBounds(101, 210, 100, 30);
		buttonList.add(btnRotate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(0, 240, 100, 30);
		buttonList.add(btnDelete);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(101, 240, 100, 30);
		buttonList.add(btnDisconnect);
		
		JButton btnClear = new JButton("Clear Board");
		btnClear.setBounds(50, 330, 100, 30);
		buttonList.add(btnClear);
		
		JButton btnKeyCon = new JButton("Key Connect");
		btnKeyCon.setBounds(0, 270, 200, 30);
		buttonList.add(btnKeyCon);
		
		JButton btnGizmoCon = new JButton("Gizmo Connect");
		btnGizmoCon.setBounds(0, 300, 200, 30);
		buttonList.add(btnGizmoCon);
		
		JLabel Gravitylbl = new JLabel("Gravity");
		Gravitylbl.setBounds(10, 350, 100, 30);
		labelList.add(Gravitylbl);
		
		gravSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 5);
		sliderList.add(gravSlider);		
		gravSlider.setValue(25);
		gravSlider.setMajorTickSpacing(5);
		gravSlider.setSnapToTicks(true);
		gravSlider.setPaintTicks(true);
		gravSlider.setPaintLabels(true);
		gravSlider.setBounds(0, 375, 200, 50);
		//gravSlider.addChangeListener(e -> setGrav());
		
		JLabel Frictionlbl = new JLabel("Friction");
		Frictionlbl.setBounds(10, 420, 100, 30);
		labelList.add(Frictionlbl);
		
		frictSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
		sliderList.add(frictSlider);
		frictSlider.setValue(5);
		frictSlider.setMajorTickSpacing(1);
		frictSlider.setSnapToTicks(true);
		frictSlider.setPaintTicks(true);
		frictSlider.setPaintLabels(true);
		frictSlider.setBounds(0, 440, 200, 50);
		//frictSlider.addChangeListener(e -> setFrict());
		

	}
	
	@Override
	public JPanel createPanel() {
		// TODO Auto-generated method stub
		JPanel buildPanel = new JPanel();
		buildPanel.setBounds(510, 25, 200, 505);
		return buildPanel;
	}

	@Override
	public List<JLabel> getJLabel() {
		// TODO Auto-generated method stub
		return labelList;
	}

	

	


}
