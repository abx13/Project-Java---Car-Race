package circuit;

import java.util.ArrayList;

import geometrie.Vecteur;

public class CircuitImpl implements Circuit {
	
	private static final long serialVersionUID = 1L;
	private Terrain[][] track;
	private Vecteur depart;
	private Vecteur dirDepart, dirArrivee;
	
	public CircuitImpl(Terrain[][] track) {
		this.track=track;
	}
	
	public CircuitImpl(Terrain[][] track, Vecteur depart, Vecteur dirDepart, Vecteur dirArrivee) {
		this.track=track;
		this.depart=depart;
		this.dirDepart=dirDepart;
		this.dirArrivee=dirArrivee;
	}
	
	public Terrain[][] getTerrain() {
		return this.track;
	}
	
	
	public Terrain[][] getTerrainImage() {
		Terrain [][] terrainImage = new Terrain [this.getWidth()][this.getHeight()];
		for (int i=0; i<terrainImage.length; i++) {
				for (int j=0; j<terrainImage[0].length; j++) {
						terrainImage[i][j]=track[j][i];
				}
		}
		return terrainImage;
	}
	
	
	public Terrain getTerrain(int i, int j) {
		return this.track[i][j];
	}
	
	public Terrain getTerrainImage(int i, int j) {
		return this.track[j][i];
	}
	
	public Terrain getTerrain(Vecteur v) {
		return this.track[(int)v.x][ (int) v.y];
	}
	
	public Terrain getTerrainImage(Vecteur v) {
		return this.track[(int)v.y][ (int) v.x];
	}
	
	public Vecteur getPointDepart() {
		return this.depart;
	}
	
	public Vecteur getPointDepartImage() {
		return new Vecteur(depart.getY(), depart.getX());
	}
	
	public Vecteur getDirectionDepart() {
		return this.dirDepart;
	}
	
	public Vecteur getDirectionDepartImage() {
		return new Vecteur(dirDepart.getY(), dirDepart.getX());
		
	}
	public Vecteur getDirectionArrivee() {
		return this.dirArrivee;	
	}
	
	public Vecteur getDirectionArriveeImage() {
		return new Vecteur(dirArrivee.getY(), dirArrivee.getX());
		
	}
	
	public int getWidth() {
		return this.track[0].length;
	}
	public int getHeight() {
		return this.track.length;
	}
	

	public ArrayList<Vecteur> getArrivees() {
		  ArrayList<Vecteur> endLine=new ArrayList<Vecteur>();
		  for(int i=0;i<track.length;i++) {
			  for(int j=0;j<track[0].length;j++) {
				  if(track[i][j]==Terrain.EndLine) {
					  endLine.add(new Vecteur(i,j));	
				  }
			  }
		  }
		  return endLine;
	}
	
	public double getDist(int i, int j) {
		Vecteur v = new Vecteur (i,j);
		return v.calculNorme();
	}
	
}
