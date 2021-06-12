package mainInitialize;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import engine.Unit;

/**
 * MainMenu is a class that represents a main menu screen ,extends JFrame because it is frame
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class MainMenu extends JFrame {
	private static MainMenu singleton = null;
	private Unit Units[];

	private BackgroundImgPanel _backGroundPanel;

	private JLabel _gifPlayer;
	private ImageIcon _imgIcon;

	/**
	 * constructor is private because of singleton, puts the first gif and starts backgroundImgPanel
	 */
	private MainMenu() {
		
		singleton = this;

		_imgIcon = new ImageIcon(this.getClass().getClassLoader().getResource("Menu\\scene1.gif"));
		_gifPlayer = new JLabel(_imgIcon);
		_gifPlayer.setBounds(0, 0, 600, 338);
		_imgIcon.setImageObserver(_gifPlayer);

		add(_gifPlayer, BorderLayout.WEST);
		add(BackgroundImgPanel.getInstance());

		setResizable(false);
		setVisible(true);
		setSize(605, 338);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * @return the JLabel that plays the gifs
	 */
	public JLabel getGifPlayer() {
		return _gifPlayer;
	}

	/**
	 * initialize the singleton instance
	 * 
	 * @return MainMenu only instance
	 */
	public static MainMenu init() {
		if (singleton == null)
			singleton = new MainMenu();
		return singleton;
	}

	/**
	 * get the Singleton instance
	 * 
	 * @return MainMenu only instance
	 */
	public static MainMenu getInstance() {
		return singleton;
	}

}
