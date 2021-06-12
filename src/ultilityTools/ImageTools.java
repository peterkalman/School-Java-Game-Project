package ultilityTools;

import images.Img;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Image tools is a class that represents usefull function that are going to be used throughout the
 * project
 * 
 * @author Peter
 */
public class ImageTools {
	/**
	 * The function converts img to buffered image since you cant always convert image to
	 * BufferedImage you will have to draw a copy of it on the new buffered Image.
	 * 
	 * @param img
	 *            - the img we want to convert to buffered Image
	 * @return - an instance of BufferedImage
	 */
	public static BufferedImage toBufferedImage(Img img) {
		if (img.getImage() instanceof BufferedImage) {
			return (BufferedImage) img.getImage();
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img.getImage(), 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	/**
	 * the function draws a panel into Buffered Image which can be used again instead of drawing the
	 * components of panel again
	 * 
	 * @param panel
	 * @return the new bufferedImage which is the panel
	 */
	public static BufferedImage createImageFromPanel(JPanel panel) {
		int w = panel.getWidth();
		int h = panel.getHeight();
		// Create a buffered image with transparency
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		// Draw the panel on to the buffered image
		Graphics g = bi.createGraphics();
		panel.paint(g);
		// Return the buffered image
		return bi;
	}

	/**
	 * Rotates an image. Actually rotates a new copy of the image.
	 * 
	 * @param img
	 *            The image to be rotated
	 * @param angle
	 *            The angle in degrees
	 * @return The rotated image
	 */
	public static Image rotate(Img img, double angle) {
		double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));

		int w = img.getWidth(), h = img.getHeight();

		int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);

		BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bimg.createGraphics();

		g.translate((neww - w) / 2, (newh - h) / 2);
		g.rotate(Math.toRadians(angle), w / 2, h / 2);
		g.drawRenderedImage(toBufferedImage(img), null);
		g.dispose();

		return bimg;
	}
	/**
	 * Crops an Img by the new given coordinates 
	 * @param imgToCrop - the Img to crop
	 * @param cropX - the X position to start the crop from
	 * @param cropY - the Y position to start the crop from
	 * @param cropHeight - the new Height to start the crop from
	 * @param cropWidth  - the new Width to start the crop from
	 * @return
	 */
	public static BufferedImage crop(Img imgToCrop, int cropX, int cropY, int cropHeight, int cropWidth) {
		BufferedImage bimage = new BufferedImage(imgToCrop.getWidth(), imgToCrop.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(imgToCrop.getImage(), 0, 0, imgToCrop.getWidth(), imgToCrop.getHeight(), null);
		bGr.dispose();
		return bimage.getSubimage(cropX, cropY, cropWidth, cropHeight);
	}
	
	/**
	 * Make button is a function that puts an image on a button and returns the instance of it
	 * 
	 * @param path-
	 *            the path to the picture
	 * @return the instance of the button
	 */
	public static JButton makeButton(String path) {
		BufferedImage buttonIcon = null;

		try {
			buttonIcon = ImageIO.read(new File(path));
		} catch (IOException e1) {
		}
		JButton btn = new JButton(new ImageIcon(buttonIcon));
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setContentAreaFilled(false);
		return btn;
	}
	/**
	 * Make Label is a function that puts an image on a label and returns the instance of it
	 * 
	 * @param path-
	 *            the path to the picture
	 * @return the instance of the label
	 */
	public static JLabel makeLabel(String path) {
		BufferedImage buttonIcon = null;

		try {
			buttonIcon = ImageIO.read(new File(path));
		} catch (IOException e1) {
		}
		JLabel lbl = new JLabel(new ImageIcon(buttonIcon));
		lbl.setBorder(BorderFactory.createEmptyBorder());
		//lbl.setContentAreaFilled(false);
		return lbl;
	}

}

