package map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.Block;
import engine.RandomMapCreator;
import engine.dijekstra.Dijekstra;
import engine.dijekstra.EdgeInt;
import engine.dijekstra.Graph;
import images.Img;
import mainInitialize.GameStats;
import ultilityTools.ImageTools;
import unit.MovingUnit;
import unit.enemy.Enemy;
import unit.player.Player;

/**
 * MapPanel is a class that represents a map's panel
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class MapGPanel extends JPanel {
	private int _mapHeight;
	private int _mapWidth;
	private int _blockSize;

	private boolean first = false;
	private Graph graph;

	public LinkedList<Point> list = new LinkedList<Point>();
	/** All Available walls */
	private String _wallPaths[] = { "Walls\\0001Wall_Atlas.png", "Walls\\0010WallLog_Atlas.png",
			"Walls\\0011Palisade_Atlas.png", "Walls\\0100Stone_Atlas.png", "Walls\\0101Fence_Atlas.png",
			"Walls\\0110Parapet_Atlas.png", "Walls\\0111SilverWall_Atlas.png", "Walls\\1000GlassWall_Atlas.png",
			"Walls\\1001UWall_Atlas.png", "Walls\\1010UWallA_Atlas.png", "Walls\\1011UWallB_Atlas.png",
			"Walls\\1100UWallYellow_Atlas.png", "Walls\\1101UWallYellowOff_Atlas.png",
			"Walls\\1110BrickWall_Atlas.jpg" };
	/** All wall destructions */
	private String _destructionPaths[] = { "Walls\\Animations\\wallDestruction_6.png",
			"Walls\\Animations\\wallDestruction_5.png", "Walls\\Animations\\wallDestruction_4.png",
			"Walls\\Animations\\wallDestruction_3.png", "Walls\\Animations\\wallDestruction_2.png",
			"Walls\\Animations\\wallDestruction_1.png" };
	/** All Available floors */
	private String _floorPaths[] = { "Floors\\0001AluminiumTile.png", "Floors\\0010CarpetBeige.png",
			"Floors\\0011CarpetBlack.png", "Floors\\0100CarpetBlue.png", "Floors\\0101CarpetGreen.png",
			"Floors\\0110CarpetRed.png", "Floors\\0111CarpetWhite.png", "Floors\\1000CopperTile.png",
			"Floors\\1001FloorFI.png", "Floors\\1010WoodFloor1.png", "Floors\\1011WoodFloor2.png",
			"Floors\\1100WoodFloor3.png", "Floors\\1101WoodFloor4.png", "Floors\\1110WoodFloor5.png",
			"Floors\\1111WoodFloor6.png" };
	private LinkedList<Img> _wall;
	private LinkedList<Img> _floor;
	private LinkedList<Img> _destruction;
	private static MapGPanel singleton = null;

	/**
	 * constructor prepares the _wall and _floor and _destruction array , also creates a new map
	 * from the two XML files
	 * 
	 * @param mapHeight
	 *            - the height of the map
	 * @param mapWidth
	 *            - the width of the map
	 * @param blockSize
	 *            - the size of a block
	 */
	private MapGPanel(int mapHeight, int mapWidth, int blockSize,boolean createNewMap) {
		//setBackground(Color.BLACK);
		//setOpaque(true);
		
		_mapHeight = mapHeight;
		_mapWidth = mapWidth;
		_blockSize = blockSize;
		if(createNewMap)
			new RandomMapCreator(mapHeight, mapWidth);
		MapG.init(_mapHeight, _mapWidth, "mapTemplates\\randomMapWall.xml", "mapTemplates\\randomMapFloor.xml");
		_wall = new LinkedList<Img>();
		_floor = new LinkedList<Img>();
		_destruction = new LinkedList<Img>();
		for (int i = 0; i < _wallPaths.length; i++) {
			addSplitAtlasAddToList(_wallPaths[i], i);
		}
		for (int i = 0; i < _floorPaths.length; i++) {
			_floor.add(new Img(_floorPaths[i], 0, 0, _blockSize, _blockSize));
		}

		for (int i = 0; i < _destructionPaths.length; i++) {
			_destruction.add(new Img(_destructionPaths[i], 0, 0, _blockSize, _blockSize));
		}
		setLayout(null);
	}

	/**
	 * initialize the singleton instance
	 * 
	 * @return MapGPanel only instance
	 */
	public static MapGPanel init(int mapHeight, int mapWidth, int blockSize,boolean createNewMap) {
		//if (singleton == null)
		singleton = new MapGPanel(mapHeight, mapWidth, blockSize,createNewMap);
		return singleton;
	}

	/**
	 * get the Singleton instance
	 * 
	 * @return MapGPanel only instance
	 */
	public static MapGPanel getInstance() {
		return singleton;
	}

	/**
	 * the function receives an atlas of walls(sprites) and creates separate images and puts them
	 * into _walls
	 * 
	 * @param imagePath
	 *            - the path to the atlas
	 * @param wallsInd
	 *            - the index of the current atlas
	 */
	public void addSplitAtlasAddToList(String imagePath, int wallsInd) {
		Img atlas = new Img(imagePath, 0, 0, 256, 256);
		Img small = new Img("", 0, 0, 64, 64);
		BufferedImage bi = ImageTools.toBufferedImage(atlas);
		for (int index = 0; index < 16; index++) {
			small.setImage((bi.getSubimage(index % 4 * _blockSize, index / 4 * _blockSize, small.getHeight(),
					small.getWidth())));
			// _wall.addLast(small.clone());
			_wall.add(wallsInd * 16 + index, small.clone());
		}
	}

	/**
	 * the paintComponent of the map
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// System.out.println("Paint Comp");
		int modelIndex, bonusModel, damageIndex;
		Block temp;
		for (int i = 0; i < _mapHeight; i++) {
			for (int j = 0; j < _mapWidth; j++) {
				temp = MapG.getInstance().get_map()[j][i];
				/** compare the wall level , if 0 then floor */
				if (temp.getWallLevel() == 0) {// floors
					/** get the modelIndex of the block */
					modelIndex = temp.getModelIndex();
					/** set coordinates */
					_floor.get(modelIndex).setImgCords((j * _blockSize), (i * _blockSize));
					_floor.get(modelIndex).drawImg(g);
				}
				/** compare the wall level , if 1 or above then wall */
				if (temp.getWallLevel() >= 1) {// walls
					/** get the modelIndex of the block */
					modelIndex = temp.getModelIndex();
					/** check if bonus models are enabled */
					if ((temp.getImgID() & 0b0100) == 0b0100) {// desigenated wall
						/** get the bonusModel of the block */
						bonusModel = temp.getBonusModel();
						/** default floor */
						_floor.get(temp.getFloorModelIndex()).setImgCords((j * _blockSize), (i * _blockSize));
						_floor.get(temp.getFloorModelIndex()).drawImg(g);
						/** set coordinates */
						_wall.get(modelIndex * 16 + bonusModel).setImgCords((j * _blockSize), (i * _blockSize));
						_wall.get(modelIndex * 16 + bonusModel).drawImg(g);

					} else {// default
						/** default bonus model */
						bonusModel = 12;

						_floor.get(0).setImgCords((j * _blockSize), (i * _blockSize));
						_floor.get(0).drawImg(g);
						/** set coordinates */
						_wall.get(modelIndex * 16 + bonusModel).setImgCords((j * _blockSize), (i * _blockSize));
						_wall.get(modelIndex * 16 + bonusModel).drawImg(g);
					}
					if (!temp.isFull()) {// half wall
						// draw damaged model
						damageIndex = getDamageIndex(temp);
						if (damageIndex < _destructionPaths.length) {
							_destruction.get(damageIndex).setImgCords((j * _blockSize), (i * _blockSize));
							_destruction.get(damageIndex).drawImg(g);
						}
					}
				}
			}
		}

		drawHUD(g);
		/*
		 * Links if(!first){ graph = new Graph(_mapHeight, _mapWidth);//((Enemy)
		 * GameStats.getPlayers().get(1)).getMover().getGraph(); graph.buildGraph(); first=true; }
		 * int center = 64 / 2; int colorR = 63, colorL = 190, colorU = 63, colorD = 190;
		 * //g.setColor(Color.BLACK); //for (int i = 0; i < _mapHeight; i++) //for (int j = 0; j <
		 * _mapWidth; j++) //g.fillOval((i + 1) * 64 - 48, (j + 1) * 64 - 48, 32, 32); for (int i =
		 * 0; i < _mapHeight-1; i++) { for (int j = 0; j < _mapWidth; j++) { for (EdgeInt e :
		 * graph.getGraph()[i][j].getAdjacencies()) { //System.out.println("-["+i+", "+j+"] -> [" +
		 * e.getTarget().getI() + ", " + e.getTarget().getJ() + "]"); if (e.getTarget().getI() == i)
		 * { if (e.getTarget().getJ() == j + 1) {// right // g.setColor(new Color(colorR, 127, 127,
		 * 200)); g.setColor(Color.green); g.fillRect((j + 1) * 64 - center, (i + 1) * 64 - center -
		 * 3, 60, 6); } else {// left // g.setColor(new Color(colorL, 127, 127, 200));
		 * g.setColor(Color.yellow); g.fillRect((j + 1) * 64 - center, (i + 1) * 64 - center + 3,
		 * -60, 6); } } else if (e.getTarget().getI() == i + 1) {// up g.setColor(Color.red); //
		 * g.setColor(new Color(127, 127, colorD, 200)); g.fillRect((j + 1) * 64 - center - 3, (i +
		 * 1) * 64 - center, 6, 60); } else {// down // g.setColor(new Color(127, 127, colorU,
		 * 200)); g.setColor(Color.blue); g.fillRect((j + 1) * 64 - center + 3, (i + 1) * 64 -
		 * center, 6, 60); } } } }
		 */
		/*
		 * g.setColor(new Color(55, 55, 55, 120)); for (Point p : list) { g.fillRect((int) p.getX(),
		 * (int) p.getY(), 64, 64); }
		 */
	}
	/**
	 * draws the health and scores of players and enemies at the top of the map
	 * @param g
	 */
	private void drawHUD(Graphics g){
		g.setFont(new Font("My Font", Font.BOLD, 16));
		
		for (MovingUnit mu : GameStats.getPlayers()) {
			if (mu instanceof Player) {
				g.setColor(new Color(255,50,50,200));
				g.drawString("HEALTH:" + ((int)(mu.getHealthPoints()*100)) + " %", 125, 40);
			} else {
				g.setColor(new Color(50,50,255,200));
				g.drawString("HEALTH:" + ((int)(mu.getHealthPoints()*100)) + " %", 725, 40);
			}
		}
		g.setColor(new Color(255,50,50,200));
		g.drawString("SCORE:" + Integer.toString(GameStats.getPlayerWon()), 45, 40);
		g.setColor(new Color(50,50,255,200));
		g.drawString("SCORE:" + Integer.toString(GameStats.getEnemyWon()),845 , 40);
	}
	
	/**
	 * get the index for Destruction array based on the current health state of the block
	 * 
	 * @param block
	 *            - the Block we want to get index for
	 * @return number below _destructionPaths.length if there is a proper index , else return the
	 *         _destructionPaths.length
	 */
	private int getDamageIndex(Block block) {
		int result = (int) (block.getHpDivision() * _destructionPaths.length);
		return (result < 0) ? _destructionPaths.length : result;
	}

}
