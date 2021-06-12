package unit.player;

import java.awt.Graphics;

import javax.swing.JPanel;

import images.Img;
import ultilityTools.ImageTools;
import ultilityTools.PaintingInterface;
import unit.customizeScreen.CustomizeInterface;

/**
 * UnitG represents the graphical class of the unit or player
 * 
 * @author Peter
 */
public class PlayerG implements PaintingInterface, CustomizeInterface {
	private int _unitHeight;
	private int _unitWidth;
	private Outfit _customize;
	private Img _unit;
	private Img _bottom;
	private Img _top;
	private Img _headGear;
	private Img _hair;
	private Img _weapon;
	private Img _face;
	private int _direction;
	/**
	 * the parent is needed inorder to switch between customize screen and game
	 */
	private JPanel _parent;
	/**
	 * padding is essential for the player if we dont want some of the models to be cut.
	 */
	public final static int padding = 10;

	/**
	 * Constructor
	 * 
	 * @param unitHeight
	 *            - the height of the player
	 * @param unitWidth
	 *            - the width of the player
	 */
	public PlayerG(int unitHeight, int unitWidth) {
		_unitHeight = unitHeight;
		_unitWidth = unitWidth;// \\images\\srcImages\\Pawns\\Clothing\\Bottom\\Walls\\0001Wall_Atlas.png
		/** initialize outfit set */
		_customize = new Outfit();
		/** allow user to select the outfits */
		// _customize.SelectAllApparels();
		_customize.fillAllApparels();
		_customize.SelectAllDefault();
		/** initialize img with blank images */
		_unit = new Img();
		_face = new Img();
		_bottom = new Img();
		_top = new Img();
		_headGear = new Img();
		_weapon = new Img();
		_hair = new Img();
		setAllSizes(_unitHeight, _unitWidth);
		setImgSide(0);
	}

	/**
	 * Constructor
	 * 
	 * @param unitHeight
	 *            - the height of the player
	 * @param unitWidth
	 *            - the width of the player
	 * @param parent
	 *            - the Panel parent of this graphics class
	 */
	public PlayerG(int unitHeight, int unitWidth, JPanel parent) {
		this(unitHeight, unitWidth);
		_parent = parent;
	}

	/**
	 * Sets all size \ positions based on the received height and width
	 * 
	 * @param unitHeight
	 *            - the new height of the Player
	 * @param unitWidth
	 *            - the new width of the player
	 */
	public void setAllSizes(int unitHeight, int unitWidth) {
		_unitHeight = unitHeight;
		_unitWidth = unitWidth;
		_unit.setImgSize(_unitWidth, _unitHeight);
		_unit.setImgCords(padding, padding);
		_face.setImgSize(_unitWidth, _unitHeight);
		_face.setImgCords(padding, padding + (int) ((-15 / 64.0) * _unitHeight));
		_bottom.setImgSize(_unitWidth, _unitHeight);
		_bottom.setImgCords(padding, padding);
		_top.setImgSize(_unitWidth, _unitHeight);
		_top.setImgCords(padding, padding + (int) ((5 / 64.0) * _unitHeight));
		_headGear.setImgSize(_unitWidth, _unitHeight);
		_headGear.setImgCords(padding, padding + (int) ((-18 / 64.0) * _unitHeight));
		_weapon.setImgSize(_unitWidth, _unitHeight);
		_weapon.setImgCords(padding + (int) ((10 / 64.0) * _unitWidth), padding + (int) ((8 / 64.0) * _unitHeight));
		_hair.setImgSize(_unitWidth, _unitHeight);
		_hair.setImgCords(padding + (int) ((-5 / 64.0)), padding + (int) ((-15 / 64.0) * _unitHeight));
		if (_parent != null) {
			_parent.repaint();
		}
	}

	/**
	 * select the proper img based on the side the player is facing
	 * 
	 * @param side
	 *            - the side that the player is facing (0 front , 1 right , 2 back ,3 left)
	 */
	public void setImgSide(int side) {// 0 front , 1 right , 2 back ,3 left
		_direction = side;
		/** if the direction is sideway */
		if (_direction % 2 == 1) {
			_unit.setImage(_customize.getFigure().getSide());
			_face.setImage(_customize.getFace().getSide());
			_bottom.setImage(_customize.getBottom().getSide());
			_top.setImage(_customize.getTop().getSide());
			_headGear.setImage(_customize.getHeadgear().getSide());
			// _weapon.setImage(_customize.getWeapon().getSide());
			_hair.setImage(_customize.getHair().getSide());
		}
		/** if the direction is back */
		else if (_direction == 2) {
			_unit.setImage(_customize.getFigure().getBack());
			_face.setImage(_customize.getFace().getBack());
			_bottom.setImage(_customize.getBottom().getBack());
			_top.setImage(_customize.getTop().getBack());
			_headGear.setImage(_customize.getHeadgear().getBack());
			// _weapon.setImage(_customize.getWeapon().getBack());
			_hair.setImage(_customize.getHair().getBack());
		}
		/** if the direction is front */
		else if (_direction == 0) {
			_unit.setImage(_customize.getFigure().getFront());
			_face.setImage(_customize.getFace().getFront());
			_bottom.setImage(_customize.getBottom().getFront());
			_top.setImage(_customize.getTop().getFront());
			_headGear.setImage(_customize.getHeadgear().getFront());
			_hair.setImage(_customize.getHair().getFront());
		}
		_weapon.setImage(_customize.getWeapon().getFront());
	}

	/**
	 * Set a new Parent for the graphics class
	 * 
	 * @param parent
	 *            - the new Panel Parent
	 */
	public void setParent(JPanel parent) {
		_parent = parent;
	}

	/**
	 * the implemented method of the paintingInterface , so the Player would appear on map
	 */
	@Override
	public void myPaintComponent(Graphics g) {
		switch (_direction) {
		case 0:
			_unit.drawImg(g);
			_bottom.drawImg(g);
			_top.drawImg(g);
			_weapon.setImgCords(padding + (int) ((5 / 64.0) * _unitWidth), padding + (int) ((8 / 64.0) * _unitHeight));
			_weapon.drawImg(g);

			_unit.drawPartImage(g, 0, 0, (int) ((_unitHeight) / 2.5), _unitWidth);
			_face.drawImg(g);
			_hair.drawImg(g);
			_headGear.drawImg(g);
			break;
		case 1:
			_weapon.setImgCords(padding + (int) ((12 / 64.0) * _unitWidth), padding + (int) ((8 / 64.0) * _unitHeight));
			_weapon.drawImgRotate(g, 85);
			_unit.drawImg(g);
			// _face.drawImg(g);
			_bottom.drawImg(g);
			_top.drawImg(g);
			// _hair.drawImg(g);
			// _headGear.drawImg(g);
			_unit.drawPartImage(g, 0, 0, (int) ((_unitHeight) / 2.5), _unitWidth);
			_face.drawImg(g);
			_hair.drawImg(g);
			_headGear.drawImg(g);
			break;
		case 2:
			_weapon.setImgCords(padding + (int) ((5 / 64.0) * _unitWidth), padding + (int) ((-5 / 64.0) * _unitHeight));
			_weapon.drawImg(g);
			_unit.drawImg(g);
			_bottom.drawImg(g);
			_top.drawImg(g);
			_unit.drawPartImage(g, 0, 0, (int) ((_unitHeight) / 3), _unitWidth);
			_face.drawImg(g);
			_hair.drawImg(g);
			_headGear.drawImg(g);
			break;
		case 3:
			/** if the direction is left flip the image */
			_weapon.setImgCords(padding + (int) ((-12 / 64.0) * _unitWidth),
					padding + (int) ((8 / 64.0) * _unitHeight));
			_weapon.setImage(ImageTools.rotate(_weapon, 85));
			_weapon.drawImgVertically(g);
			_unit.drawImgVertically(g);
			_bottom.drawImgVertically(g);
			_top.drawImgVertically(g);
			_unit.drawPartImageVeritcally(g, 0, 0, (int) ((_unitHeight) / 2.5), _unitWidth);
			_face.drawImgVertically(g);
			_hair.drawImgVertically(g);
			_headGear.drawImgVertically(g);
			break;
		}
	}

	/**
	 * whenever a button is clicked in the customize menu
	 */
	@Override
	public void CustomizeButtonClicked(String name, boolean isNext) {
		if (name.compareTo("Headgear") == 0) {
			_customize.getHeadgear().switchTo(isNext);
		} else if (name.compareTo("Hair") == 0) {
			_customize.getHair().switchTo(isNext);
		} else if (name.compareTo("Face") == 0) {
			_customize.getFace().switchTo(isNext);
		} else if (name.compareTo("Body") == 0) {
			_customize.getFigure().switchTo(isNext);
		} else if (name.compareTo("Top") == 0) {
			_customize.getTop().switchTo(isNext);
		} else if (name.compareTo("Weapon") == 0) {
			_customize.getWeapon().switchTo(isNext);
		} else if (name.compareTo("Bottom") == 0) {
			_customize.getBottom().switchTo(isNext);
		}

		setImgSide(0);
		if(_parent!=null)
			_parent.repaint();
	}

	/**
	 * Copies a new players graphics to the current players graphics
	 * 
	 * @param playerGraphics
	 *            - the new Players graphics
	 */
	public void copyPlayerG(PlayerG playerGraphics) {
		_customize = playerGraphics._customize;
		setAllSizes(playerGraphics._unitHeight, playerGraphics._unitWidth);
		setImgSide(0);
	}

	/**
	 * @return - the outfit which contains all of the clothing with a weapon
	 */
	public Outfit getCustomize() {
		return _customize;
	}

	/**
	 * @return - get the current direction at which the player is facing
	 */
	public int getDirection() {
		return _direction;
	}

}
