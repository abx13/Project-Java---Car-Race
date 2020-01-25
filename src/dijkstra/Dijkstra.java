package dijkstra;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;



import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import geometrie.Vecteur;

public class Dijkstra {

	private Circuit circ;
	private int[][] distance;
	private PriorityBlockingQueue<Vecteur> q;
	private ArrayList<Vecteur> arrivee;
	Vecteur current;


	public Dijkstra(Circuit circ) {
		//instanciation du circuit et de la liste des points de la ligne d'arrivée
		this.circ = circ;
		arrivee = circ.getArrivees();
		
		//creation du tableau de distances
		distance = new int[circ.getHeight()][circ.getWidth()];
		
		//création de la priority Blocking queue 
		q = new PriorityBlockingQueue<Vecteur>(500, new ComparatorDijk(distance));
		
		//on mets directement la fonction dans le constructeur pour que ça se fasse automatiquement, sans besoin de l'appeler dans le main. 
		compute();
	}

	public void compute() {
		//initialisation du tableau de distances: les points de la ligne d'arrivée sont initialisés à 0 et les autres à la valeur maximale pour pouvoir ensuite les mettre a jour avec leur score
		for (int i = 0; i < distance.length; i++) {
			for (int j = 0; j < distance[0].length; j++) {
				if (arrivee.contains(new Vecteur (i, j))) {
					distance[i][j]=0;
					//intialisation  de la priority Blocking queue avec les points de la ligne d'arrivée
					q.add(new Vecteur(i,j));
				}else{
					distance[i][j] = Integer.MAX_VALUE;
				}
			}
		}
		
		while (q.size() != 0) {
			//on retire et on met a jour le noeud dont le score est le plus faible, c'est-à-dire celui qui est en tête
			//on enlève directement le vecteur qu'on va traiter de la priority blocking queue
			current = q.poll();
			maj(current);
			
		}
		System.out.println("fin compute dijkstra");
	}

	private void maj(Vecteur s) {
		Terrain[][] track= circ.getTerrain();
		int x = (int) s.getX();
		int y = (int) s.getY();
		//on cherche les voisins
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++) {
			
				//si le vecteur ne bouge pas (indice ininteressant)
				if ((i == 0 && j == 0)) {
					//System.out.println("ne bouge pas");
					continue;
				}
				//si le terrain n'est pas runnable
				if(!TerrainTools.isRunnable(track[x+i][y+i])) {
					//System.out.println("no runnable");
					continue;
				}
				//si sort du circuit
				if ((x + i) < 0 || (x + i) >= distance.length|| (y + j) < 0 || (y + j) >= distance[0].length) {
					//System.out.println("sort du circuit");
					continue;
				}
				
				//si du mauvais côté de la ligne d'arrivée: on veut faire la course en sens inverse de la voiture: on part de l'arrivée pour arriver au point de départ
				if (circ.getTerrain(x, y) == Terrain.EndLine) {
					if (Vecteur.produitScal(new Vecteur(i, j),circ.getDirectionArrivee()) > 0) {
						//System.out.println("mauvais cote ligne arrivée");
						continue;
					}
				}
				//on calcule le nouveau score en ajoutant le poids à la distance 
				int score = distance[x][y];
				int poids = poids(i,j);
				score+=poids;
				//le score ne doit pas être négatif car on parle de distance à la ligne d'arrivée et les distance ne peuvent pas être négatives
				if (score < 0) {
					System.out.println(score);
					
				}
				// on compare le score/distance avant la mise a jour avec le score après la mise a jour, si le nouveau score est inférieur on met à jour et on le rajoute dans la priority blocking queue 
				if (distance[x + i][y + j] > score) {
					//si jamais il est déjà dans la liste
					q.remove(new Vecteur(x + i, y + j));
					distance[x + i][y + j] = score;
					q.add(new Vecteur(x + i, y + j));
					//System.out.println("update");
				}
			}
	}
	
	//on calcule le poids pour mettre à jour le score selon si c'est une diagonale ou non
	private int poids(int i, int j) {
		// diagonales
		if (((i == -1) && (j == -1)) || ((i == 1) && (j == -1))|| ((i == -1) && (j == 1)) || ((i == 1) && (j == 1))) {
			return 14; 
		}
		//non diagonales
		return 10;
	}
	
	
		
	public int[][] getDistance() {
		return distance;
	}

	public Vecteur getCurrent() {
		return current;
	}

	public Circuit getCircuit() {
		return circ;
	}
}

