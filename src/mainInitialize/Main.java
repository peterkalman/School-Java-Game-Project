package mainInitialize;

import unit.enemy.Enemy;
import unit.player.Player;
/**
 * The main class represents the starter of the game
 * @author Peter
 *
 */
public class Main {

	/**
	 * the main function , runs first initiates the game
	 */
	public static void main(String[] args) {
		MainMenu.init();
	}
	/*
	 * List of singletons
	 * MainMenu
	 * BackgroundImgPanel
	 * MapGFrame
	 * MapGPanel
	 * MapG
	 * CustomizeFrame
	 * CustomizeScreen
	 * DisplayScreen
	 */
	/*
	 * List of objectives:
	 * Diffrent Floor\ Wall layout	1 V
	 * Power ups	
	 * Health points	3 [ finished game]
	 * Win\Lose	2 
	 * New Projectiles
	 * Diffrent Enemies
	 * Diffrent Map Layouts
	 * Classes
	 */
	/**
	 *  adds the players to the global  player list and sets their places as occupied
	 */
	public static void initPlayers(){
		GameStats.addPlayer(new Player(64*1, 64*1, 64, 64));
		GameStats.addOccupied(1, 1);
		GameStats.addPlayer(new Enemy(64*13, 64*13, 64, 64));
		GameStats.addOccupied(13, 13);
	}
}
