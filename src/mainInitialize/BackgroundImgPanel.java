package mainInitialize;

import images.Img;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ultilityTools.ImageTools;
import unit.customizeScreen.CustomizeFrame;
import unit.customizeScreen.DisplayScreen;
import unit.player.Player;
import map.MapGFrame;

/**
 * this class represents the panel of mainMenu extends JPanel because it is panel
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class BackgroundImgPanel extends JPanel {
	// The buttons
	private JButton _startBtn;
	private JButton _exitBtn;
	private JButton _settingsBtn;
	private JButton _instructionBtn;
	// the images
	private Img _gameLogo;
	// gif related
	private JLabel _gifPlayer;
	private ImageIcon _imgIcon;
	// The array of available gifs
	private String _gifScenePath[] = { "Menu\\scene1.gif", "Menu\\scene2.gif", "Menu\\scene3.gif" };
	// current gif index
	private static int _currentGifIndex = 0;
	// the button panel
	private static JPanel _buttonPanel;
	// The array of available buttonIcons
	// if the item uses getResource , srcImages is not required at start
	private String _buttonImgPath[] = { "srcImages\\Menu\\startIcon.png", "srcImages\\Menu\\settingsIcon.png",
			"srcImages\\Menu\\exitIcon.png", "srcImages\\Menu\\continueIcon.png",
			"srcImages\\Menu\\instructionsIcon.png" };
	/** the instance of the game */
	private static BackgroundImgPanel singleton = new BackgroundImgPanel(MainMenu.getInstance().getGifPlayer());

	/**
	 * constructor - creates the buttons assigns button listeners and advances the gifPlayer if a
	 * new game button is clicked
	 * 
	 * @param main
	 *            - the frame from which the backgroundImgPanel was called
	 * @param gifPlayer
	 *            - the label in which gifs will be displayed
	 */
	private BackgroundImgPanel(JLabel gifPlayer) {
		_gifPlayer = gifPlayer;
		new Img("Menu\\menuBackground.png", 0, 0, MainMenu.getInstance().getWidth(),
				MainMenu.getInstance().getHeight());
		_gameLogo = new Img("Menu\\gameLogo.png", 0, 0, 264, 100);

		_buttonPanel = new JPanel();
		_startBtn = ImageTools.makeButton(_buttonImgPath[0]);
		_settingsBtn = ImageTools.makeButton(_buttonImgPath[1]);
		_instructionBtn = ImageTools.makeButton(_buttonImgPath[4]);
		_exitBtn = ImageTools.makeButton(_buttonImgPath[2]);
		_startBtn.addActionListener(new ActionListener() {
			/**
			 * the startButton action listener. increases the number on the currentGif and changes
			 * it initiates the game or shows it if it was hidden sets the frame invisible
			 */
			public void actionPerformed(ActionEvent e) {
				MainMenu.getInstance().setVisible(false);

				++_currentGifIndex;
				_currentGifIndex %= _gifScenePath.length;
				_imgIcon = new ImageIcon(
						singleton.getClass().getClassLoader().getResource(_gifScenePath[_currentGifIndex]));

				_gifPlayer.setIcon(_imgIcon);
				/*
				 * if (GameStats.getInstance().getPlayers().size() == 0) {
				 * Main.initPlayers();//initiates positions for players } if
				 * (CustomizeFrame.getInstance() != null) { ((Player)
				 * GameStats.getPlayers().get(0)).setCustomGraphics(64, 64);//set default custom
				 * graphics }
				 */

				if (MapGFrame.getInstance() == null) {// if map doesnt exist
					GameStats.restartPositions();
					MapGFrame.init("Game");
					BufferedImage buttonIcon = null;
					try {
						buttonIcon = ImageIO.read(new File(_buttonImgPath[3]));
						_startBtn.setIcon(new ImageIcon(buttonIcon));
					} catch (IOException e1) {
					}

				} else {// if map exists
					MapGFrame.getInstance().setVisible(true);
				}
				GameStats.startGame();
			}
		});
		_instructionBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_buttonPanel.setVisible(false);
				add(InstructionPanel.init());
				InstructionPanel.getInstance().setVisible(true);
			}
		});

		_settingsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu.getInstance().setVisible(false);
				if (CustomizeFrame.getInstance() == null)
					CustomizeFrame.init();
				else {
					CustomizeFrame.getInstance().setVisible(true);
				}
				DisplayScreen.copyToDisplay();
			}
		});
		_exitBtn.addActionListener(new ActionListener() {
			/** the exitButton action listener. exits the system */
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		_buttonPanel.setLayout(new BoxLayout(_buttonPanel, BoxLayout.Y_AXIS));

		BufferedImage img = ImageTools.toBufferedImage(_gameLogo);
		_buttonPanel.add(new JLabel(new ImageIcon(img)));
		_buttonPanel.setBackground(Color.black);
		_buttonPanel.add(_startBtn);
		_buttonPanel.add(_instructionBtn);
		_buttonPanel.add(_settingsBtn);
		_buttonPanel.add(_exitBtn);
		_buttonPanel.setOpaque(true);
		setLayout(new BorderLayout());
		add(_buttonPanel, BorderLayout.EAST);
	}

	/**
	 * get the Singleton instance
	 * 
	 * @return BackgroundImgPanel only instance
	 */
	public static BackgroundImgPanel getInstance() {
		return singleton;
	}

	public static JPanel getButtonPanel() {
		return _buttonPanel;
	}

}
