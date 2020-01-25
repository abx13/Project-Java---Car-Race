package dijkstra;

import java.util.Comparator;

import geometrie.Vecteur;

public class ComparatorDijk implements Comparator<Vecteur>{
	private int[][] dist;

	public ComparatorDijk(int[][] distance) {	
		this.dist = distance;	
	}
	
	public int compare( Vecteur o1,  Vecteur o2) {
		if(dist[(int) o1.getX()][(int) o1.getY()]>dist[(int) o2.getX()][(int) o2.getY()])
			return 1;
		else if (dist[(int) o1.getX()][(int) o1.getY()]==dist[(int) o2.getX()][(int) o2.getY()])
			return 0;
		return -1;
	}
}
