package unit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Block;
import images.Img;
import mainInitialize.GameStats;
import mainInitialize.Main;
import map.MapG;
import map.MapGPanel;
import ultilityTools.PaintingInterface;
import unit.enemy.Enemy;

/**
 * Projectile is a class that represents a bullet (currently only for player)
 * 
 * @author Peter
 */
public class Projectile extends ObjectOnMap implements ActionListener {
	private int _speed;
	private LinkedList<Block> _colidedBlocks;
	private LinkedList<ObjectOnMap> _colidedUnits;
	private double _angle;
	private double _directionAngle;
	private Timer _timer;
	private int _bulletHp;
	private int _bulletDamage;
	private ProjectileG _graphics;
	private int _xMove;
	private int _yMove;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            - the starting X position (in Pixels)
	 * @param y
	 *            - the starting Y position (in Pixels)
	 * @param width
	 *            - the width of the projectile panel
	 * @param height
	 *            - the height of the projectile panel
	 * @param speed
	 *            - the speed of the projectile
	 * @param angle
	 *            - the angle at which to turn the projectile to
	 * @param directionAngle
	 *            - the angle of cone (currently only direct projectiles)
	 */
	public Projectile(int x, int y, int width, int height, int speed, double angle, double directionAngle) {
		super(x, y, width, height);
		_xMove = _yMove = 0;
		MapGPanel.getInstance().add(this);// dont forget to add to panel after creating new panel\
		_colidedBlocks = new LinkedList<Block>();
		_colidedUnits = new LinkedList<ObjectOnMap>();
		// setBackground(Color.gray);
		_bulletHp = 50;
		_bulletDamage = 30;
		_speed = speed;
		_timer = new Timer(97, this);
		_angle = angle;
		_directionAngle = directionAngle;
		calculateNewCordinates();
		_graphics = new ProjectileG(getObjectWidth(), getObjectHeight(), _angle, _directionAngle);
		setObjectGraphicClass(_graphics);
		// //System.out.println(getObjectWidth() + " " + getObjectHeight());
		_timer.start();
	}

	/**
	 * insert the appropriate pixels moved and the height \ width of the Panel to fit the rotated
	 * projectile
	 */
	public void calculateNewCordinates() {
		double sin = Math.abs(Math.sin(Math.toRadians(_angle)));
		double cos = Math.abs(Math.cos(Math.toRadians(_angle)));
		int neww = (int) Math.floor(getObjectWidth() * cos + getObjectHeight() * sin);
		int newh = (int) Math.floor(getObjectHeight() * cos + getObjectWidth() * sin);
		setObjectWidth(neww);
		setObjectHeight(newh);
		_xMove = (int) (Math.sin(Math.toRadians(_directionAngle)) * _speed);
		_yMove = (int) (Math.cos(Math.toRadians(_directionAngle)) * _speed);
		// //System.out.println(_xMove + "" + _yMove);
	}

	/**
	 * is called whenever a timer tick occours, does an action collides with an object ,self
	 * destructs if health reaches 0
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		setObjectX(getObjectX() + _xMove);
		setObjectY(getObjectY() + _yMove);
		_graphics.copyProjectile(getObjectWidth(), getObjectHeight(), _angle, _directionAngle);
		// _bulletHp-=1;
		actionIfCollision();
		// repaint();
		if (_bulletHp <= 0)
			killProjectile();
	}
	/**
	 * the function checks if collides in one of the directions
	 * @param collideObj  -the checked object
	 * @return true if both objects collide
	 */
	public boolean checkProperCollisions(ObjectOnMap collideObj) {
		return ((_xMove > 0 && isCollidingLeft(collideObj)) || (_xMove < 0 && isCollidingRight(collideObj))
				|| (_yMove > 0 && isCollidingUp(collideObj)) || (_yMove < 0 && isCollidingDown(collideObj)));

	}
	/**
	 * do an action is struck by wall , halfwall or Moving unit
	 */
	public void actionIfCollision() {// problem is in here
		ObjectOnMap tempObj;
		for (Block temp : GameStats.getFullWalls()) {
			tempObj = new ObjectOnMap(temp.getX() * temp.getWidth(), temp.getY() * temp.getHeight(), temp.getWidth(),
					temp.getHeight());
			if (checkProperCollisions(tempObj)
					&& (_colidedBlocks.isEmpty() || !_colidedBlocks.getLast().equals(temp))) {
				_colidedBlocks.add(temp);
				_bulletHp = 0;
				return;
			}
		}

		for (Block temp : GameStats.getHalfWalls()) {
			tempObj = new ObjectOnMap(temp.getX() * temp.getWidth(), temp.getY() * temp.getHeight(), temp.getWidth(),
					temp.getHeight());
			if (checkProperCollisions(tempObj)
					&& (_colidedBlocks.isEmpty() || !_colidedBlocks.getLast().equals(temp))) {
				_colidedBlocks.add(temp);
				_bulletHp -= temp.getDefenseVal();
				temp.setCurrentHealth(temp.getCurrentHealth() - _bulletDamage);
				return;
			}
		}
		Enemy enemyTemp = null;
		for (MovingUnit temp : GameStats.getPlayers()) {
			if (temp instanceof Enemy && (_colidedUnits.isEmpty() || !_colidedUnits.getLast().equals(temp))) {
				enemyTemp = (Enemy) temp;
				tempObj = new ObjectOnMap(enemyTemp.getRealX(), enemyTemp.getRealY(), enemyTemp.getRealWidth(),
						enemyTemp.getRealHeight());
				if (checkProperCollisions(tempObj)) {
					_colidedUnits.add(temp);
					enemyTemp.getStats().setHealth(enemyTemp.getStats().getHealth() - _bulletDamage);
					//System.out.println("Enemy HP : " + enemyTemp.getStats().getHealth());
					return;
				}
			}
		}
	}
	/**
	 * destroy the projectile and remove from the map panel
	 */
	public void killProjectile() {
		setObjectGraphicClass(null);
		repaint();
		_timer.stop();
		_timer.removeActionListener(this);
		MapGPanel.getInstance().remove(this);
	}
}
