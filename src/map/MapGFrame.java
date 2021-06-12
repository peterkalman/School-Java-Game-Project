package map;

import mainInitialize.GameStats;
import mainInitialize.MainMenu;
import unit.player.Player;
import unit.player.PlayerG;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.input.KeyboardInput;

/**
 * MapGFrame is a class that represents the frame of the map extends JFrame because it is frame ,
 * implements EscapeButtonInterface because escape calls main menu
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class MapGFrame extends JFrame implements EscapeButtonInterface {
	private static MapGFrame singleton = null;

	/**
	 * Constructor
	 * 
	 * @param frameTitle-
	 *            the title of the frame
	 */
	private MapGFrame(String frameTitle) {
		super(frameTitle);
		setBackground(Color.black);
		/** adds to the keyboardInput it self as listener */
		KeyboardInput.getInstance().addListener(this);
		/** creates graphical map */

		// setLayout(new GridBagLayout());
		add(MapGPanel.init(15, 15, 64,true),BorderLayout.CENTER);
		
		
		//GameStats.generateNewMap(); // doesnt work because Frame is not initialized when this is called
		//add(ScorePanel.init());
		/** adds player panels */
		GameStats.addUnitPanels();
		// MapGPanel.getInstance().add(new Player(128, 64*3, 64, 64));

		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setUndecorated(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(15 * (64 + 1), 15 * (64 + 2) + 8);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height-this.getSize().height);
		setVisible(true);
	}

	/**
	 * initialize the singleton instance
	 * 
	 * @return MapGFrame only instance
	 */
	public static MapGFrame init(String frameTitle) {
		//if (singleton == null)
		singleton = new MapGFrame(frameTitle);
		return singleton;
	}

	/**
	 * get the Singleton instance
	 * 
	 * @return MapGFrame only instance
	 */
	public static MapGFrame getInstance() {
		return singleton;
	}

	/**
	 * the implemented method is called when 'escape' is clicked
	 */
	@Override
	public void EscapeButtonClicked() {
		MainMenu.getInstance().setVisible(true);
		GameStats.pauseGame();
		setVisible(false);
		// KeyboardInput.getInstance().setValue(_ind, value);
	}

}
