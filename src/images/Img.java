package images;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import ultilityTools.ImageTools;

/**
 * Img is a class that represents an image , which is used to make drawing images a lot easier.
 * 
 * @author user
 */
public class Img {
	private Image _image;
	private int x, y, width, height;

	public Img() {
		_image = null;
		x = y = width = height = 0;
	}

	/**
	 * Constructor
	 * 
	 * @param path
	 *            - the path string of this Img
	 * @param x
	 *            - the X position of the img
	 * @param y
	 *            - the Y position of the img;
	 * @param width
	 *            - the width of the img;
	 * @param height
	 *            - the height of the img;
	 */
	public Img(String path, int x, int y, int width, int height) {
		/**
		 * gets the image from the resource folder that is located by using the path given
		 */
		_image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage();

		setImgCords(x, y);
		setImgSize(width, height);
	}

	/**
	 * clone is a function that returns a copy of the current Img without damaging the original
	 */
	public Img clone() {
		Img clon = new Img("", getX(), getY(), getWidth(), getHeight());
		clon.setImage(_image);
		return clon;
	}

	/**
	 * Draws image onto graphics , using Graphics2d
	 * 
	 * @param g
	 *            -Graphics that are used
	 */
	public void drawImg(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// g2d.setColor(new Color(0, 0, 0, 255));
		// g2d.fillRect(x, y, width, height);
		g2d.drawImage(_image, x, y, width, height, null);
	}

	/**
	 * Draws image onto the graphics but in a vertical position
	 * 
	 * @param g
	 *            - Graphics that are used
	 */
	public void drawImgVertically(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(_image, x + width, y, -width, height, null);
	}

	/**
	 * Draws image onto the graphics but in a horizontal position
	 * 
	 * @param g
	 *            - Graphics that are used
	 */
	public void drawImgHorizontally(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(_image, x, y + height, width, -height, null);
	}

	/**
	 * Draws image onto the graphics but in an angle rotation clockwise
	 * 
	 * @param g
	 *            - Graphics that are used
	 */
	public void drawImgRotate(Graphics g, double angle) {
		setImage(ImageTools.rotate(this, angle));
		drawImg(g);
	}
	/**
	 * Draws image onto the graphics but only a cropped part of it
	 * @param g - Graphics that are used
	 * @param newX - the new X position from the new cropped img
	 * @param newY - the new Y position from the new cropped img
	 * @param newHeight - the new Height  for the new cropped img
	 * @param newWidth - the new Width for the new cropped img
	 */
	public void drawPartImage(Graphics g, int newX, int newY, int newHeight, int newWidth) {
		BufferedImage bimage = ImageTools.crop(this, newX, newY, newHeight, newWidth);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bimage, x + newX, y + newY, newWidth, newHeight, null);
	}
	/**
	 * Draws image onto the graphics but only a cropped part of it and draw it vertically
	 * @param g - Graphics that are used
	 * @param newX - the new X position from the new cropped img
	 * @param newY - the new Y position from the new cropped img
	 * @param newHeight - the new Height  for the new cropped img
	 * @param newWidth - the new Width for the new cropped img
	 */
	public void drawPartImageVeritcally(Graphics g, int newX, int newY, int newHeight, int newWidth) {
		BufferedImage bimage = ImageTools.crop(this, newX, newY, newHeight, newWidth);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bimage, x + newX + newWidth, y + newY, -newWidth, newHeight, null);
	}

	/**
	 * Sets image cordinates
	 * 
	 * @param x
	 *            - the X position of the img
	 * @param y
	 *            - the Y position of the img;
	 */
	public void setImgCords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param width
	 *            - the width of the img;
	 * @param height
	 *            - the height of the img;
	 */
	public void setImgSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	/**
	 * @return the Image of the Img
	 */
	public Image getImage() {
		return _image;
	}
	/**
	 * set a new Image of the Img  
	 * @param image - the new Image
	 */
	public void setImage(Image image) {
		_image = image;
	}
	/**
	 * set a new Image of the Img  
	 * @param path - the path to a picture in the resource folder.
	 */
	public void setImage(String path) {
		// _image = new Image
		_image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage();
	}
	/**
	 * @return get X position of the Img
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return get Y position of the Img
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return get the Width of the Img
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * set a new Width for the Img
	 * @param width - the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the height of the Img
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * set a new Height for the Img
	 * @param height - the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
