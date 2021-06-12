package mainInitialize;

import java.awt.Point;
import java.util.LinkedList;

import engine.Block;
import engine.dijekstra.Dijekstra;
import engine.input.KeyboardInput;
import map.MapGFrame;
import map.MapGPanel;
import unit.MovingUnit;
import unit.WinningInterface;
import unit.customizeScreen.CustomizeFrame;
import unit.enemy.Enemy;
import unit.player.Player;

/**
 * Gamestats is a class that represents a global overview on the game's statistics as well
 * containing information about every object on the map.
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class GameStats {
	private static LinkedList<MovingUnit> _players;
	private static LinkedList<Block> _fullWalls;
	private static LinkedList<Block> _halfWalls;
	private static LinkedList<Point> _playerOccupied;
	// private static GameStats singleton = new GameStats();
	public final static int _height = 15;
	public final static int _width = 15;
	
	private static int _playerWon=0;
	private static int _enemyWon=0;

	/**
	 * Constructor is private because of singleton
	 */
	private GameStats() {
		_players = new LinkedList<MovingUnit>();
		_fullWalls = new LinkedList<Block>();
		_halfWalls = new LinkedList<Block>();
		_playerOccupied = new LinkedList<Point>();
	}

	/**
	 * adds a unit to the MovingUnit list
	 * 
	 * @param player
	 *            - the unit we want to add
	 */
	public static void addPlayer(MovingUnit player) {
		_players.add(player);
	}

	/**
	 * @return the linkedlist with all MovingUnit
	 */
	public static LinkedList<MovingUnit> getPlayers() {
		return _players;
	}

	/**
	 * add fullWall block to the fullwall list
	 * 
	 * @param fullWall
	 *            - the Block we want to add
	 */
	public static void addFullWall(Block fullWall) {
		_fullWalls.add(fullWall);
	}

	/**
	 * @return the linkedlist with all FullWall blocks
	 */
	public static LinkedList<Block> getFullWalls() {
		return _fullWalls;
	}

	/**
	 * removes fullwall block from the list (uses comparison based on addresses)
	 * 
	 * @param fullWall
	 *            - the fullWall block we want to remove from the list
	 * @return true if successfuly removed the block, else false.
	 */
	public static boolean removeFullWall(Block fullWall) {
		if (_fullWalls.contains(fullWall)) {
			return _fullWalls.remove(fullWall);
		}
		return false;
	}

	/**
	 * add halfWall block to the halfwall list
	 * 
	 * @param halfWall
	 *            - the Block we want to add
	 */
	public static void addHalfWall(Block halfWall) {
		_halfWalls.add(halfWall);
	}

	/**
	 * @return the linkedlist with all HalfWall blocks
	 */
	public static LinkedList<Block> getHalfWalls() {
		return _halfWalls;
	}

	/**
	 * removes halfwall block from the list (uses comparison based on addresses)
	 * 
	 * @param halfWall
	 *            - the halfWall block we want to remove from the list
	 * @return true if successfuly removed the block, else false.
	 */
	public static boolean removeHalfWall(Block halfWall) {
		if (_halfWalls.contains(halfWall)) {
			return _halfWalls.remove(halfWall);
		}
		return false;
	}

	/**
	 * add occupiedPoint block to the playerOccupied list
	 * 
	 * @param p
	 *            - the point we want to add
	 */
	public static void addOccupied(Point p) {
		if (!_playerOccupied.contains(p))
			_playerOccupied.add(p);
	}

	/**
	 * add occupiedPoint block to the playerOccupied list
	 * 
	 * @param i
	 *            - the X coordinate of the point
	 * @param j
	 *            - the Y coordinate of the point
	 */
	public static void addOccupied(int i, int j) {
		addOccupied(new Point(i, j));
	}

	/**
	 * remove occupied Point from the playerOccupied list
	 * 
	 * @param i
	 *            - the X coordinate of the point we want to remove
	 * @param j
	 *            - the Y coordinate of the point we want to remove
	 * @return true if removed successfuly else false.
	 */
	public static boolean removeOccupied(int i, int j) {
		if (occupiedExists(i, j))
			return _playerOccupied.remove(new Point(i, j));
		return false;
	}

	/**
	 * check if point with similar coordinates exists in playerOccupied List
	 * 
	 * @param i
	 *            - the X coordinate of the point
	 * @param j
	 *            - the Y coordinate of the point
	 * @return
	 */
	public static boolean occupiedExists(int i, int j) {
		return (_playerOccupied.contains(new Point(i, j)));
	}

	/**
	 * calls pauseUnit for all moving units that are registered in the Global MovingUnit list.
	 */
	public static void pauseGame() {
		for (MovingUnit mu : _players) {
			((WinningInterface) mu).pauseUnit();
		}
	}

	/**
	 * calls startUnit for all moving units that are registered in the Global MovingUnit list.
	 */
	public static void startGame() {
		for (MovingUnit mu : _players) {
			((WinningInterface) mu).startUnit();
		}
	}
	/**
	 * Creates a new MapGPanel which generates a completly new map 
	 * @param newMap - if we want to create a new map or just restart the previous one
	 */
	public static void generateNewPanel(boolean newMap) {
		if (MapGPanel.getInstance() != null) {
			MapGFrame.getInstance().remove(MapGPanel.getInstance());
		}
		MapGFrame.getInstance().add(MapGPanel.init(15, 15, 64, newMap));
	}
	/**
	 * empties all of the global lists and initiates all positions of the Units
	 * if there is no custom graphics its sets a default
	 */
	public static void restartPositions() {
		new GameStats();
		Main.initPlayers();
		if (CustomizeFrame.getInstance() != null) {
			((Player) GameStats.getPlayers().get(0)).setCustomGraphics(64, 64);// set default custom
																				// graphics
		}
	}
	/**
	 * adds the players' panel into the panel of the map
	 */
	public static void addUnitPanels() {
		for (MovingUnit mu : GameStats.getPlayers()) {
			MapGPanel.getInstance().add(mu);
		}
	}
	/**
	 * Restarts the entire game with a choise of creating a new map or keeping the last one.
	 * @param newMap-if true , it creates a new map else it keeps the last one
	 */
	public static void restartGame(boolean newMap) {
		// KeyboardInput.getInstance().cleanListenerList();
		KeyboardInput.getInstance().resetAllKeys();
		restartPositions();
		generateNewPanel(newMap);
		addUnitPanels();
		pauseGame();
		MapGFrame.getInstance().setVisible(false);
		MapGFrame.getInstance().setVisible(true);
		MapGFrame.getInstance().setFocusable(true);
	}
	/**
	 * speeding up the enemy's timer and slowing down the player's timer
	 */
	public static void increaseDifficaulty() {
		if (Enemy._timerTicker > 5)
			Enemy._timerTicker -= 2;
		if (Player._timerTicker < 100)
			Player._timerTicker += 2;
	}
	/**
	 * slowing down the enemy's timer and speeding up the player's timer
	 */
	public static void decreaseDifficaulty() {
		if (Enemy._timerTicker < 70)
			Enemy._timerTicker += 3;
		if (Player._timerTicker > 4)
			Player._timerTicker -= 2;
	}
	/**
	 * increase by 1 the matches won by the player 
	 */
	public static void playerWonInc(){
		_playerWon++;
	}
	/**
	 * increase by 1 the matches won by the enemy
	 */
	public static void enemyWonInc(){
		_enemyWon++;
	}
	/**
	 * @return the matches won by player
	 */
	public static int getPlayerWon() {
		return _playerWon;
	}
	/**
	 * @return the matches won by enemy
	 */
	public static int getEnemyWon() {
		return _enemyWon;
	}
	
	
}
