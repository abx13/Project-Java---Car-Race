package strategy;

import selector.Selector;
import selector.SelectorArrivee;
import circuit.Circuit;
import commande.Commande;
import geometrie.Vecteur;
import voiture.Voiture;

public class StrategyArrivee implements Strategy {
	private static final long serialVersionUID = 1L;
	private Selector selector;
	private Circuit circ;
	private Voiture voit;
	private int bestScore = 0;
	private Vecteur directionBestArrivee = new Vecteur(0, 0);
	private final static double EPS=0.1;

	public StrategyArrivee(Circuit circ, Voiture voit, Selector selector) {
		this.circ = circ;
		this.voit = voit;
		this.selector = selector;
	}

	private void direction() {
		//on reprend le tableau de score calculé par le selector
		int[] score = ((SelectorArrivee) selector).getScore();
		//on cherche le meilleur indice
		int bestIndex = 0;
		for (int i = 1; i < score.length; i++)
			if (score[bestIndex] > score[i])
				bestIndex = i;
		//on établit le meilleur score
		bestScore = ((int) (score[bestIndex] * EPS));
		//on établit la direction la plus rapide pour aller à l'arrivee
		directionBestArrivee = new Vecteur(voit.getPosition(), circ.getArrivees().get(bestIndex));
	}

	public Commande getCommande() {
		direction();	//initialisation des attributs de la classe
		double turn = (Vecteur.calculAngle(voit.getDirection(), directionBestArrivee.normalisationVecteur()))/ voit.getBraquage();
		double acc;
		//optimisation : la rotation doit entre comprise entre 1 et -1 et si la rotation est maximale on décélère pour pouvoir la faire
		if (turn > 1) {
			turn = 1;
			acc = -1;
		}
		if (turn < -1) {
			turn = -1;
			acc = -1;
		}
		//optimisation : adaptation de l'acceleration selon la rotation
		if (Math.abs(turn) > Math.PI / 3)
			acc = -1 * Math.abs(turn); //acceleration négative car la rotation est forte
		else
			acc = 1 - Math.abs(turn);	//acceleration positive car la rotation n'est pas si forte
		
		return new Commande(turn, acc);
	}

	public Vecteur getDirection() {
		return directionBestArrivee;
	}

	public int getBestScore() {
		return bestScore;
	}

}