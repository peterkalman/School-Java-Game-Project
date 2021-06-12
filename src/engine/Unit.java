package engine;

import unit.MovingUnit;
import unit.WinningInterface;

/**
 * Unit is a class that represents the current stats of the unit
 * 
 * @author Peter
 */
public class Unit {
	private int _maxHealth;
	private char _unitType;
	private int _health;
	private int _startingX;
	private int _startingY;
	private int _currentX;
	private int _currentY;

	private MovingUnit _listener;

	/**
	 * Consturctor
	 * 
	 * @param unitType
	 *            - the type of unit (P/E)
	 * @param startX
	 *            - the starting X position (in position units)
	 * @param startY
	 *            - the starting Y position (in position units)
	 * @param health
	 *            - the health of the unit
	 * @param listener
	 *            - the panel of the unit (that listens)
	 */
	public Unit(char unitType, int startX, int startY, int health, MovingUnit listener) {
		_listener = listener;
		_unitType = unitType;
		_health = _maxHealth = health;
		_startingX = startX;
		_currentX = _startingX;
		_startingY = startY;
		_currentY = _startingY;
	}

	/**
	 * @return the type of the unit
	 */
	public char getUnitType() {
		return _unitType;
	}

	/**
	 * set the type of the unit
	 * 
	 * @param unitType
	 *            - the new unitType
	 */
	public void setUnitType(char unitType) {
		_unitType = unitType;
	}

	/**
	 * @return the health of the unit
	 */
	public int getHealth() {
		return _health;
	}
	
	public int getMaxHealth() {
		return _maxHealth;
	}

	/**
	 * set the health of the unit , if health is set to zero or below remove notify listener
	 * 
	 * @param health
	 *            - the new health of the unit
	 */
	public void setHealth(int health) {
		if (health <= 0)
			_listener.removeFromMap();
		_health = health;
	}

	/**
	 * @return the starting X coordinate of the unit (in position units)
	 */
	public int getStartingX() {
		return _startingX;
	}

	/**
	 * set the starting X coordinate of the unit (in position units)
	 * 
	 * @param startingX
	 */
	public void setStartingX(int startingX) {
		_startingX = startingX;
	}

	/**
	 * @return the starting Y coordinate of the unit (in position units)
	 */
	public int getStartingY() {
		return _startingY;
	}

	/**
	 * set the starting Y coordinate of the unit (in position units)
	 * 
	 * @param startingY
	 */
	public void setStartingY(int startingY) {
		_startingY = startingY;
	}

	/**
	 * @return the current X coordinate of the unit (in position units)
	 */
	public int getCurrentX() {
		return _currentX;
	}

	/**
	 * set the current X coordinate of the unit (in position units)
	 * 
	 * @param currentX
	 */
	public void setCurrentX(int currentX) {
		_currentX = currentX;
	}

	/**
	 * @return the current Y coordinate of the unit (in position units)
	 */
	public int getCurrentY() {
		return _currentY;
	}

	/**
	 * set the current Y coordinate of the unit (in position units)
	 * 
	 * @param currentY
	 */
	public void setCurrentY(int currentY) {
		_currentY = currentY;
	}

	/**
	 * return a string containing the unit type
	 */
	public String toString() {
		return "Unit " + _unitType;
	}
}
