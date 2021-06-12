package unit.enemy;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Unit;
import engine.dijekstra.SmartMove;
import engine.input.KeyboardInput;
import mainInitialize.GameStats;
import map.BrieferScreen;
import map.MapG;
import map.MapGFrame;
import map.MapGPanel;
import ultilityTools.PaintingInterface;
import unit.FireCooldown;
import unit.MovingUnit;
import unit.WinningInterface;
import unit.ObjectOnMap;
import unit.Projectile;
import unit.player.Player;
import unit.player.PlayerG;

/**
 * The Enemy class is a class that represents the enemy panel
 * 
 * @author Peter
 */
public class Enemy extends MovingUnit implements ActionListener, WinningInterface {
	/** the stats of the enemy */
	private Unit _stats;
	/** the graphic class of the enemy */
	private EnemyG _graphics;
	/** if the enemy is busy doing one of the actions the flag will be active */
	private boolean _rightBusy = false;
	private boolean _leftBusy = false;
	private boolean _upBusy = false;
	private boolean _downBusy = false;
	private boolean _busy = false;
	private boolean _isAttacking = false;
	private boolean _isBlocked;
	private int _opt;

	private int _damageToWall = 20;
	public static int _timerTicker=28;
	private SmartMove _mover;
	private Unit _hunted;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            - the graphical X position of the enemy
	 * @param y
	 *            - the graphical Y position of the enemy
	 * @param width
	 *            - the width of the enemy
	 * @param height
	 *            - the height of the enemy
	 * @param map
	 *            - the Graphical map
	 */
	public Enemy(int x, int y, int width, int height) {
		/** use Object's on map constructor */
		super(x, y, width, height);
		/** initialize stats */
		_stats = new Unit('E', x / 64, y / 64, 500, this);
		/** create new Graphics */
		_graphics = new EnemyG(_height, _width);
		setObjectGraphicClass(_graphics);
		/** the action timer */
		_timer = new Timer(_timerTicker, this);// 40
		_timer.start();
		_mover = new SmartMove();
		_hunted = ((Player) (GameStats.getPlayers().get(0))).getStats();
	}

	/**
	 * The action that is done after every tick checks if the enemy won , checks if he is busy if
	 * not check if he needs to attack
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		actionIfWin();
		// //System.out.println("Hunted Coordinates: "+ _hunted.getCurrentX() +" , " +
		// _hunted.getCurrentY());
		if (!_leftBusy && !_rightBusy && !_upBusy && !_downBusy)
			_opt = _mover.getSmartMove(_stats, _hunted.getCurrentX(), _hunted.getCurrentY());

		/**
		 * if 'A' is held and none of the actions are done or the left action is currently busy
		 */
		if ((_opt == 3 && !_busy) || (_leftBusy && _busy)) {
			_leftBusy = true;
			_busy = true;
			if (_i == 0) {
				_isBlocked = isBlocked(_stats.getCurrentX() - 1, _stats.getCurrentY());
				/** if the location player moves is floor */
				if (!_isBlocked)
					_stats.setCurrentX(_stats.getCurrentX() - 1);
				GameStats.addOccupied(_stats.getCurrentX(), _stats.getCurrentY());
			}
			/** Move left ,receives boolean that tells which animation cycle to do */
			if (_isBlocked)
				if (!_isAttacking)
					attackLeft();
				else
					moveLeft(true);
			else
				moveLeft(false);

			/** set direction in order to draw Properly */
			_graphics.setImgSide(3);

			/** after finishing cycle */
			if (_i == 16) {
				if (!_isBlocked)
					GameStats.removeOccupied(_stats.getCurrentX() + 1, _stats.getCurrentY());
				/** reset the _i and free the busy flags */
				_i = 0;
				_leftBusy = false;
				_busy = false;
				_isBlocked = false;
			}
			// repaint();
			return;
		}
		/**
		 * if 'D' is held and none of the actions are done or the right action is currently busy
		 */
		if (((_opt == 1 && !_busy)) || (_rightBusy && _busy)) {
			_rightBusy = true;
			_busy = true;
			if (_i == 0) {
				_isBlocked = isBlocked(_stats.getCurrentX() + 1, _stats.getCurrentY());
				/** if the location player moves is floor */
				if (!_isBlocked)
					_stats.setCurrentX(_stats.getCurrentX() + 1);
				GameStats.addOccupied(_stats.getCurrentX(), _stats.getCurrentY());
			}
			/** Move right ,receives boolean that tells which animation cycle to do */
			if (_isBlocked)
				if (!_isAttacking)
					attackRight();
				else
					moveRight(true);
			else
				moveRight(false);

			/** set direction in order to draw Properly */
			_graphics.setImgSide(1);

			/** after finishing cycle */
			if (_i == 16) {
				if (!_isBlocked)
					GameStats.removeOccupied(_stats.getCurrentX() - 1, _stats.getCurrentY());
				/** reset the _i and free the busy flags */
				_i = 0;
				_rightBusy = false;
				_busy = false;
				_isBlocked = false;
			}
			// repaint();
			return;
		}
		/**
		 * if 'W' is held and none of the actions are done or the up action is currently busy
		 */
		if (((_opt == 2 && !_busy)) || (_upBusy && _busy)) {
			_upBusy = true;
			_busy = true;
			/** Move up , receives boolean that tells which animation cycle to do */
			if (_i == 0) {
				_isBlocked = isBlocked(_stats.getCurrentX(), _stats.getCurrentY() - 1);
				/** if the location player moves is floor */
				if (!_isBlocked)
					_stats.setCurrentY(_stats.getCurrentY() - 1);
				GameStats.addOccupied(_stats.getCurrentX(), _stats.getCurrentY());
			}
			if (_isBlocked)// MapG.get_map()[_stats.getCurrentX()][_stats.getCurrentY()
				// - 1].getWallLevel()
				// != 0)
				/** if not in middle of attack */
				if (!_isAttacking)
					attackUp();
				else
					moveUp(true);
			else
				moveUp(false);

			/** set direction in order to draw Properly */
			_graphics.setImgSide(2);

			/** after finishing cycle */
			if (_i == 16) {
				if (!_isBlocked)
					GameStats.removeOccupied(_stats.getCurrentX(), _stats.getCurrentY() + 1);
				/** reset the _i and free the busy flags */
				_i = 0;
				_upBusy = false;
				_busy = false;
				_isBlocked = false;
			}
			// repaint();
			return;
		}
		/**
		 * if 'S' is held and none of the actions are done or the down action is currently busy
		 */
		if (((_opt == 0 && !_busy)) || (_downBusy && _busy)) {

			_downBusy = true;
			_busy = true;
			if (_i == 0) {
				_isBlocked = isBlocked(_stats.getCurrentX(), _stats.getCurrentY() + 1);
				if (!_isBlocked)
					/** if the location player moves is floor */
					_stats.setCurrentY(_stats.getCurrentY() + 1);
				GameStats.addOccupied(_stats.getCurrentX(), _stats.getCurrentY());
			}
			/** Move down , receives boolean that tells which animation cycle to do */
			if (_isBlocked)// MapG.get_map()[_stats.getCurrentX()][_stats.getCurrentY()
				// + 1].getWallLevel()
				// != 0)
				if (!_isAttacking)
					attackDown();
				else
					moveDown(true);
			else
				moveDown(false);

			/** set direction in order to draw Properly */
			_graphics.setImgSide(0);

			/** after finishing cycle */
			if (_i == 16) {
				if (!_isBlocked)
					GameStats.removeOccupied(_stats.getCurrentX(), _stats.getCurrentY() - 1);
				/** reset the _i and free the busy flags */
				_i = 0;
				_downBusy = false;
				_busy = false;
				_isBlocked = false;
			}
			// repaint();
			return;
		}

		if (_opt != 0) {
			_graphics.setImgSide(0);
			repaint();
		}

	}

	/**
	 * set the new isAttacking flag which meansif the enemy is attacking at the moment
	 * 
	 * @param isAttacking
	 *            - the new is Attacking flag of the enemy
	 */
	public void setAttacking(boolean isAttacking) {
		_isAttacking = isAttacking;
	}

	/**
	 * @return the damage a single attack of the enemy does to a wall
	 */
	public int getDamageToWall() {
		return _damageToWall;
	}

	/**
	 * notifies the user if the enemy has won
	 */
	public void actionIfWin() {
		if (isWin()) {
			GameStats.pauseGame();
			JDialog jd = new JDialog();
			GameStats.enemyWonInc();
			GameStats.decreaseDifficaulty();
			jd.add(BrieferScreen.init(false,jd));
			jd.pack();
			jd.setVisible(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			jd.setLocation(dim.width / 2 - jd.getSize().width / 2, 0);
			/*GameStats.restartGame(false);
			GameStats.pauseGame();*/
		}
	}

	/**
	 * checks if there are no players left if true the enemy has won .
	 */
	public boolean isWin() {
		for (MovingUnit temp : GameStats.getPlayers()) {
			if (temp instanceof Player)
				return false;
		}
		return true;// (getObjectX() == (7 * 64) && getObjectY() == (7 * 64));
	}

	/**
	 * pauses the unit , used for pausing the game
	 */
	public void pauseUnit() {
		_timer.stop();
		if (_fireTimer != null)
			_fireTimer.stop();// enemy doesnt use fire timer now .
	}

	/**
	 * starts the unit , used for continueing the game
	 */
	public void startUnit() {
		_timer.start();
		// _fireTimer.start();
	}

	/**
	 * start to attack the right direction
	 */
	@Override
	public void attackRight() {
		new EnemyAttack(getObjectX() + 64, getObjectY(), 64, 64, _damageToWall, 90, this);
	}

	/**
	 * start to attack the left direction
	 */
	@Override
	public void attackLeft() {
		new EnemyAttack(getObjectX() - 64, getObjectY(), 64, 64, _damageToWall, 270, this);

	}

	/**
	 * start to attack the upper direction
	 */
	@Override
	public void attackUp() {
		new EnemyAttack(getObjectX(), getObjectY() - 64, 64, 64, _damageToWall, 180, this);
	}

	/**
	 * start to attack the bottom direction
	 */
	@Override
	public void attackDown() {
		new EnemyAttack(getObjectX(), getObjectY() + 64, 64, 64, _damageToWall, 180, this);
	}

	/**
	 * @returns the ObjectX without the padding ( in pixels)
	 */
	@Override
	public int getRealX() {
		return getObjectX();
	}

	/**
	 * @returns the ObjectY without the padding ( in pixels)
	 */
	@Override
	public int getRealY() {
		return getObjectY();
	}
	/**
	 * @returns the Object Height without the padding ( in pixels)
	 */
	@Override
	public int getRealHeight() {
		return getObjectHeight();
	}
	/**
	 * @returns the Object Width without the padding ( in pixels)
	 */
	@Override
	public int getRealWidth() {
		return getObjectWidth();
	}
	/**
	 * @return the Enemy stats
	 */
	public Unit getStats() {
		return _stats;
	}

	@Override
	public float getHealthPoints() {
		return _stats.getHealth()/((float)_stats.getMaxHealth());
	}

}
