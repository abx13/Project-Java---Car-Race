package selector;

import java.util.ArrayList;

import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import geometrie.Vecteur;
import voiture.Voiture;

public class SelectorPointAPoint implements Selector{
	private Voiture voit;
	private Circuit circ;
	private ArrayList<Vecteur> listePoints = new ArrayList<Vecteur>();
	private int score;
	private static double EPS = 0.1;
	private static final int ZONE = 30;

	public SelectorPointAPoint(Circuit circ, Voiture voit, ArrayList<Vecteur> listePoints) {
		this.voit = voit;
		this.circ = circ;
		for (int i=0; i<listePoints.size(); i++) {
			Vecteur p = listePoints.get(i);
			this.listePoints.add(p);
		}
			
		score = 0;
	}

	public boolean isSelected() {
		//il n'y a plus de points dans la liste de points 
		if (listePoints.size() <= 0)
			return false;
		//la voiture n'atteindra jamais exactement le point: on définit une zone où on considère que le point est atteint, on le retire donc de la liste
		if (Vecteur.calculNorme(Vecteur.soustractionNouveau(listePoints.get(0), voit.getPosition())) < ZONE)
			listePoints.remove(0);

		int cpt = -1;	//on initialise a -1 car on fait cpt++ directement après
		Vecteur pos = voit.getPosition().clonage();
		Vecteur directionVersPoint = new Vecteur(pos, listePoints.get(0).normalisationVecteur());

		 while (TerrainTools.isRunnable(circ.getTerrain(pos))){
			 //si on est sur la ligne d'arrivée et que le point est derrière nous
			 if (circ.getTerrain(pos) == Terrain.EndLine && Vecteur.produitScal(directionVersPoint, circ.getDirectionArrivee()) < 0)
				return false;

			cpt++;
			Vecteur.additionNouveau(pos, directionVersPoint.multipleScal(EPS));
			//si on arrive dans la zone où on considère que la voiture a atteint le point, on valide la strategie et on donne le score pour y arriver
			if (Vecteur.calculNorme(Vecteur.soustractionNouveau(listePoints.get(0), pos)) < ZONE) {
				score = (int) (cpt * EPS);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Vecteur> getListePoints() {
		return listePoints;
	}

	public int getScore() {
		return score;
	}

}