package engine.dijekstra;

import java.util.ArrayList;

/**
 * Vertex is a class that represents a vertex in a graph
 * 
 * @author Peter
 */
public class Vertex implements VertexInt {
	private ArrayList<EdgeInt> _adjacencies;
	private double _minDistance = Double.POSITIVE_INFINITY;
	private VertexInt _previous;
	private int _i, _j;

	/**
	 * constructor
	 * 
	 * @param i
	 *            - the X coordinate of the vertex
	 * @param j
	 *            - the Y coordinate of the vertex
	 */
	public Vertex(int i, int j) {
		_i = i;
		_j = j;
		_adjacencies = new ArrayList<EdgeInt>();
	}

	@Override
	public int getI() {
		return _i;
	}

	@Override
	public int getJ() {
		return _j;
	}

	@Override
	public ArrayList<EdgeInt> getAdjacencies() {
		return _adjacencies;
	}

	@Override
	public void setAdjacencies(ArrayList<EdgeInt> adjacencies) {
		_adjacencies = adjacencies;
	}

	@Override
	public double getMinDistance() {
		return _minDistance;
	}

	@Override
	public void setMinDistance(double minDistance) {
		_minDistance = minDistance;

	}

	@Override
	public VertexInt getPrevious() {
		return _previous;
	}

	@Override
	public void setPrevious(VertexInt previous) {
		_previous = previous;
	}

	@Override
	public int compareTo(VertexInt other) {
		return Double.compare(_minDistance, other.getMinDistance());
	}

	/**
	 * Nullifies the vertex for clearing previous set paths
	 */
	@Override
	public void clearVertex() {
		setMinDistance(Double.POSITIVE_INFINITY);
		setPrevious(null);
	}

	@Override
	public String toString() {
		return _i + "," + _j;
	}

}
