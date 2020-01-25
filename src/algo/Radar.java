package algo;

import java.io.Serializable;

import circuit.Circuit;
import voiture.Voiture;

public interface Radar extends Serializable{
	
	public double[] scores(); // score de chaque branche
	public double[] getScore();//accesseur
	
	public int[] distancesInPixels(); // conversion en pixels (int)
	public int[] getDistancesPixels();//accesseur
	
	public int calcBestIndex();//calcul meilleur indice 
	public int getBestIndex(); // accesseur
	
	//accesseurs
	public double[] getAngles(); 
	public Voiture getVoit();
	public Circuit getCircuit();
}
