package engine.dijekstra;

import java.util.*;

import engine.dijekstra.EdgeInt;
import engine.dijekstra.VertexInt;

/**
 * Class for Dijkstra
 * 
 * @author Peter
 */

public class Dijekstra {
	/**
	 * Find the best short tracks enemy (policeman) to each of the other vertices
	 * 
	 * @param source
	 *            -the vertex we want to start the procedure from
	 */

	public static void computePaths(VertexInt source) {
		source.setMinDistance(0.0);
		PriorityQueue<VertexInt> vertexQueue = new PriorityQueue<VertexInt>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			VertexInt current = vertexQueue.poll();

			for (EdgeInt e : current.getAdjacencies()) {
				VertexInt target = e.getTarget();
				double distance = e.getWeight();
				double distanceThroughU = current.getMinDistance() + distance;

				if (distanceThroughU < target.getMinDistance()) {
					vertexQueue.remove(target);
					target.setMinDistance(distanceThroughU);
					target.setPrevious(current);
					vertexQueue.add(target);
				}
			}
		}
	}

	/**
	 * returns the shortest path to target target-destination
	 * 
	 * @param target
	 *            - the the shortest path to target from the previous given computePaths
	 */

	public static LinkedList<VertexInt> getShortestPathTo(VertexInt target) {
		LinkedList<VertexInt> path = new LinkedList<VertexInt>();
		for (VertexInt vertex = target; vertex != null; vertex = vertex.getPrevious()) {
			path.add(vertex);
		}

		Collections.reverse(path);
		return path;
	}
}
