package engine;

import mainInitialize.GameStats;
import map.MapG;
import map.MapGFrame;
import map.MapGPanel;

/**
 * Block is a class that represents the current stats of the block and ImgID  mapG matrix is filled with these
 * 
 * @author Peter
 */
public class Block {
	/**
	 * the amount of chance to hit that is being substracted from the projectile
	 */
	private int _defenseVal;
	/**
	 * max health of a wall
	 */
	private int _maxHealth;
	/**
	 * current health of a wall
	 */
	private int _currentHealth;
	/**
	 * diffrent levels of walls\floors
	 */
	private int _wallLevel;// 0 means floor, 1 means wall
	/**
	 * the img ID according to xml table
	 */
	private int _imgID;
	/**
	 * the modelIndex according to the 4 bits before the 4lsb bits
	 */
	private int _modelIndex;
	/**
	 * the bonusIndex according to the 4 bits before the 8 lsb bits
	 */
	private int _bonusModel;
	private int _x, _y, _height, _width;

	private int _floorModelIndex;

	private boolean _isFull = false;

	/**
	 * constructor- prepares the models and stats
	 * 
	 * @param imgID
	 *            - the img ID of the block according to the xml
	 */
	public Block(int imgID, int x, int y, int width, int height) {
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		_imgID = imgID;
		_wallLevel = (_imgID & 0b0011);
		_modelIndex = (_imgID >> 4) & 0b1111;
		if (_wallLevel >= 0b0001) {
			_bonusModel = (_imgID >> 8) & 0b1111;
			if (_wallLevel == 0b0010) {
				_defenseVal = 0;
				_maxHealth = 0;
				GameStats.addFullWall(this);
				_isFull = true;
			} else {
				GameStats.addHalfWall(this);
				_defenseVal = 30;
				_maxHealth = 10 * 10;
			}
		}

		_currentHealth = _maxHealth;
	}

	/*
	 * public Block(int imgID) { this(imgID,0,0,0,0); }
	 */
	/**
	 * @return true if the wall is full else false.
	 */
	public boolean isFull() {
		return _isFull;
	}

	/**
	 * @return a string containing the coordinates of the block
	 */
	public String toString() {
		String st = new String();
		st = _x + " " + _y + " " + _width + " " + _height;
		return st;
	}

	/**
	 * @return the defense value of the block
	 */
	public int getDefenseVal() {
		return _defenseVal;
	}

	/**
	 * set the defence value of the block
	 * 
	 * @param defenseVal
	 *            -the new defence value of the block
	 */
	public void setDefenseVal(int defenseVal) {
		_defenseVal = defenseVal;
	}

	/**
	 * @return the max health of the block
	 */
	public int getMaxHealth() {
		return _maxHealth;
	}

	/**
	 * set the max health of the block
	 * 
	 * @param maxHealth
	 *            - the new max health of the block
	 */
	public void setMaxHealth(int maxHealth) {
		_maxHealth = maxHealth;
	}

	/**
	 * @return the current health of the block
	 */
	public int getCurrentHealth() {
		return _currentHealth;
	}

	/**
	 * set the floorModelIndex of the block and set the model index if block is floor
	 * 
	 * @param floorModel
	 *            - the new floorModel of the block
	 */
	public void setFloorIndex(int floorModel) {
		_floorModelIndex = floorModel;
		if (_wallLevel == 0)
			_modelIndex = floorModel;
	}

	/**
	 * @return the floor model index
	 */
	public int getFloorModelIndex() {
		return _floorModelIndex;
	}

	/**
	 * set the current health of the block and update accordiongly by wall from global list
	 * 
	 * @param currentHealth
	 *            - the new current health of the block
	 */
	public void setCurrentHealth(int currentHealth) {
		_currentHealth = currentHealth;
		if (_currentHealth <= 0) {
			updateNeighborsBonusModel();

			_defenseVal = 0;
			_wallLevel = 0;
			_modelIndex = _floorModelIndex;
			_bonusModel = 0;
			GameStats.removeHalfWall(this);
			MapGPanel.getInstance().repaint();
			// need to add update neighbor function

		}
	}

	/**
	 * @return the division float between currentHealth to maxHealth
	 */
	public float getHpDivision() {
		return (_maxHealth == 0) ? 0 : _currentHealth / (float) _maxHealth;
	}

	/**
	 * update the neighbors of the nearby walls (used in case of removal of the block)
	 */
	public void updateNeighborsBonusModel() {
		// System.out.println("Up");
		if (MapG.get_map()[_x][_y - 1].getWallLevel() != 0)
			MapG.get_map()[_x][_y - 1].setBonusModel(getProperBonusModelIndex(MapG.get_map()[_x][_y - 1], 'd'));
		// System.out.println("Down");
		if (MapG.get_map()[_x][_y + 1].getWallLevel() != 0)
			MapG.get_map()[_x][_y + 1].setBonusModel(getProperBonusModelIndex(MapG.get_map()[_x][_y + 1], 'u'));
		// System.out.println("Right");
		if (MapG.get_map()[_x + 1][_y].getWallLevel() != 0)
			MapG.get_map()[_x + 1][_y].setBonusModel(getProperBonusModelIndex(MapG.get_map()[_x + 1][_y], 'l'));
		// System.out.println("Left");
		if (MapG.get_map()[_x - 1][_y].getWallLevel() != 0)
			MapG.get_map()[_x - 1][_y].setBonusModel(getProperBonusModelIndex(MapG.get_map()[_x - 1][_y], 'r'));
	}

	/**
	 * <pre>
	 * return the bonus model index ( direction index) for a wall based on the neighbors.
	 *  REFER TO
	 * _wallModels wall directions used for better use of cpu , and overall better performance in
	 * order to understand - guider is the logical sequence that we can use inorder to use math to
	 * increase performance the first index represents the amount of neighbors the second index
	 * represents the index according to the logical guider
	 * </pre>
	 * 
	 * _wall3Models=//guider the wall that is missing clockwise <br/>
	 * _wall2Models//guider will be the most clockwised way exists,clockwise<br/>
	 * _wall1Models//guider the wall that exists clockwise<br/>
	 * 
	 * @param blck
	 *            - the block we want to check its neighbors
	 * @param blocker
	 *            - the Char indicating direction of the removed block
	 * @return the proper direction index for the received block
	 */
	public int getProperBonusModelIndex(Block blck, char blocker) {
		/**
		 * 
		 */
		int[][] _wallModels = { { 12 }, { 8, 14, 13, 4 }, { 5, 15, 10, 0, 6, 9 }, { 7, 1, 2, 11 }, { 3 } };
		/** if the block is a full wall */
		if (blck.getMaxHealth() == 0)
			return blck._bonusModel;
		int i = blck._x, j = blck._y;
		int up = (i > 0 && blocker != 'u' && equals(MapG.get_map()[i][j - 1])) ? 1 : 0;
		int down = (i < MapG.get_map().length && blocker != 'd' && equals(MapG.get_map()[i][j + 1])) ? 1 : 0;
		int right = (j < MapG.get_map().length && blocker != 'r' && equals(MapG.get_map()[i + 1][j])) ? 1 : 0;
		int left = (j > 0 && blocker != 'l' && equals(MapG.get_map()[i - 1][j])) ? 1 : 0;
		// System.out.println("up :" + up + " down :" + down + " right :" + right + " left :" +
		// left);
		int sum = up + down + right + left;
		/** if the sum of same neighbor walls is 0 or 4 */
		if (sum % 4 == 0)
			return _wallModels[sum][0];
		else {
			/** if the of the same neigbor walls is 1 or 3 */
			if (sum % 2 == 1) {
				if (up == down) {
					if (up == right) {
						return _wallModels[sum][3];
					} else {
						return _wallModels[sum][1];
					}
				} else {
					if (up == right)
						return _wallModels[sum][0];
					else {
						return _wallModels[sum][2];
					}
				}
			}
			if (up == down) {
				if (up != 0)
					return _wallModels[sum][5];
				else
					return _wallModels[sum][4];
			} else {
				if (up == left)
					if (up != 0)
						return _wallModels[sum][0];
					else
						return _wallModels[sum][2];
				else if (up == right)
					if (up != 0)
						return _wallModels[sum][1];
					else
						return _wallModels[sum][3];
			}
		}
		return 0;
	}

	/**
	 * return true if the block contains the same wallLeven and ModelIndex in the object else false
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Block) {
			Block blck = (Block) obj;
			return (blck.getWallLevel() == _wallLevel && blck.getModelIndex() == _modelIndex);
		}
		return false;
	}

	/**
	 * @return the wall level of the block while 0 indicating floor and 1 indicating wall(any wall)
	 */
	public int getWallLevel() {
		return _wallLevel;
	}

	/**
	 * set the wall level of the block
	 * 
	 * @param wallLevel
	 *            - the new wall level of the block
	 */
	public void setWallLevel(int wallLevel) {
		_wallLevel = wallLevel;
	}

	/**
	 * @return the ImgID of the block
	 */
	public int getImgID() {
		return _imgID;
	}

	/**
	 * set the ImgID of the block
	 * 
	 * @param imgID
	 *            - the new ImgID of the block
	 */
	public void setImgID(int imgID) {
		_imgID = imgID;
	}

	/**
	 * @return the modelIndex of the block
	 */
	public int getModelIndex() {
		return _modelIndex;
	}

	/**
	 * set the modelIndex of the block
	 * 
	 * @param modelIndex
	 *            - the new modelIndex of the block
	 */
	public void setModelIndex(int modelIndex) {
		_modelIndex = modelIndex;
	}

	/**
	 * @return the bonusModel of the block
	 */
	public int getBonusModel() {
		return _bonusModel;
	}

	/**
	 * set the bonusModel of the block
	 * 
	 * @param bonusModel
	 *            - the new bonus model of the block
	 */
	public void setBonusModel(int bonusModel) {
		_bonusModel = bonusModel;
	}

	/**
	 * @return the X position the block (in position units)
	 */
	public int getX() {
		return _x;
	}

	/**
	 * set the X position of the block (in position units)
	 * 
	 * @param x
	 *            - the new X position of the block
	 */
	public void setX(int x) {
		_x = x;
	}

	/**
	 * @return the Y position the block (in position units)
	 */
	public int getY() {
		return _y;
	}

	/**
	 * set the Y position of the block (in position units)
	 * 
	 * @param y
	 *            - the new Y position of the block
	 */
	public void setY(int y) {
		_y = y;
	}

	/**
	 * @return the height of the block (in pixels)
	 */
	public int getHeight() {
		return _height;
	}

	/**
	 * set the height of the block (in pixels)
	 * 
	 * @param height
	 *            - the new height of the block
	 */
	public void setHeight(int height) {
		_height = height;
	}

	/**
	 * @return the width of the block (in pixels)
	 */
	public int getWidth() {
		return _width;
	}

	/**
	 * set the width of the block (in pixels)
	 * 
	 * @param width
	 *            - the new width of the block
	 */
	public void setWidth(int width) {
		_width = width;
	}
}
