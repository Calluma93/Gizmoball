package view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.BuildListener;
import controller.ConnectionListener;
import controller.LoadListener;
import controller.PlayListener;
import controller.RenderListener;
import controller.SaveListener;
import controller.ViewListener;
import model.Model;

public class View extends JFrame {

	private JPanel contentPane;
	private JPanel dv;
	private JPanel bp;
	private Model model;
	private ActionListener listener;
	private BuildListener buildListener;
	private PlayListener playListener;
	private LoadListener loadListener;
	private ViewListener viewListener;
	private SaveListener saveListener;
	private ConnectionListener conListener;
	private RenderListener renderListener;
	private Board board;
	private JPanel boardPanel;
	JPanel buildPanel;
	private JTextField txtPlayerName;
	private JTextField textField;
	private List<JSlider> Slider;

	IView playView;
	IView buildView;
	FlipperView flipperView;

	private boolean playMode = true;

	public View(Model m) {
		this.model = m;

		buildListener = new BuildListener(this, m);
		playListener = new PlayListener(m, this);
		saveListener = new SaveListener(m);
		loadListener = new LoadListener(m, this);
		viewListener = new ViewListener(this, m);
		conListener = new ConnectionListener(m);
		renderListener = new RenderListener(m, this);

		buildView = new BuildView();
		playView = new PlayView();

		defaultFrame();
		createBoard();
		menuBarPanel();
	}

	public void createBoard() {
		board = new Board(500, 500, model);
		boardPanel = new JPanel();
		boardPanel.setBounds(5, 25, 500, 505);
		contentPane.add(boardPanel);
		boardPanel.add(board);
	}

	public void defaultFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 575);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	public void developmentPanel() {
		JPanel developmentPanel = new JPanel();
		developmentPanel.setBounds(0, 25, 75, 600);
		contentPane.add(developmentPanel);
		developmentPanel.setLayout(null);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(listener);
		startButton.setBounds(0, 0, 75, 150);
		developmentPanel.add(startButton);

		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(listener);
		stopButton.setBounds(0, 148, 75, 150);
		developmentPanel.add(stopButton);

		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		quitButton.setBounds(0, 444, 75, 49);
		developmentPanel.add(quitButton);

		JButton testButton = new JButton("Test");
		testButton.addActionListener(listener);
		testButton.setBounds(0, 500, 75, 49);
		developmentPanel.add(testButton);
		testButton.setVisible(true);
	}

	private void menuBarPanel() {
		JMenuBar toolBar = new JMenuBar();
		toolBar.setBounds(0, 0, 900, 21);
		contentPane.add(toolBar);

		JMenu mFile = new JMenu("File");
		toolBar.add(mFile);
		JMenuItem miSave = new JMenuItem("Save");
		miSave.addActionListener(saveListener);
		mFile.add(miSave);
		JMenuItem miLoad = new JMenuItem("Load");
		miLoad.addActionListener(loadListener);
		mFile.add(miLoad);
		JMenuItem miExit = new JMenuItem("Exit");
		miExit.addActionListener(viewListener);
		mFile.add(miExit);

		JMenu mMode = new JMenu("Mode");
		toolBar.add(mMode);
		JMenuItem miBuild = new JMenuItem("Build");
		miBuild.addActionListener(viewListener);
		miBuild.addActionListener(playListener);
		mMode.add(miBuild);
		JMenuItem miPlay = new JMenuItem("Play");
		miPlay.addActionListener(viewListener);
		mMode.add(miPlay);
		
		JMenu mRender = new JMenu("Render");
		toolBar.add(mRender);
		
		JMenuItem miNormal = new JMenuItem("Normal");
		miNormal.addActionListener(renderListener);
		mRender.add(miNormal);
		
		JMenuItem miRealEstate = new JMenuItem("Real Estate");
		miRealEstate.addActionListener(renderListener);
		mRender.add(miRealEstate);
		
		JMenuItem miCollidables = new JMenuItem("Collidables");
		miCollidables.addActionListener(renderListener);
		mRender.add(miCollidables);
	}

	private void playerScorePanel() {
		JPanel panel = new JPanel();
		panel.setBounds(83, 541, 500, 43);
		contentPane.add(panel);
		panel.setLayout(null);

		txtPlayerName = new JTextField();
		txtPlayerName.setBounds(79, 11, 140, 20);
		panel.add(txtPlayerName);
		txtPlayerName.setColumns(10);

		JLabel lblScore = new JLabel("Score");
		lblScore.setBounds(229, 14, 46, 14);
		panel.add(lblScore);

		JLabel lblPlayerName = new JLabel("Player Name:");
		lblPlayerName.setBounds(10, 14, 76, 14);
		panel.add(lblPlayerName);

		textField = new JTextField();
		textField.setBounds(263, 11, 140, 20);
		panel.add(textField);
		textField.setColumns(10);

	}

	public void createBuildView() {
		board.enableGridLines();
		board.repaint();
		playMode = false;
		try {
			List<JButton> playButtons = playView.getJButtons();

			for (JButton b : playButtons) {

				dv.add(b);
				b.removeActionListener(playListener);
			}
			dv.removeAll();
			contentPane.remove(dv);
		} catch (Exception e) {

		}
		buildView.createButtons();

		bp = buildView.createPanel();
		bp.setLayout(null);
		contentPane.add(bp);
		List<JRadioButton> buildRadioButtons = buildView.getJRadioButtons();
		ButtonGroup buttonGroup = new ButtonGroup();
		for (JRadioButton b : buildRadioButtons) {
			buttonGroup.add(b);
			bp.add(b);
			b.addActionListener(buildListener);
		}
		List<JButton> buildButtons = buildView.getJButtons();
		for (JButton b : buildButtons) {
			bp.add(b);
			b.addActionListener(buildListener);
		}
		List<JTextField> inputField = buildView.getInputField();
		for (JTextField b : inputField) {
			bp.add(b);
			b.addActionListener(buildListener);
		}
		List<JLabel> inputLabel = buildView.getJLabel();
		for (JLabel b : inputLabel) {
			bp.add(b);
		}

		Slider = buildView.getSlider();
		for (JSlider b : Slider) {
			bp.add(b);
			b.addChangeListener(buildListener);
		}
		// boardPanel.setBounds(5, 20, 500, 505);
		setBounds(100, 100, buildView.getFrameSizeX(), buildView.getFrameSizeY());
	}

	public JSlider getGravSlider() {
		return Slider.get(0);
	}

	public JSlider getFricSlider() {
		return Slider.get(1);
	}

	public void createPlayView() {

		board.disableGridLines();
		board.repaint();
		playMode = true;
		try {
			List<JButton> buildButtons = buildView.getJButtons();

			for (JButton b : buildButtons) {

				dv.add(b);
				b.removeActionListener(buildListener);
			}

			List<JRadioButton> buildRadio = buildView.getJRadioButtons();

			for (JRadioButton r : buildRadio) {

				dv.add(r);
				r.removeActionListener(buildListener);
			}

			bp.removeAll();
			contentPane.remove(bp);
		} catch (Exception e) {

		}

		playView.createButtons();

		// boardPanel.setBounds(5, 20, 500, 505);

		dv = playView.createPanel();
		dv.setLayout(null);
		contentPane.add(dv);
		List<JButton> playButtons = playView.getJButtons();

		for (JButton b : playButtons) {

			dv.add(b);
			b.addActionListener(playListener);
		}

		contentPane.setBackground(Color.white);
		setBounds(100, 100, playView.getFrameSizeX(), playView.getFrameSizeY());
	}

	public boolean getCurrentMode() {
		return playMode;
	}

	public Board getBoard() {
		return board;
	}

	public void PlayMode() {
		buildPanel.setVisible(false);
	}

	public void BuildMode() {
		buildPanel.setVisible(true);
	}

	public void FlipperMode() {
		buildPanel.setVisible(false);
	}

	public String FileChooser() {
		// Create a file chooser
		final JFileChooser fc = new JFileChooser();
		// In response to a button click:
		fc.showOpenDialog(this);
		String s = fc.getSelectedFile().getAbsolutePath();
		return s;
	}

	public String openKeyDialog() {
		JOptionPane keyWindow = new JOptionPane();
		boolean keyEntered = false;
		String key = "";

		while (!keyEntered) {
			String userInput = keyWindow.showInputDialog("Please enter a key (type 'space' for the space bar)");
			key = userInput.replaceAll("\\s", "");

			if (key.equals("space")) {
				keyEntered = true;
			} else if (key.length() > 1) {
				keyWindow.showMessageDialog(this, "Please only enter 1 Character.", "ERROR", JOptionPane.ERROR_MESSAGE);
			} else if (key.length() == 0) {
				keyWindow.showMessageDialog(this, "Please enter a key.", "ERROR", JOptionPane.ERROR_MESSAGE);
			} else {
				keyEntered = true;
			}
		}

		keyWindow.showMessageDialog(this, "Key successfully bound to Gizmo!", "Key trigger added",
				JOptionPane.PLAIN_MESSAGE);
		return key;
	}
	
	public void setRenderMode(int mode){
		board.setMode(mode);
	}
}
