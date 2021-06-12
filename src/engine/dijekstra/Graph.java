package engine.dijekstra;

import java.util.ArrayList;

import unit.enemy.Enemy;

import engine.Block;
import mainInitialize.GameStats;
import map.MapG;

/**
 * Graph contains all of the vertices and manipulates and connects every vertex with all other
 * neighbor vertices through using edges
 * 
 * @author Peter
 */
public class Graph implements GraphInt {

	private int[][] _isBlockedMat;
	private VertexInt[][] _matVert;
	private int _size;
	private int _sizeW;
	public static int speedToDestroyTimeDiff = 5;// 120/((Enemy)(GameStats.getPlayers().getLast())).getDamageToWall();
	private boolean first = true;

	/**
	 * @param size
	 *            - the amount of rows in the graph
	 * @param sizeW
	 *            - the amount of cols in the graph
	 */
	public Graph(int size, int sizeW) {
		_size = size;
		_sizeW = sizeW;
		_isBlockedMat = new int[_size][_sizeW];
		_matVert = new VertexInt[_size][_sizeW];
		for (int i = 0; i < _size; i++)
			for (int j = 0; j < _sizeW; j++) {
				_matVert[i][j] = new Vertex(i, j);
			}

	}

	/**
	 * get the Vertex matrix of this graph
	 */
	@Override
	public VertexInt[][] getGraph() {
		return _matVert;
	}

	/**
	 * Check if the coordinates are registered in the full wall list
	 * 
	 * @param i
	 *            - the X coordinate
	 * @param j
	 *            - the Y coordinate
	 * @return true if it exists else false.
	 */
	private boolean checkIfIndexFullBlocked(int i, int j) {
		for (Block temp : GameStats.getFullWalls()) {
			if (temp.getX() == i && temp.getY() == j)
				return true;
		}
		return false;
	}

	/**
	 * Check if the coordinates are registered in the half wall list
	 * 
	 * @param i
	 *            - the X coordinate
	 * @param j
	 *            - the Y coordinate
	 * @return true if it exists else false.
	 */
	private boolean checkIfIndexHalfBlocked(int i, int j) {
		for (Block temp : GameStats.getHalfWalls()) {
			if (temp.getX() == i && temp.getY() == j)
				return true;
		}
		return false;
	}

	/**
	 * add an adjeceny from one vertex to another based on the coordinates.
	 * 
	 * @param origI
	 *            - the X coordinate of the source
	 * @param origJ
	 *            - the Y coordinate of the source
	 * @param i
	 *            - the X coordinate of the dest
	 * @param j
	 *            - the Y coordinate of the dest
	 */
	public void addAdjaceny(int origI, int origJ, int i, int j) {
		_matVert[origI][origJ].getAdjacencies()
				.add(new Edge(_matVert[i][j], (checkIfIndexHalfBlocked(i, j)) ? speedToDestroyTimeDiff : 1));
	}

	/**
	 * connects every vertex to all other nearby vertices
	 */
	@Override
	public void buildGraph() {
		for (int i = 1; i < _size - 1; i++) {
			for (int j = 1; j < _sizeW - 1; j++) {
				if (!MapG.get_map()[i][j].isFull()) {
					_matVert[i][j].getAdjacencies().clear();
					if (!MapG.get_map()[i - 1][j].isFull()) {
						addAdjaceny(i, j, i - 1, j);
					}
					if (!MapG.get_map()[i + 1][j].isFull()) {
						addAdjaceny(i, j, i + 1, j);
					}
					if (!MapG.get_map()[i][j - 1].isFull()) {
						addAdjaceny(i, j, i, j - 1);
					}
					if (!MapG.get_map()[i][j + 1].isFull()) {
						addAdjaceny(i, j, i, j + 1);
					}
				}
			}
		}

	}

}
