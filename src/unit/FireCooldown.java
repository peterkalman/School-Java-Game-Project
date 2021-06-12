package unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Firecooldown is a class that is meant to be the function of a timer which would restrict the unit
 * to use weapons
 * 
 * @author Peter
 */
public class FireCooldown implements ActionListener {
	private int _fireCooldown = 0;
	/**
	 * the action that is done whenever a timer ticks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (_fireCooldown > 0)
			_fireCooldown--;

	}
	/**
	 * @return the fireCooldown countdown
	 */
	public int getFireCooldown() {
		return _fireCooldown;
	}
	/**
	 * set the fire cooldown for the countdown 
	 * @param fireCooldown - the new fire cooldown
	 */
	public void setFireCooldown(int fireCooldown) {
		_fireCooldown = fireCooldown;
	};

}
