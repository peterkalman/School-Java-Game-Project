package unit.customizeScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import unit.player.PlayerG;

/**
 * OptimizingButton is a class containing a button and a listener ( PlayerG) that gets alerted
 * whenever a button the button is pressed
 * 
 * @author Peter
 */
public class OptimizingButton implements ActionListener {
	private JButton _btn;
	private String _name;
	private boolean _isNext;
	private PlayerG _listener;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            - the label of the button
	 * @param isNext
	 *            - is it a reverse button ( next or back)
	 */
	public OptimizingButton(String name, boolean isNext) {
		_listener = DisplayScreen.getInstance().getPlayer();
		_isNext = isNext;
		_name = new String(name);
		_btn = new JButton(_name);
	}

	/**
	 * Gets called whenever the button is clicked, notifies the Listener about the click
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * if (_isNext) { System.out.println("Item Next : " + _name); } else {
		 * System.out.println("Item Prev : " + _name); }
		 */
		_listener.CustomizeButtonClicked(_name, _isNext);
	}

	/**
	 * @return the label of the button
	 */
	public String getName() {
		return _name;
	}

	/**
	 * set a new Label for the button
	 * 
	 * @param name
	 *            - the new label for the button
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * @return the button in this class
	 */
	public JButton getBtn() {
		return _btn;
	}

}
