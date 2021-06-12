package unit;

import javax.swing.Timer;

import mainInitialize.GameStats;
import map.MapG;
import map.MapGPanel;
import unit.enemy.Enemy;
import unit.player.Player;
import unit.player.PlayerG;

/**
 * MovingUnit is a class that represents a moving panel
 * 
 * @author Peter
 */
public abstract class MovingUnit extends ObjectOnMap {

	/** the index of the current action ,16 is end */
	protected int _i = 0;
	protected Timer _fireTimer;
	protected FireCooldown _fireListener;
	/** the timer that does action */
	protected Timer _timer;

	public MovingUnit(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * moveLeft animation uses _i as progress does diffrent animations based on isReturning
	 * 
	 * @param isReturning
	 *            - if true the animation will be a bump else the animation would be a block
	 *            movement
	 */
	public void moveLeft(boolean isReturning) {
		if (!isReturning) {
			if (_i < 16) {
				setObjectX(getObjectX() - 4);
			}
			_i++;
		} else {
			if (_i < 2)
				setObjectX(getObjectX() - 4);
			else if (_i < 4)
				setObjectX(getObjectX() + 4);
			if (++_i == 4) {
				_i = 16;
			}
		}
	}

	/**
	 * moveRight animation uses _i as progress does diffrent animations based on isReturning
	 * 
	 * @param isReturning
	 *            - if true the animation will be a bump else the animation would be a block
	 *            movement
	 */
	public void moveRight(boolean isReturning) {
		if (!isReturning) {
			if (_i < 16) {
				setObjectX(getObjectX() + 4);
			}
			_i++;
		} else {
			if (_i < 2)
				setObjectX(getObjectX() + 4);
			else if (_i < 4)
				setObjectX(getObjectX() - 4);
			if (++_i == 4) {
				_i = 16;
			}
		}
	}

	/**
	 * moveUp animation uses _i as progress does diffrent animations based on isReturning
	 * 
	 * @param isReturning
	 *            - if true the animation will be a bump else the animation would be a block
	 *            movement
	 */
	public void moveUp(boolean isReturning) {
		if (!isReturning) {
			if (_i < 16) {
				setObjectY(getObjectY() - 4);
			}
			_i++;
		} else {
			if (_i < 2)
				setObjectY(getObjectY() - 4);
			else if (_i < 4)
				setObjectY(getObjectY() + 4);
			if (++_i == 4) {
				_i = 16;
			}
		}
	}

	/**
	 * moveDown animation uses _i as progress does diffrent animations based on isReturning
	 * 
	 * @param isReturning
	 *            - if true the animation will be a bump else the animation would be a block
	 *            movement
	 */
	public void moveDown(boolean isReturning) {
		if (!isReturning) {
			if (_i < 16) {
				setObjectY(getObjectY() + 4);
			}
			_i++;
		} else {
			if (_i < 2)
				setObjectY(getObjectY() + 4);
			else if (_i < 4)
				setObjectY(getObjectY() - 4);
			if (++_i == 4) {
				_i = 16;
			}
		}
	}

	/**
	 * Attack function of the movingUnit ( ranged or melee)
	 */
	public abstract void attackRight();

	/**
	 * Attack function of the movingUnit ( ranged or melee)
	 */
	public abstract void attackLeft();

	/**
	 * Attack function of the movingUnit ( ranged or melee)
	 */
	public abstract void attackUp();

	/**
	 * Attack function of the movingUnit ( ranged or melee)
	 */
	public abstract void attackDown();

	/**
	 * get the no padding X coordinate
	 * 
	 * @return the X position ( in pixels)
	 */
	public abstract int getRealX();

	/**
	 * get the no padding Y coordinate
	 * 
	 * @return the Y position (in pixels)
	 */
	public abstract int getRealY();

	/**
	 * get the no padding height
	 * 
	 * @return - the height of the Panel without padding(in pixels)
	 */
	public abstract int getRealHeight();

	/**
	 * get the no padding width
	 * 
	 * @return - the width of the Panel without padding(in pixels)
	 */
	public abstract int getRealWidth();
	
	public abstract float getHealthPoints();
	
	/**
	 * Check if a certain place is blocked by other movingUnits or walls
	 * 
	 * @param i
	 *            - the I coordinate ( in position units)
	 * @param j
	 *            - the J coordinate ( in position units)
	 * @return
	 */
	public boolean isBlocked(int i, int j) {
		boolean flag = (MapG.get_map()[i][j].getWallLevel() != 0);
		flag = flag || GameStats.occupiedExists(i, j);
		return flag;
	}

	/**
	 * remove the moving unit from the Map and remove the global Unit List, stop all timers and
	 * nullify the graphic class
	 */
	public void removeFromMap() {
		// _timer.removeActionListener(this);
		MapGPanel.getInstance().remove(this);
		GameStats.getPlayers().remove(this);

		_timer.stop();
		try {
			_fireTimer.stop();
		} catch (NullPointerException e) {
		}
		;
		setObjectGraphicClass(null);

	}
}
