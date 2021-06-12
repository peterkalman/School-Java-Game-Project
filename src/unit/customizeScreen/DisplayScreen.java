package unit.customizeScreen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import mainInitialize.GameStats;
import unit.player.Player;
import unit.player.PlayerG;

/**
 * The panel that contains the visuals of the player Custom
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class DisplayScreen extends JPanel {
	// static playerG
	private static PlayerG _player;
	private static DisplayScreen singleton = null;
	public static int _width;
	public static int _height;

	/**
	 * constructor initates the PlayerG
	 */
	private DisplayScreen() {
		setBackground(Color.gray);
		_width = 400;
		_height = 550;
		
		_player = new PlayerG(_height, _width);
		// _player.setParent(this);
	}

	/**
	 * initialize the singleton instance
	 * 
	 * @return DisplayScreen only instance
	 */
	public static DisplayScreen init() {
		if (singleton == null)
			singleton = new DisplayScreen();
		return singleton;
	}

	/**
	 * get the Singleton instance
	 * 
	 * @return DisplayScreen only instance
	 */
	public static DisplayScreen getInstance() {
		return singleton;
	}

	/**
	 * copy the charachter from game ( if exists) to the display screen
	 */
	public static void copyToDisplay() {
		if (GameStats.getPlayers() != null && GameStats.getPlayers().size() != 0
				&& GameStats.getPlayers().getFirst() instanceof Player)
			_player.copyPlayerG(((Player) GameStats.getPlayers().get(0)).getPlayerGraphics());
		_player.setParent(singleton);
		_player.setAllSizes(_height, _width);
	}

	/**
	 * The paint component calls the Paint Component of the PlayerG
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		_player.myPaintComponent(g);
	}

	/**
	 * @return the graphics for the player
	 */
	public PlayerG getPlayer() {
		return _player;
	}

}
