package engine.dijekstra;

import java.util.LinkedList;

import engine.Unit;
import mainInitialize.GameStats;
import unit.MovingUnit;
import unit.ObjectOnMap;

/**
 * Smartmove is responsible to give the shortest path towards the player from the prespective of the
 * enemy.
 * 
 * @author Peter
 */
public class SmartMove {
	private Graph _graph;
	private int _unitX;
	private int _unitY;
	private LinkedList<VertexInt> ans = new LinkedList<VertexInt>();

	/**
	 * Constructor Initiate the graph
	 */
	public SmartMove() {
		_graph = new Graph(GameStats._height, GameStats._width);

	}

	/**
	 * receives the unit to start the pathing from and receives dest coordinates.
	 * 
	 * @param unit
	 *            - the Unit to start pathing from
	 * @param destX
	 *            -the dest X coordinate
	 * @param destY
	 *            - the dest Y coordinate
	 * @return -1 if no move was suggested , 0 down,1 left ,2 up,3 right
	 */
	public int getSmartMove(Unit unit, int destX, int destY) {

		_unitX = unit.getCurrentX();
		_unitY = unit.getCurrentY();
		_graph.buildGraph();// clears all adjacencies and rebuilds graph in case
							// of destruction.

		// _graph.getGraph()[_unitX][_unitY].startVertex();

		for (int i = 0; i < GameStats._height; i++)
			for (int j = 0; j < GameStats._width; j++)
				_graph.getGraph()[i][j].clearVertex();

		Dijekstra.computePaths(_graph.getGraph()[_unitX][_unitY]);// redirects
																	// all of
																	// the paths
																	// from the
																	// given
																	// vector

		ans = Dijekstra.getShortestPathTo(_graph.getGraph()[destX][destY]);

		// System.out.println(ans);
		// if ans is empty we reached target. or no moves possible
		if (ans.size() > 1) {
			ans.removeFirst();
			VertexInt ansVert = ans.getFirst();
			if (ansVert.getI() == _unitX) {
				if (ansVert.getJ() == _unitY + 1)
					return 0;// down
				return 2;// up
			}
			if (ansVert.getI() == _unitX + 1)
				return 1;// left
			return 3;// right
		}
		return -1;
	}

	public Graph getGraph() {
		return _graph;
	}

}
