package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainInitialize.BackgroundImgPanel;
import mainInitialize.GameStats;
import mainInitialize.MainMenu;
import ultilityTools.ImageTools;

/**
 * this class represents the panel of the Briefer extends JPanel because it is panel
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class BrieferScreen extends JPanel {
	private JButton _restartBtn;
	private JButton _newGameBtn;
	private JButton _backToMenu;
	private JLabel _briefText;
	private static BrieferScreen singleton;
	private static JDialog _parent;

	/**
	 * Constructor sets up buttons of the victory\lose screen
	 * 
	 * @param playerWon
	 *            - if true , Player won text will appear with new game option. else enemy won
	 */
	private BrieferScreen(boolean playerWon) {
		setBackground(Color.BLACK);
		setUpLabel(playerWon);
		if (playerWon) {
			setUpNewGame();
		}
		setUpRestart();
		setUpBack();
	}

	/**
	 * initialize the singleton instance
	 * 
	 * @return BrieferScreen only instance
	 */
	public static BrieferScreen init(boolean playerWon, JDialog parent) {
		singleton = new BrieferScreen(playerWon);
		_parent = parent;
		return singleton;
	}

	/**
	 * get the Singleton instance
	 * 
	 * @return BrieferScreen only instance
	 */
	public static BrieferScreen getInstance() {
		return singleton;
	}

	/**
	 * Creates a label with the proper img
	 * 
	 * @param playerWon-if
	 *            true , Player won text will appear . else enemy won text
	 */
	private void setUpLabel(boolean playerWon) {
		if (playerWon)
			_briefText = ImageTools.makeLabel("srcImages\\Menu\\playerWon.png");
		else
			_briefText = ImageTools.makeLabel("srcImages\\Menu\\enemyWon.png");
		add(_briefText);
	}

	/**
	 * Creates Restart button that starts the game with previous map
	 */
	private void setUpRestart() {
		_restartBtn = ImageTools.makeButton("srcImages\\Menu\\restartIcon.png");
		_restartBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameStats.restartGame(false);
				GameStats.startGame();
				BrieferScreen.getInstance().setVisible(false);
				_parent.setVisible(false);
			}
		});
		add(_restartBtn);
	}

	/**
	 * Creates NewGame button that starts the game with new map
	 */
	private void setUpNewGame() {
		_newGameBtn = ImageTools.makeButton("srcImages\\Menu\\startIcon.png");
		_newGameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameStats.restartGame(true);
				GameStats.startGame();
				BrieferScreen.getInstance().setVisible(false);
				_parent.setVisible(false);
			}
		});
		add(_newGameBtn);
	}

	/**
	 * Creates Back to menu button that closes the mapFrame and opens the menu
	 */
	private void setUpBack() {
		_backToMenu = ImageTools.makeButton("srcImages\\Menu\\mainMenuIcon.png");
		_backToMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu.getInstance().setVisible(true);
				MapGFrame.getInstance().setVisible(false);
				BrieferScreen.getInstance().setVisible(false);
				_parent.setVisible(false);
			}
		});
		add(_backToMenu);
	}
}
