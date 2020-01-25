package dijkstra;

import algo.RadarClassique;
import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import geometrie.Vecteur;
import voiture.Voiture;

public class RadarDijkstra extends RadarClassique{
	
	private Dijkstra dijk;
	private static final long serialVersionUID = 1L;

	public RadarDijkstra(double[] angles, Voiture voit, Circuit circ, Dijkstra dijk) {
		super(angles, voit, circ);
		this.dijk=dijk;
	}
	
	public double calcScore(double angle) {
		Vecteur p = voit.getPosition().clonage();
		Vecteur dir = voit.getDirection().clonage();
		dir = dir.rotation(angle);
		int score =  (int) Double.POSITIVE_INFINITY;
		int cpt = -1; //on initialise à -1 car on fait directement cpt++ après 
		
		
		while (TerrainTools.isRunnable(circ.getTerrainImage(p))) {
			cpt++;
			Vecteur nvllpos = Vecteur.additionNouveau(p, dir.multipleScal(EPS));	//EPS=0.1
			
			//si la voiture va en arrière alors qu'elle est sur la ligne d'arrivée
			if (circ.getTerrainImage(p) == Terrain.EndLine && Vecteur.produitScal(dir, circ.getDirectionArriveeImage()) <= 0) {
				return -Double.POSITIVE_INFINITY;
				
			}
			//on crée une variable représentant la distance à l'arrivée si on choisi l'angle donné
			int distTmp =  dijk.getDistance()[(int) nvllpos.getX()][(int) nvllpos.getY()];
			//si distTmp=0 ça veut dire qu'on est sur la ligne d'arrivée, on met un score maximal pour être sur qu'il va être choisi par le radar
			if (distTmp == 0) {
				distTmp = Integer.MAX_VALUE ;
			}
			//premier coup, on fixe le score, le tmp devrait être inférieur au score mis par défaut à +Infini
			if (distTmp < score && cpt==0) {
				score = distTmp;
			}
			//ce n'est plus le premier coup, on veut améliorer notre score
			if (cpt!=0 && distTmp>score) {
				score=distTmp;
			}
			
						
		}
		return score; 
	}


}
