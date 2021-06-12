package unit.player;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Unit;
import engine.input.KeyboardInput;
import mainInitialize.GameStats;
import map.BrieferScreen;
import map.MapG;
import map.MapGFrame;
import map.MapGPanel;
import unit.FireCooldown;
import unit.MovingUnit;
import unit.WinningInterface;
import unit.ObjectOnMap;
import unit.Projectile;
import unit.customizeScreen.DisplayScreen;

/**
 * Player represents a player which extends ObjectOnMap because it is on map
 * 
 * @author Peter
 */
public class Player extends MovingUnit implements ActionListener, WinningInterface {
	/** the stats of the player */
	private Unit _stats;
	/** the graphic class of the player */
	private PlayerG _graphics;
	/**
	 * if the player is busy doing one of the actions the flag will be active
	 */
	private boolean _rightBusy = false;
	private boolean _leftBusy = false;
	private boolean _upBusy = false;
	private boolean _downBusy = false;
	private boolean _busy = false;
	public static int _timerTicker=20;
	private boolean _isBlocked;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            - the graphical X position of the player
	 * @param y
	 *            - the graphical Y position of the player
	 * @param width
	 *            - the width of the player
	 * @param height
	 *            - the height of the player
	 * @param map
	 *            - the Graphical map
	 */
	public Player(int x, int y, int width, int height) {
		/** use Object's on map constructor */
		super(x - PlayerG.padding, y - PlayerG.padding, width + PlayerG.padding * 2, height + PlayerG.padding * 2);
		/** initialize stats */
		_stats = new Unit('P', x / 64, y / 64, 100, this);
		/** add player as listener */
		KeyboardInput.getInstance().addListener(this);
		/** set focus on player */
		// MapGFrame.getInstance().setFocusable(true);
		// MapGFrame.getInstance().setFocusTraversalKeysEnabled(false);
		/** create new Graphics */

		setCustomGraphics(height, width);
		/** the action timer */
		_timer = new Timer(_timerTicker, this);
		_timer.start();
		_fireListener = new FireCooldown();
		_fireTimer = new Timer(_timerTicker, _fireListener);
		_fireTimer.start();
	}

	/**
	 * Sets the graphics for the player based on the DisplayScreen ( if it exists) while also
	 * matching the size and setting this panel as parent
	 * 
	 * @param height-
	 *            the height of the Player (in pixels) and sets the size to it
	 * @param width
	 *            - the width of the Player (in pixels) and sets the size to it
	 */
	public void setCustomGraphics(int height, int width) {
		if (DisplayScreen.getInstance() == null || DisplayScreen.getInstance().getPlayer() == null)
			_graphics = new PlayerG(height, width);
		else {
			_graphics = DisplayScreen.getInstance().getPlayer();
		}
		_graphics.setParent(this);
		_graphics.setAllSizes(height, width);
		setObjectGraphicClass(_graphics);
	}

	/**
	 * convert Boolean value to integer value
	 * 
	 * @param bool
	 *            - the boolean value
	 * @return 1 if bool is true else 0
	 */
	public int convertBooleanToInt(boolean bool) {
		return (bool) ? 1 : 0;
	}

	/**
	 * The action that is done after every tick checks if the player won , checks if he is busy if
	 * not checks if he shoots Else check if he moves
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		MapGFrame.getInstance().repaint();
		LinkedList<Boolean> keys = KeyboardInput.getInstance().get_keys();
		actionIfWin();
		if (!_busy) {
			if (keys.get(KeyEvent.VK_RIGHT))
				attackRight();
			else if (keys.get(KeyEvent.VK_LEFT))
				attackLeft();
			else if (keys.get(KeyEvent.VK_UP))
				attackUp();
			else if (keys.get(KeyEvent.VK_DOWN))
				attackDown();
		}
		if (_fireListener.getFireCooldown() == 0) {
			/**
			 * if 'A' is held and none of the actions are done or the left action is currently busy
			 */
			if ((keys.get(KeyEvent.VK_A) && !_busy) || (_leftBusy && _busy)) {
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
				moveLeft(_isBlocked);
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
			if (((keys.get(KeyEvent.VK_D) && !_busy)) || (_rightBusy && _busy)) {
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
				moveRight(_isBlocked);
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
			if (((keys.get(KeyEvent.VK_W) && !_busy)) || (_upBusy && _busy)) {
				_upBusy = true;
				_busy = true;

				if (_i == 0) {
					_isBlocked = isBlocked(_stats.getCurrentX(), _stats.getCurrentY() - 1);
					/** if the location player moves is floor */
					if (!_isBlocked)
						_stats.setCurrentY(_stats.getCurrentY() - 1);
					GameStats.addOccupied(_stats.getCurrentX(), _stats.getCurrentY());
				}

				/** Move up , receives boolean that tells which animation cycle to do */
				moveUp(_isBlocked);
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
			if (((keys.get(KeyEvent.VK_S) && !_busy)) || (_downBusy && _busy)) {

				_downBusy = true;
				_busy = true;

				if (_i == 0) {
					_isBlocked = isBlocked(_stats.getCurrentX(), _stats.getCurrentY() + 1);
					/** if the location player moves is floor */
					if (!_isBlocked)
						_stats.setCurrentY(_stats.getCurrentY() + 1);
					GameStats.addOccupied(_stats.getCurrentX(), _stats.getCurrentY());
				}
				/** Move down , receives boolean that tells which animation cycle to do */
				moveDown(_isBlocked);
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
		}
		if (_graphics.getDirection() != 0) {
			_graphics.setImgSide(0);
			repaint();
		}

	}

	/**
	 * check if the player won , and notify the user about it
	 */
	public void actionIfWin() {
		if (isWin()) {
			GameStats.pauseGame();
			JDialog jd = new JDialog();
			GameStats.increaseDifficaulty();
			GameStats.playerWonInc();
			jd.add(BrieferScreen.init(true,jd));
			jd.pack();
			jd.setVisible(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			jd.setLocation(dim.width / 2 - jd.getSize().width / 2, 0);
		}
	}

	/**
	 * check if the player is the only one left in the global unit list
	 */
	public boolean isWin() {
		return (GameStats.getPlayers().size() == 1 && GameStats.getPlayers().getFirst() instanceof Player);
	}

	/**
	 * @return player graphics of the player
	 */
	public PlayerG getPlayerGraphics() {
		return _graphics;
	}

	/**
	 * @return Unit Stats of the player
	 */
	public Unit getStats() {
		return _stats;
	}

	/**
	 * pauses the unit , used for pausing the game
	 */
	@Override
	public void pauseUnit() {
		_timer.stop();
		_fireTimer.stop();
	}

	/**
	 * starts the unit, used for continueing the game
	 */
	@Override
	public void startUnit() {
		_timer.start();
		_fireTimer.start();
	}

	/**
	 * launch a projectile to the right direction if the fireCooldown is 0
	 */
	@Override
	public void attackRight() {
		if (_fireListener.getFireCooldown() == 0) {
			//System.out.println("Projectile started");
			_fireListener.setFireCooldown(20);// 10 *20;
			Projectile p = new Projectile(getRealX() + 16, getRealY(), 64, 64, 16, 180, 90.0);
		}
	}

	/**
	 * launch a projectile to the left direction if the fireCooldown is 0
	 */
	@Override
	public void attackLeft() {
		if (_fireListener.getFireCooldown() == 0) {
			//System.out.println("Projectile started");
			_fireListener.setFireCooldown(20);// 10 *20;
			Projectile p = new Projectile(getRealX() - 16, getRealY(), 64, 64, 16, 0, 270.0);
		}
	}

	/**
	 * launch a projectile to the upper direction if the fireCooldown is 0
	 */
	@Override
	public void attackUp() {
		if (_fireListener.getFireCooldown() == 0) {
			//System.out.println("Projectile started");
			_fireListener.setFireCooldown(20);// 10 *20;
			Projectile p = new Projectile(getRealX(), getRealY() - 16, 64, 64, 16, 90, 180.0);
		}
	}

	/**
	 * launch a projectile to the below direction if the fireCooldown is 0
	 */
	@Override
	public void attackDown() {
		if (_fireListener.getFireCooldown() == 0) {
			//System.out.println("Projectile started");
			_fireListener.setFireCooldown(20);// 10 *20;
			Projectile p = new Projectile(getRealX(), getRealY() + 16, 64, 64, 16, 270, 0.0);
		}

	}

	/**
	 * @returns the ObjectX without the padding ( in pixels)
	 */
	@Override
	public int getRealX() {
		return getObjectX() + PlayerG.padding;
	}

	/**
	 * @returns the ObjectY without the padding ( in pixels)
	 */
	@Override
	public int getRealY() {
		return getObjectY() + PlayerG.padding;
	}

	/**
	 * @returns the Object Height without the padding ( in pixels)
	 */
	@Override
	public int getRealHeight() {
		return getObjectHeight() - PlayerG.padding * 2;
	}

	/**
	 * @returns the Object Width without the padding ( in pixels)
	 */
	@Override
	public int getRealWidth() {
		return getObjectWidth() - PlayerG.padding * 2;
	}

	@Override
	public float getHealthPoints() {
		return _stats.getHealth()/((float)_stats.getMaxHealth());
	}

}
