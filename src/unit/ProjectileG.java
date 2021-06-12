package unit;

import images.Img;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import ultilityTools.ImageTools;
import ultilityTools.PaintingInterface;

/**
 * ProjectileG is a class that represents the graphic class of a projectile
 * 
 * @author Peter
 */
public class ProjectileG implements PaintingInterface {
	private Img _projectileImg;
	private int _width;
	private int _height;
	private double _angle;
	private double _directionAngle;

	/**
	 * Constructor
	 * 
	 * @param width
	 *            - the width of the projectile (in pixels)
	 * @param height
	 *            - the height of the projectile (in pixels)
	 * @param angle
	 *            - the angle that indicates to which direction to turn the projectile
	 * @param directionAngle
	 *            - the angle of the cone
	 */
	public ProjectileG(int width, int height, double angle, double directionAngle) {
		copyProjectile(width, height, angle, directionAngle);
		_projectileImg = new Img("Pawns\\Weapons\\Animations\\Bullet.png", 0, 0, _width, _height);
		_projectileImg.setImage(ImageTools.rotate(_projectileImg, _angle));
		// _projectileImg.setImage(ImageTools.rotate(_projectileImg, _angle));
	}

	/**
	 * sets the variables with the received parameters
	 * 
	 * @param width
	 *            - the width of the projectile (in pixels)
	 * @param height
	 *            - the height of the projectile (in pixels)
	 * @param angle
	 *            - the angle that indicates to which direction to turn the projectile
	 * @param directionAngle
	 *            - the angle of the cone
	 */
	public void copyProjectile(int width, int height, double angle, double directionAngle) {
		_width = width;
		_height = height;
		_angle = angle;
		_directionAngle = directionAngle;
	}
	/**
	 * the PaintComponent of the img
	 */
	@Override
	public void myPaintComponent(Graphics g) {
		// _projectileImg.drawImgRotate(g, _angle);
		// g.setColor(Color.blue);
		// g.fillRect(0, 0, _width, _height);
		_projectileImg.drawImg(g);
	}
}
