package engine;

import java.util.Random;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * RandomMapCreator is a class that represents a random matrix that represents a map
 * 
 * @author Peter
 */
public class RandomMapCreator {
	/**
	 * the indexes available<br/>
	 * first index is floor <br/>
	 * second index is middle floor <br/>
	 * third index is full<br/>
	 * wall forth index is half wall
	 */
	private int _keys[] = { 0, 1, 1, 2 };// { 128, 3077, 3173 };
	/**
	 * matrix that represents the location of diffrent walls , 0 if no wall
	 */
	private int _wallMap[][];
	/**
	 * matrix that represents the location of diffrent floors
	 */
	private int _floorMap[][];

	private int _rows;
	private int _cols;
	/**
	 * wall models according to indexes, [0] at first means no wall ( because we read the wallmap)
	 * the second index is an indestructible wall and the third index is half wall
	 */
	private int[] _walls = { 0, 16, 32 };
	/**
	 * floor models according to indexes
	 */
	private int[] _floors = { 0, 160 };
	/**
	 * the spawn zone the randomization cannot override it.
	 */
	private int[][] _spawn;
	/**
	 * <pre>
	 * wall directions used for better use of cpu , and overall better performance in order to
	 * understand - guider is the logical sequence that we can use inorder to use math to increase
	 * performance the first index represents the amount of neighbors the second index represents
	 * the index according to the logical guider
	 * </pre>
	 * 
	 * _wall3Models=//guider the wall that is missing clockwise . <br/>
	 * _wall2Models//guider will be the most clockwised wayexists, clockwise.<br/>
	 * _wall1Models//guider the wall that exists clockwise.
	 */
	private int[][] _wallModels = { { 12 }, { 8, 14, 13, 4 }, { 5, 15, 10, 0, 6, 9 }, { 7, 1, 2, 11 }, { 3 } };

	private Random _rand = new Random();

	/**
	 * Constructor
	 * 
	 * @param row
	 *            - the amount of rows in the new Generated map
	 * @param col
	 *            - the amount of cols in the new Generated map
	 */
	public RandomMapCreator(int row, int col) {
		setRandomWallsAndFloors();
		_wallMap = new int[row][col];
		_floorMap = new int[row][col];
		_rows = row;
		_cols = col;
		// one wall bonusmodel is 3072 +5
		for (int i = 0; i < _rows; i++) {
			_wallMap[i][_cols - 1] = _wallMap[i][0] = _keys[2];
		}
		for (int i = 0; i < _cols; i++) {
			_wallMap[_rows - 1][i] = _wallMap[0][i] = _keys[2];
		}
		/**
		 * copy the walls into the spawn matrix
		 */
		_spawn = myCopy(_wallMap, _rows, _cols);
		setSpawn();
		fillFullWallMat();

		/**
		 * fill a block of floor in the middle
		 */

		fillFloorMat();

		/**
		 * copy the indestructible set to the spawnArray;
		 */
		_spawn = myCopy(_wallMap, _rows, _cols);
		setSpawn();
		/**
		 * create randomly ,destructible blocks
		 */
		fillHalfWallMat();
		_wallMap = polishMap(_wallMap);
		createXmlFileFromMap(_floorMap, "RandomMapFloor");
		createXmlFileFromMap(_wallMap, "RandomMapWall");
	}

	/**
	 * fill the center block with flor inside the floor map matrix
	 */
	public void fillFloorMat() {
		for(int i=0;i<_rows;i++){
			for(int j=0;j<_cols;j++)
				_floorMap[i][j] = _floors[_keys[0]];
		}
		for (int i = (_rows + 1) / 4; i < (_rows) / 2 * 1.5; i++) {
			for (int j = (_cols + 1) / 4; j < (_cols) / 2 * 1.5; j++) {
					_floorMap[i][j] = _floors[_keys[1]];
			}
		}
	}

	/**
	 * fills symmetricaly the indestructible walls into the wallMap matrix
	 */
	public void fillFullWallMat() {
		int maxWalls, k, result;
		for (int i = (_rows + 1) / 2; i < _rows - 2; i++) {
			maxWalls = ((_rows + 1) * (_cols - 1)) / 30;
			for (int j = (_cols + 1) / 2; j < _cols - 2; j++) {
				if (maxWalls > 0) {
					result = _rand.nextInt(2);
					maxWalls -= (result * 4);
				} else
					result = 0;
				if (!isInBlockedZone(i, j) && _wallMap[i][_cols - 1 - j] == _wallMap[i][j]
						&& _wallMap[_rows - 1 - i][j] == _wallMap[i][j]
						&& _wallMap[_rows - 1 - i][_cols - 1 - j] == _wallMap[i][j]) {
					_wallMap[i][j] = _keys[2 * result];
					_wallMap[i][_cols - 1 - j] = _keys[2 * result];
					_wallMap[_rows - 1 - i][j] = _keys[2 * result];
					_wallMap[_rows - 1 - i][_cols - 1 - j] = _keys[2 * result];
				}
			}
		}
	}

	/**
	 * fills the wallMap matrix with half walls randomly
	 */
	public void fillHalfWallMat() {
		int maxWalls, k;
		for (int i = 1; i < _rows - 1; i++) {
			maxWalls = _rand.nextInt(6);
			while (maxWalls-- > 0 && !isFullRow(i)) {
				k = _rand.nextInt(_cols - 2) + 1;
				if ((_wallMap[i][k] == 0) && !isInBlockedZone(i, k))
					_wallMap[i][k] = _keys[3];
				else
					maxWalls++;
			}
		}
	}

	/**
	 * the function sets the the corner coordinates to be key1 in the spawn matrix
	 */
	public void setSpawn() {
		_spawn[1][1] = _spawn[_rows - 2][_cols - 2] = _spawn[_rows - 2][1] = _spawn[1][_cols - 2] = 1;
	}

	/**
	 * my copy , returns a new instance copy of the source .
	 * 
	 * @param source
	 *            - the source matrix to copy
	 * @param rows
	 *            - the rows of the new matrix
	 * @param cols
	 *            - the cols of the new matrix
	 * @return the new instance of the matrix
	 */
	public static int[][] myCopy(int[][] source, int rows, int cols) {
		int temp[][] = new int[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				temp[i][j] = source[i][j];
		return temp;
	}

	/**
	 * return true if the the given indexes are occupied by value other than 0 in the spawn matrix
	 * 
	 * @param i
	 *            - the i index
	 * @param j
	 *            - the j index
	 * @return true if the indexes contain value other than 0
	 */
	public boolean isInBlockedZone(int i, int j) {
		if (_spawn[i][j] != 0)
			return true;
		return false;
	}

	/**
	 * boolean function that tells if the row is full with walls
	 * 
	 * @param i
	 *            - the index of the row
	 * @return true if the row is full , false if there is at least one space available
	 */
	public boolean isFullRow(int i) {
		for (int j = 0; j < _cols; j++)
			if (_wallMap[i][j] == 0)
				return false;
		return true;
	}

	private void setRandomWallsAndFloors() {
		_floors[0] = _rand.nextInt(14) * 16;
		do {
			_floors[1] = _rand.nextInt(14) * 16;
		} while (_floors[1] == _floors[0]);
		_walls[1] = (_rand.nextInt(13) + 1) * 16;
		do {
			_walls[2] = (_rand.nextInt(13) + 1) * 16;
		} while (_walls[1] == _walls[2]);
	}

	/**
	 * the function takes the raw drafted matrixes and converts them into walls with connection
	 * since there are 16 variations of wall directions it can become complicated if not explained
	 * properly use the template for understanding in srcImages/walls to understand the directions
	 */
	public int[][] polishMap(int map[][]) {
		int tempMap[][] = RandomMapCreator.myCopy(map, _rows, _cols);
		int isFullWall;
		for (int i = 0; i < _rows; i++) {
			for (int j = 0; j < _cols; j++) {
				/** if the block is a wall */
				if (_wallMap[i][j] != 0) {
					isFullWall = (_wallMap[i][j] == 1) ? 1 : 0;
					/** if the block is in the border of the map */
					if (i % (_rows - 1) == 0 || j % (_cols - 1) == 0) {
						/** if the block is in the corner of the map */
						if (i % (_rows - 1) == 0 && j % (_cols - 1) == 0) {
							/** if the block is in the 0 row */
							if (i == 0) {
								/** if the block is in the 0 col */
								if (j == 0) {
									tempMap[i][j] = _walls[_wallMap[i][j]] + 5 + isFullWall + 10 * 256;

								}
								/** if the block is not in the 0 col */
								else {
									tempMap[i][j] = _walls[_wallMap[i][j]] + 5 + isFullWall + 0 * 256;
								}

							}
							/** if the block is not in row 0 */
							else {
								/** if the block is in the 0 col */
								if (j == 0) {
									tempMap[i][j] = _walls[_wallMap[i][j]] + 5 + isFullWall + 15 * 256;

								}
								/** if the block is not in the 0 col */
								else {
									tempMap[i][j] = _walls[_wallMap[i][j]] + 5 + isFullWall + 5 * 256;
								}
							}
						}
						/** if the block is not corner */
						else {
							/** if the block is a row wall */
							if (i % (_rows - 1) == 0) {
								tempMap[i][j] = _walls[_wallMap[i][j]] + 5 + isFullWall + 6 * 256;
							}
							/** if the block is a col wall */
							if (j % (_cols - 1) == 0) {
								tempMap[i][j] = _walls[_wallMap[i][j]] + 5 + isFullWall + 9 * 256;
							}
						}
					}
					/** if the block is not border */
					else {
						// this function is really complicated to understand at
						// first
						tempMap[i][j] = _walls[_wallMap[i][j]] + 5 + isFullWall + 256 * getModelIndex(i, j);
					}
				}
				/** if the block is not a wall */
			}
		}
		// pass the instance of temp into wallMap
		return tempMap;
	}

	/**
	 * the function sums the amount of neighbor walls in 4 direction to the current block if they
	 * are the same as current block.
	 * 
	 * @param i
	 *            - the row index of the current block
	 * @param j
	 *            - the col index of the current block
	 * @return the sum of all similar neighbors
	 */
	public int sumAllWallNeighbor(int i, int j) {
		int sum = 0;
		sum += (_wallMap[i + 1][j] == _wallMap[i][j] ? 1 : 0) + (_wallMap[i - 1][j] == _wallMap[i][j] ? 1 : 0)
				+ (_wallMap[i][j + 1] == _wallMap[i][j] ? 1 : 0) + (_wallMap[i][j - 1] == _wallMap[i][j] ? 1 : 0);
		return sum;
	}

	/**
	 * the function uses complex stracture of indexes I have given in order to show some logical
	 * sequence and it returns the required index of the current block
	 * 
	 * @param i
	 *            - the row index of the current block
	 * @param j
	 *            - the col index of the current block
	 * @return the index according to the template that suits best for the block
	 */
	public int getModelIndex(int i, int j) {

		// if the same block as [i][j] put 1 in variable
		int up = ((_wallMap[i - 1][j] == _wallMap[i][j]) ? 1 : 0);
		int down = ((_wallMap[i + 1][j] == _wallMap[i][j]) ? 1 : 0);
		int right = ((_wallMap[i][j + 1] == _wallMap[i][j]) ? 1 : 0);
		int left = ((_wallMap[i][j - 1] == _wallMap[i][j]) ? 1 : 0);
		int sum = up + down + right + left;// sumAllWallNeighbor(i, j);
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
	 * the function creates an xml file from the finalMap, with all of the elements needed
	 * 
	 * @param map
	 *            - the matrix which we want to convert to xml map
	 * @param name
	 *            - the name of the XML file we want to give
	 */
	public void createXmlFileFromMap(int map[][], String name) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Map");
			doc.appendChild(rootElement);
			for (int i = 0; i < _rows; i++) {
				for (int j = 0; j < _cols; j++) {
					Element Line = doc.createElement("Line");
					rootElement.appendChild(Line);

					Element Area = doc.createElement("Area");
					Line.appendChild(Area);

					Line.setAttribute("value", Integer.toString(map[i][j]));
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File f = new File("mapTemplates\\" + name + ".xml");
			/** if the randomMap exists , delete it */
			if (f.exists())
				f.delete();
			StreamResult result = new StreamResult(f);
			transformer.transform(source, result);

			//System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
