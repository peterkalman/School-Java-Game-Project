package unit.customizeScreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainInitialize.GameStats;
import mainInitialize.MainMenu;
import unit.player.Player;
import unit.player.PlayerG;

/**
 * CustomizeScreen represents a Panel on the CustomizeFrame Contains buttons and category of weapons
 * 
 * @author Peter
 */
public class CustomizeScreen extends JPanel {
	private static CustomizeScreen singleton = null;
	private String _nameList[] = { "Headgear", "Hair", "Face", "Body", "Top", "Weapon", "Bottom" };
	private String _weaponTypes[] = { "Energy", "Heavy", "Melee", "Pistols", "Rifles", "Shotguns", "SMGs" };
	private LinkedList<OptimizingButton> _nextButtons;
	private JPanel _nextPanel;
	private LinkedList<OptimizingButton> _prevButtons;
	private JPanel _prevPanel;
	private JPanel _bottomPanel;
	private JButton _backButton;
	private JComboBox<String> _weaponSelector;

	/**
	 * Constructor prepares all buttons and appearal sets
	 */
	private CustomizeScreen() {
		DisplayScreen.init();
		setBackground(Color.black);
		_weaponSelector = new JComboBox<String>(_weaponTypes);
		_weaponSelector.setSelectedIndex(4);
		_weaponSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DisplayScreen.getInstance().getPlayer().getCustomize().fillAllApparels();
				DisplayScreen.getInstance().getPlayer().getCustomize().getWeapon().resetCurrent();
				DisplayScreen.getInstance().getPlayer().setImgSide(0);
				DisplayScreen.getInstance().getPlayer().setAllSizes(DisplayScreen._height, DisplayScreen._width);
				DisplayScreen.getInstance().repaint();
			}
		});

		_nextPanel = new JPanel();
		_prevPanel = new JPanel();
		_bottomPanel = new JPanel();
		_nextPanel.setOpaque(false);
		_prevPanel.setOpaque(false);
		_bottomPanel.setOpaque(false);
		// setOpaque(false);
		// _displayUnitScreen.setBackground(Color.black);
		_nextButtons = new LinkedList<OptimizingButton>();
		_prevButtons = new LinkedList<OptimizingButton>();
		setLayout(new BorderLayout());
		_nextPanel.setLayout(new GridLayout(8, 1, 10, 10));

		for (int i = 0; i < _nameList.length; i++) {
			_nextButtons.add(new OptimizingButton(_nameList[i], true));
			setButtonListener(_nextButtons.get(i));
			_nextPanel.add(_nextButtons.get(i).getBtn());
		}
		_prevPanel.setLayout(new GridLayout(8, 1, 10, 10));
		for (int i = 0; i < _nameList.length; i++) {
			_prevButtons.add(new OptimizingButton(_nameList[i], false));
			setButtonListener(_prevButtons.get(i));
			_prevPanel.add(_prevButtons.get(i).getBtn());
		}
		_bottomPanel.setLayout(new GridLayout(2, 2));
		_bottomPanel.add(new JLabel("WEAPON:"));

		_bottomPanel.add(_weaponSelector);

		_backButton = new JButton("BACK");
		_backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MainMenu.getInstance() != null)
					MainMenu.getInstance().setVisible(true);
				CustomizeFrame.getInstance().setVisible(false);
			}
		});
		_bottomPanel.add(_backButton);
		add(_nextPanel, BorderLayout.EAST);
		add(_prevPanel, BorderLayout.WEST);
		add(_bottomPanel, BorderLayout.SOUTH);
		add(DisplayScreen.getInstance(), BorderLayout.CENTER);
	}

	/**
	 * initialize the singleton instance
	 * 
	 * @return CustomizeScreen only instance
	 */
	public static CustomizeScreen init() {
		if (singleton == null)
			singleton = new CustomizeScreen();
		return singleton;
	}

	/**
	 * get the Singleton instance
	 * 
	 * @return CustomizeScreen only instance
	 */
	public static CustomizeScreen getInstance() {
		return singleton;
	}

	/**
	 * sets the button to listener for clicks
	 * 
	 * @param optBtn
	 *            -the button we want to set as listener
	 */
	public void setButtonListener(OptimizingButton optBtn) {
		optBtn.getBtn().addActionListener(optBtn);
	}

	/**
	 * @return the name of the weapon type selected
	 */
	public String getWeaponSelected() {
		return _weaponTypes[_weaponSelector.getSelectedIndex()];
	}

	/**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { CustomizeFrame frame = CustomizeFrame.init(); }
	 */

}
