package mvc;

import circuit.Circuit;
import voiture.Voiture;

public class Modele {
	private Voiture voit;
	private Circuit circ;
	private double[] angles;
	
	//constructeur 
	public Modele(Voiture voit, Circuit circ, double[] angles) {
		this.voit=voit;
		this.circ=circ;
		this.angles=angles;
	}
	
	public void reinit (Voiture voit, Circuit circ) {
		this.voit=voit;
		this.circ=circ;
	}
	//getters et setters
	public double[] getAngles() {
		return angles;
	}

	public void setAngles(double[] angles) {
		this.angles = angles;
	}

	public Voiture getVoit() {
		return voit;
	}

	public void setVoit(Voiture voit) {
		this.voit = voit;
	}

	public Circuit getCirc() {
		return circ;
	}

	public void setCirc(Circuit circ) {
		this.circ = circ;
	}

}