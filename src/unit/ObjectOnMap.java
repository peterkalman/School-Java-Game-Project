package unit;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import map.MapG;
import map.MapGPanel;

import java.awt.Rectangle;

import ultilityTools.PaintingInterface;

/**
 * ObjectOnMap is a class that represents an object on map that is not part of it.
 * 
 * @author Peter
 */
public class ObjectOnMap extends JPanel {
	protected int _x;
	protected int _y;
	protected int _width;
	protected int _height;
	/** the graphic of the Object */
	protected PaintingInterface _graphicClass = null;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            - the x position
	 * @param y
	 *            - the y position
	 * @param width
	 *            - the width of the object
	 * @param height
	 *            - the height of the object
	 */
	public ObjectOnMap(int x, int y, int width, int height) {

		setOpaque(false);
		setBoundForObject(x, y, width, height);
	}

	/**
	 * Set bounds for the object
	 * 
	 * @param x
	 *            - the x position
	 * @param y
	 *            - the y position
	 * @param width
	 *            - the width of the object
	 * @param height
	 *            - the height of the object
	 */
	public void setBoundForObject(int x, int y, int width, int height) {
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		setBounds(_x, _y, _width, _height);
	}

	/**
	 * the painting method , if the object wants to be drawn it must implement paintingInterface 
	 * @param g - the graphics used
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// System.out.println("Painting");
		if (_graphicClass != null)
			if (_graphicClass instanceof PaintingInterface) {
				//MapGPanel.getInstance().repaint();
				_graphicClass.myPaintComponent(g);
			}
	}

	/**
	 * when the center of one panel is on the border of the other panel
	 * 
	 * @param collideObj the object to check 
	 * @return true if the center of this panel is within the other panel
	 */
	public boolean isColliding(ObjectOnMap collideObj) {
		return (isCollidingHorizontal(collideObj) && isCollidingVertical(collideObj));
	}
	/**
	 * check if the current object touches the checked object from the left or right
	 * @param collideObj - the checked object
	 * @return true if they collide else false
	 */
	public boolean isCollidingHorizontal(ObjectOnMap collideObj) {
		return (isCollidingLeft(collideObj) && isCollidingRight(collideObj));
	}
	/**
	 * check if the current object touches the checked object from the top or bottom 
	 * @param collideObj - the checked object
	 * @return true if they collide else false
	 */
	public boolean isCollidingVertical(ObjectOnMap collideObj) {

		return (isCollidingUp(collideObj) && isCollidingDown(collideObj));
	}
	/**
	 * check if the current object touches the checked object from the left
	 * @param collideObj - the checked object
	 * @return true if they collide else false
	 */
	public boolean isCollidingRight(ObjectOnMap collideObj) {
		int maxX = collideObj.getObjectX() + collideObj.getObjectWidth() - _width / 2;
		return (_x < maxX && isTouching(collideObj));
	}
	/**
	 * check if the current object touches the checked object from the right
	 * @param collideObj - the checked object
	 * @return true if they collide else false
	 */
	public boolean isCollidingLeft(ObjectOnMap collideObj) {
		int minX = collideObj.getObjectX() - _width / 2;
		return (_x > minX && isTouching(collideObj));
	}
	/**
	 * check if the current object touches the checked object from the bottom 
	 * @param collideObj - the checked object
	 * @return true if they collide else false
	 */
	public boolean isCollidingUp(ObjectOnMap collideObj) {
		int minY = collideObj.getObjectY() - _height / 2;
		return (_y > minY && isTouching(collideObj));
	}
	/**
	 * check if the current object touches the checked object from the top 
	 * @param collideObj - the checked object
	 * @return true if they collide else false
	 */
	public boolean isCollidingDown(ObjectOnMap collideObj) {
		int maxY = collideObj.getObjectY() + collideObj.getObjectHeight() - _height / 2;
		return (_y < maxY && isTouching(collideObj));
	}
	/**
	 * check if borders collide between an object
	 * @param collideObj - the checked object
	 * @return true if they collide else false
	 */
	public boolean isTouching(ObjectOnMap collideObj) {
		Rectangle r = new Rectangle(_x, _y, _width, _height);
		Rectangle t = new Rectangle(collideObj.getObjectX(), collideObj.getObjectY(), collideObj.getObjectWidth(),
				collideObj.getObjectHeight());
		return r.intersects(t);
	}

	/*
	 * every set uses setBoundForObject
	 */
	public int getObjectX() {
		return _x;
	}

	public void setObjectX(int x) {
		setBoundForObject(x, _y, _width, _height);
	}

	public int getObjectY() {
		return _y;
	}

	public void setObjectY(int y) {
		setBoundForObject(_x, y, _width, _height);

	}

	public int getObjectWidth() {
		return _width;
	}

	public void setObjectWidth(int width) {
		setBoundForObject(_x, _y, width, _height);

	}

	public int getObjectHeight() {
		return _height;
	}

	public void setObjectHeight(int height) {
		setBoundForObject(_x, _y, _width, height);

	}

	public void setObjectGraphicClass(PaintingInterface graphicClass) {
		_graphicClass = graphicClass;
	}

}
