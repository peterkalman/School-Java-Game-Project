package map;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import engine.Block;

/**
 * MapG is a class that represents the graphical structure of the map
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class MapG {
	private int _size;
	private int _counter = 0;
	// private int[][] _map;
	private static Block[][] _map;
	private static MapG singleton = null;

	// size - Height
	// sizeW - Width
	/**
	 * Constructor reads if the xml path given has childnodes and fills map with the blocks
	 * 
	 * @param size
	 *            - the row amount
	 * @param sizeW
	 *            - the col amount
	 * @param fileName
	 *            - the file path
	 */
	private MapG(int size, int sizeW, String wallName, String floorName) {
		
		// Map is a mat sizeW*size
		_map = new Block[size][sizeW];
		_size = sizeW;

		try {
			File wallFile = new File(wallName);
			File floorFile = new File(floorName);
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document wallDoc = docBuilder.parse(wallFile);
			Document floorDoc = docBuilder.parse(floorFile);

			if (wallDoc.hasChildNodes() && floorDoc.hasChildNodes()) {
				readNode(wallDoc.getChildNodes(), floorDoc.getChildNodes());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * initialize the singleton instance
	 * 
	 * @return MapG only instance
	 */
	
	public static MapG init(int size, int sizeW, String wallName, String floorName) {
		singleton = new MapG(size, sizeW, wallName, floorName);
		return singleton;
	}
	/**
	 * get the Singleton instance
	 * 
	 * @return MapG only instance
	 */
	public static MapG getInstance() {
		return singleton;
	}
	/**
	 * @return Block Matrix that is read from the XML
	 */
	public static Block[][] get_map() {
		return _map;
	}

	/**
	 * the function reads from the nodeList (xml) the values of the attributes and updates _map
	 * based on it from both floorList and wallList
	 * 
	 * @param wallList - the nodes that contain walls
	 * @param floorList - the nodes that contain floors
	 */
	private void readNode(NodeList wallList, NodeList floorList) {
		for (int count = 0; count < wallList.getLength(); count++) {
			Node tempWallNode = wallList.item(count);
			Node tempFloorNode = floorList.item(count);
			if (tempWallNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempWallNode.hasAttributes()) {
					NamedNodeMap nodeWallMap = tempWallNode.getAttributes();
					NamedNodeMap nodeFloorMap = tempFloorNode.getAttributes();
					for (int i = 0; i < nodeWallMap.getLength(); i++) {

						Node wallNode = nodeWallMap.item(i);
						Node floorNode = nodeFloorMap.item(i);
						_map[_counter % _size][_counter / _size] = new Block(Integer.parseInt(wallNode.getNodeValue()),
								_counter % _size, _counter / _size, 64, 64);

						_map[_counter % _size][_counter / _size]
								.setFloorIndex(((Integer.parseInt(floorNode.getNodeValue())) >> 4) & 0b1111);
						// System.out.println(" I:" + _counter / _size + " J:" + _counter % _size +
						// " "
						// + _map[_counter / _size][_counter % _size]);
						_counter++;
					}
				}

				if (tempWallNode.hasChildNodes()) {
					readNode(tempWallNode.getChildNodes(), tempFloorNode.getChildNodes());
				}
			}
		}
	}
}
