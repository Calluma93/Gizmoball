package view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

public interface IView {

	public List<JButton> getJButtons();
	public List<JRadioButton> getJRadioButtons();
	public List<JTextField> getInputField();
	public List<JLabel> getJLabel();
	public int getListSize();
	public int getFrameSizeX();
	public int getFrameSizeY();
	public void createButtons();
	public JPanel createPanel();
	List<JSlider> getSlider();
}
