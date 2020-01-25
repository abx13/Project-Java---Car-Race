package strategy;

import selector.Selector;
import selector.SelectorPointAPoint;
import commande.Commande;
import geometrie.Vecteur;
import voiture.Voiture;

public class StrategyPointAPoint implements Strategy {
	
	private static final long serialVersionUID = 1L;
	private Voiture voit;
	private SelectorPointAPoint selector;
	private Vecteur directionVersPoint;

	public StrategyPointAPoint(Voiture voit, Selector selector) {
		this.voit = voit;
		this.selector = (SelectorPointAPoint) selector;
		directionVersPoint = null;
	}

	private Vecteur directionVersPoint() {
		return new Vecteur(voit.getPosition(), selector.getListePoints().get(0));
	}

	
	public Commande getCommande() {
		
		directionVersPoint = directionVersPoint();
		double turn = (Vecteur.calculAngle(voit.getDirection(), directionVersPoint)) / voit.getBraquage();
		double acc = 1;
		//optimisation: la rotation doit entre comprise entre 1 et -1 et si la rotation est maximale on décélère pour pouvoir la faire
		if (turn > 1) {
			turn = 1;
			acc = -1;
		}
		if (turn < -1) {
			turn = -1;
			acc = -1;
		}
		//optimisatisation : on modifie l'accéleration selon si l'angle de rotation est très fort
		if (Math.abs(turn) > Math.PI / 8)
			acc = 0;

		return new Commande(turn, acc);
	}

	public Vecteur getDirectionVersPoint() {
		return directionVersPoint;
	}

	public int getScore() {
		return selector.getScore();
}
}