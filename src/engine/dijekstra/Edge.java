package engine.dijekstra;
/**
 * The edge represents the one side connection between two vertices
 * @author Peter
 *
 */
public class Edge implements EdgeInt {
	private final VertexInt _target;
	private final double _weight;
	/**
	 * constructor
	 * @param target - the target Vertex
	 * @param weight - the weight of the edge
	 */
	public Edge(VertexInt target, double weight) {
		_target = target;
		_weight = weight;
	}
	/**
	 * @return the target Vertex of this edge
	 */
	@Override
	public VertexInt getTarget() {
		return _target;
	}
	/**
	 * @return the weight of this edge
	 */
	@Override
	public double getWeight() {
		return _weight;
	}
	/**
	 * @return the string representing the Target and weight
	 */
	@Override
	public String toString() {
		return "T:" + _target + " W:" + _weight;
	}
}
