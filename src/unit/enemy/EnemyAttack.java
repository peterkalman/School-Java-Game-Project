package unit.enemy;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import engine.Block;
import engine.Unit;
import mainInitialize.GameStats;
import map.MapGPanel;
import unit.MovingUnit;
import unit.ObjectOnMap;
import unit.ProjectileG;
import unit.player.Player;
import unit.player.PlayerG;

/**
 * EnemyAttack is a class that represents the melee attack of an enemy
 * 
 * @author Peter
 */
public class EnemyAttack extends ObjectOnMap implements ActionListener, MeleeAttackInterface {
	private Timer _timer;
	private EnemyAttackG _graphics;
	private int _attackDamage;
	private double _directionAngle;
	private Enemy _parent;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            - the starting X position (in Pixels)
	 * @param y
	 *            - the starting Y position (in Pixels)
	 * @param width
	 *            - the width of the Enemy Attack panel (in Pixels)
	 * @param height
	 *            - the height of the Enemy Attack panel (in Pixels)
	 * @param attackDamage
	 *            - the attack damage of the attack
	 * @param directionAngle
	 *            - the direction of the attack in degrees
	 * @param parent
	 *            - the parent Panel of the attack
	 */
	public EnemyAttack(int x, int y, int width, int height, int attackDamage, double directionAngle, Enemy parent) {
		super(x, y, width, height);
		_parent = parent;
		_parent.setAttacking(true);
		MapGPanel.getInstance().add(this);
		_attackDamage = attackDamage;
		_directionAngle = directionAngle;
		_timer = new Timer(200, this);// 200
		_graphics = new EnemyAttackG(getObjectWidth(), getObjectHeight(), _directionAngle, this);
		setObjectGraphicClass(_graphics);
		_timer.start();
	}
	/**
	 * activates whenever a timer ticks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		actionIfHalfWall();
		actionIfPlayer();
		_graphics.nextAttackImg();
		repaint();
	}
	/**
	 * do action if collides with halfwall
	 */
	public void actionIfHalfWall() {
		for (Block temp : GameStats.getHalfWalls()) {
			ObjectOnMap tempObj = new ObjectOnMap(temp.getX() * temp.getWidth(), temp.getY() * temp.getHeight(),
					temp.getWidth(), temp.getHeight());
			if (isColliding(tempObj)) {
				// System.out.println("I am eating" +temp.getX()+":"+temp.getY());
				temp.setCurrentHealth(temp.getCurrentHealth() - _attackDamage);
				/*
				 * System.out.println("Hp left " + temp.getCurrentHealth());
				 * System.out.println(_bulletHp);
				 */
				return;
			}
		}
	}
	/**
	 * do action if collides with player
	 */
	public void actionIfPlayer() {
		for (MovingUnit temp : GameStats.getPlayers()) {
			if (temp instanceof Player) {
				Unit tempPlayerStats = ((Player) temp).getStats();
				ObjectOnMap tempObj = new ObjectOnMap(temp.getRealX(), temp.getRealY(), temp.getRealWidth(),
						temp.getRealHeight());
				if (isColliding(tempObj)) {
					tempPlayerStats.setHealth(tempPlayerStats.getHealth() - _attackDamage);
					// System.out.println("I am eating player at" + ((Player)temp).getRealX() + " :
					// " + ((Player)temp).getRealY());
					// System.out.println("Player HP:" + ((Player) temp).getStats().getHealth());
				}
			}
		}
	}
	/**
	 * removes the enemy attack from the map when notified about animation ending.
	 */
	@Override
	public void meleeAttackFinished() {
		setObjectGraphicClass(null);
		repaint();
		_timer.stop();
		_timer.removeActionListener(this);
		MapGPanel.getInstance().remove(this);
		_parent.setAttacking(false);
		_parent = null;
	}
}
