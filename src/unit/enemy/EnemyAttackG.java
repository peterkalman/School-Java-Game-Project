package unit.enemy;

import java.awt.Graphics;

import images.Img;
import ultilityTools.ImageTools;
import ultilityTools.PaintingInterface;
/**
 * EnemyAttackG is a class that represents the graphics of an Enemy attack
 * @author Peter
 *
 */
public class EnemyAttackG implements PaintingInterface {
	private Img _attackImg;
	private int _width;
	private int _height;
	private String[] _pathArr = { "Pawns\\Weapons\\Animations\\Melee_Combat1.png",
			"Pawns\\Weapons\\Animations\\Melee_Combat2.png", "Pawns\\Weapons\\Animations\\Melee_Combat3.png",
			"Pawns\\Weapons\\Animations\\Melee_Combat4.png", "Pawns\\Weapons\\Animations\\Melee_Combat5.png" };
	private int _turn = 0;
	private double _angle;
	private MeleeAttackInterface _listener;
	/**
	 * Constructor
	 * @param width - the width of the attack (in pixels)
	 * @param height - the height of the attack (in pixels)
	 * @param angle - the angle of direction of the attack
	 * @param listener - the EnemyAttack that listens for the end of animation
	 */
	public EnemyAttackG(int width, int height, double angle, MeleeAttackInterface listener) {
		_listener = listener;
		_width = width;
		_height = height;
		_angle = angle;
		_attackImg = new Img();
		// _attackImg.setImage(_pathArr[0]);
		_attackImg.setImgCords(0, 0);
		_attackImg.setImgSize(_width, _height);
		// _attackImg.setImage(ImageTools.rotate(_attackImg, _angle));

	}
	/**
	 * get the next img panel or if finished notify the listener about it
	 */
	public void nextAttackImg() {
		if (_turn >= _pathArr.length) {
			_listener.meleeAttackFinished();
		} else {
			_attackImg.setImage(_pathArr[_turn++]);
			_attackImg.setImage(ImageTools.rotate(_attackImg, _angle));
		}

	}
	/**
	 * paint component for the attackImg
	 */
	@Override
	public void myPaintComponent(Graphics g) {
		_attackImg.drawImg(g);
	}

}
