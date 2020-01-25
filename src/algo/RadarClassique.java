package algo;

import circuit.Circuit;
import circuit.Terrain;
import voiture.Voiture;
import circuit.TerrainTools;
import geometrie.Vecteur;

public class RadarClassique implements Radar{
	
	private static final long serialVersionUID = 1L;
	protected double[] angles;
	protected static final double EPS = 0.1;
	protected Voiture voit;
	protected Circuit circ;
	protected double[] scores;
	protected int bestIndex;
	protected int[] distPix;
	
	//Constructeur
	// on initialise les attributs directement dans le constructeur en faisant appel aux méthodes
	public RadarClassique(double[] angles,Voiture voit, Circuit circ) {
		this.angles=angles;
		this.voit=voit;
		this.circ=circ;
		//l'ordre des trois opérations suivantes est important
		this.scores = scores();
		this.distPix= distancesInPixels();
		this.bestIndex= calcBestIndex();
	}
	
	//créer le tableau de score pour chaque angle à partir de la méthode calcScore
	public double[] scores() {
		
		double[] scores= new double[angles.length];
		for (int i=0; i< angles.length; i++) {
			scores[i]= calcScore(angles[i]);
		}
		this.scores = scores;
		return scores;
	}
	
	//calculer le score pour l'angle donné
	private double calcScore(double angle) {
		//on réalise des clonages pour éviter de modifier les vraies valeurs de la voiture
		Vecteur p = voit.getPosition().clonage();
		Vecteur d= voit.getDirection().clonage();
		d= d.rotation(angle);
		
		int cpt=-1; //on initialise a -1 parce qu'on fait directement cpt++ après
		//tant que la voiture est sur un terrain où elle peut rouler on incrémente le compteur et on fait avancer la voiture dans la direction donnée par l’angle donné 
		while(TerrainTools.isRunnable(circ.getTerrainImage((int)p.x, (int)p.y))){
			cpt++;
			p= Vecteur.additionNouveau(p, d.multipleScal(EPS)) ;
			//si elle va en arrière on fait que le score soit le plus petit possible pour qu'il ne soit jamais choisi par le radar (-Infinity)
			if (circ.getTerrainImage((int)p.x, (int)p.y)==Terrain.EndLine && Vecteur.produitScal(d, circ.getDirectionArrivee()) <= 0) {
				return Double.NEGATIVE_INFINITY;
			}
			//si on arrive a la ligne d'arrivée on fait que le socore soit le plus grand possible pour qu'il soit obligatoirement choisi par le radar
			if (circ.getTerrainImage((int)p.x, (int)p.y)== Terrain.EndLine && Vecteur.produitScal(d, circ.getDirectionArrivee()) >= 0) {
				return Double.POSITIVE_INFINITY;
			}
						
		}
		return cpt;// le compteur = le score 
}

	//on converti le score en nb de pixels, notre pas étant EPS= 0.1
	public int[] distancesInPixels() {
		
		int[] distPix = new int[scores.length];
		for (int i=0; i<scores.length; i++){
			distPix[i]= (int) (scores[i]* EPS);
			if (distPix[i] < 0) {
				distPix[i] = 0;
			}
		}
		this.distPix=distPix;
		return distPix;
	}

	//on cherche l’indice du meilleur score pour savoir quel est l’angle qui nous amène le plus loin
	public int calcBestIndex() {
		
		int bestIndex = 0;	//on initialise l’indexe à 0 par défaut
		for (int i=0; i<this.angles.length; i++) {	//on parcourt tout le tableau de score pour trouver le meilleur score
			if (this.scores[i]>this.scores[bestIndex]) {
				bestIndex=i;
			if (scores[i] == scores[bestIndex])	//si deux cases du tableau de score ont la même valeur on fait une comparaison selon la valeur du tableau converti en pixels
					if (distPix[i] > distPix[bestIndex])
						bestIndex = i;
			}
		}
		this.bestIndex=bestIndex;
		return bestIndex;
		
	}
	
	//accesseurs
	
	public double[] getAngles() {
		return angles;
	}
	
	
	public Voiture getVoit() {
		return voit;
	}
	
	public double[] getScore() {
		return scores;
	}

	public Circuit getCircuit() {
		return circ;
	}
	
	public int[] getDistancesPixels() {
		return distPix;
	}
	
	public int getBestIndex() {
		return bestIndex;
	}
	
	
	
}


