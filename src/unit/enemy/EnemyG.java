package unit.enemy;

import java.awt.Graphics;

import images.Img;
import ultilityTools.ImageTools;
import ultilityTools.PaintingInterface;
import unit.player.ApparelSet;
import unit.player.PlayerG;
/**
 * EnemyG is a class that represents the graphical appearance of an enemy on a map
 * @author Peter
 *
 */
public class EnemyG implements PaintingInterface{
	private int _unitHeight;
	private int _unitWidth;
	private Img _unit;
	private ApparelSet _unitSet;
	private int _direction;
	/**
	 * Constructor
	 * @param unitHeight -  the height of the Enemy Panel
	 * @param unitWidth - the width of the Enemy Panel
	 */
	public EnemyG(int unitHeight, int unitWidth) {
		_unitHeight = unitHeight;
		_unitWidth = unitWidth;
		_unitSet = new ApparelSet();
		_unitSet.setFile("Pawns\\Aliens\\Terminator_Male_front.png",false);
		_unit = new Img();
		setImgSide(0);
		setAllSizes(_unitHeight, _unitWidth);
		
	}
	
	/**
	 * set a new Size for the enemy 
	 * @param unitHeight - the new Height of the enemy
	 * @param unitWidth - the new Width of the enemy
	 */
	public void setAllSizes(int unitHeight, int unitWidth) {
		_unitHeight = unitHeight;
		_unitWidth = unitWidth;
		_unit.setImgSize(_unitWidth, _unitHeight);
		_unit.setImgCords(0, 0);
	}
	/**
	 * select the proper img based on the side the enemy is facing
	 * 
	 * @param side
	 *            - the side that the enemy is facing (0 front , 1 right , 2 back ,3 left)
	 */
	public void setImgSide(int side) {// 0 front , 1 right , 2 back ,3 left
		_direction = side;
		/** if the direction is sideway */
		if (_direction % 2 == 1) {
			_unit.setImage(_unitSet.getSide());
		}
		/** if the direction is back */
		else if (_direction == 2) {
			_unit.setImage(_unitSet.getBack());
		}
		/** if the direction is front */
		else if (_direction == 0) {
			_unit.setImage(_unitSet.getFront());
		}
	}
	
	/**
	 * the implemented method of the paintingInterface , so the Player would appear on map
	 */
	@Override
	public void myPaintComponent(Graphics g) {
		switch (_direction) {
		case 0:
		case 1:
		case 2:
			_unit.drawImg(g);
			break;
		case 3:
			/** if the direction is left flip the image */
			_unit.drawImgVertically(g);
			break;
		}
	}

}
