package selector;

import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import geometrie.Vecteur;
import voiture.Voiture;

public class SelectorArrivee implements Selector {
	private Voiture voit;
	private Circuit circ;
	private boolean[] arriveeVisibleBool;
	private int[] score;
	private static final double EPS=0.1;

	public SelectorArrivee(Voiture voit, Circuit circ) {
		super();
		this.voit = voit;
		this.circ = circ;
		this.arriveeVisibleBool = new boolean[circ.getArrivees().size()];
		this.score = new int[circ.getArrivees().size()];
	}
	//on ne choisit cette stratégie que si l'arrivée est visible 
	public boolean isSelected() {
		arriveeVisible();	//initialise les attributs pour savoir si l'arrivée est visible ou pas
		for (int i = 0; i < arriveeVisibleBool.length; i++) {
			if (arriveeVisibleBool[i]==true) {
				return true;
			}
		}
		return false;
	}

	private void arriveeVisible() {
		
		for (int i = 0; i < arriveeVisibleBool.length; i++) {
			//initialisation par défaut
			int cpt = -1;
			score[i] = (int) Double.NEGATIVE_INFINITY;
			arriveeVisibleBool[i] = false;
			
			//creation d'un vecteur à partir de la position de la voiture et du point d'arrivée
			Vecteur pos = voit.getPosition().clonage();
			Vecteur dirArrivee = new Vecteur(pos, circ.getArrivees().get(i));
			dirArrivee.normalisationVecteur();
			
			//si la direction est la même que la direction d'arrivée du circuit
			if (Vecteur.produitScal(dirArrivee, circ.getDirectionArrivee()) >= 0) {
				//si le point d'arrivée est à l'avant de la voiture et non pas à l'arrière
				if (Vecteur.produitScal(voit.getDirection(), circ.getDirectionArrivee()) >= 0) {
					 while (TerrainTools.isRunnable(circ.getTerrain(pos))) {
						cpt++;
						//on met a jour la nouvelle position de la voiture
						pos = Vecteur.additionNouveau(pos,dirArrivee.multipleScal(EPS));
						//si la nouvelle position arrive à l'arrivée, on met à jour le tableau de boolean et le score
						if (circ.getTerrain(pos) == Terrain.EndLine) {
							arriveeVisibleBool[i] = true;
							score[i] = cpt;
							break;
						}
					}
				}
			}
		}
	}
	
	public int[] getScore() {
		return score;
	}
}