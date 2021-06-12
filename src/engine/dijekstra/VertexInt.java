package engine.dijekstra;

import java.util.ArrayList;

public interface VertexInt extends Comparable<VertexInt> {

	public int getI();

	public int getJ();

	public ArrayList<EdgeInt> getAdjacencies();

	public void setAdjacencies(ArrayList<EdgeInt> adjacencies);

	public double getMinDistance();

	public void setMinDistance(double minDistance);

	public VertexInt getPrevious();

	public void setPrevious(VertexInt previous);

	public int compareTo(VertexInt other);

	public void clearVertex();
}
