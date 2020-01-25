package circuit;

import java.io.Serializable;
import java.util.ArrayList;

import geometrie.Vecteur;

public interface Circuit extends Serializable {
	//accesseurs de la matrice
	public Terrain getTerrain(int i, int j);
	public Terrain getTerrain(Vecteur v);
	//accesseurs de la matrice avec inversion des X et Y
	public Terrain getTerrainImage(int x, int y);
	public Terrain getTerrainImage(Vecteur v);
	public Terrain[][] getTerrain();
	public Terrain[][] getTerrainImage();
	//accesseur des données du circuit
	public Vecteur getPointDepart();
	public Vecteur getDirectionDepart();
	public Vecteur getDirectionArrivee();
	public Vecteur getDirectionArriveeImage();
	public Vecteur getDirectionDepartImage();
	public Vecteur getPointDepartImage();
	public int getWidth();
	public int getHeight();
	public ArrayList<Vecteur> getArrivees();
	//calcul de la distance entre 2 coordonées
	public double getDist(int i, int j) ;

	
}
