package unit.customizeScreen;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import unit.player.PlayerG;

/**
 * CustomizeFrame is a class that represents a frame with Player custom options
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class CustomizeFrame extends JFrame {
	private static CustomizeFrame singleton = null;
	/**
	 * Constructor inits the Customize panel and shows it to the user
	 */
	private CustomizeFrame() {

		add(CustomizeScreen.init());
		setVisible(true);
		setSize(600, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2,338);
	}
	/**
	 * initialize the singleton instance
	 * 
	 * @return CustomizeFrame only instance
	 */
	public static CustomizeFrame init() {
		if (singleton == null)
			singleton = new CustomizeFrame();
		return singleton;
	}
	/**
	 * get the Singleton instance
	 * 
	 * @return CustomizeFrame only instance
	 */
	public static CustomizeFrame getInstance() {
		return singleton;
	}
}
