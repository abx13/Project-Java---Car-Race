package strategy;

import commande.Commande;

import algo.Radar;


public class StrategyRadar implements Strategy{
	
	private static final long serialVersionUID = 1L;
	private Radar r;

	public StrategyRadar (Radar r) {
		this.r = r;
	}
	
	public Commande getCommande() {
		r.scores();
		r.calcBestIndex();
		//System.out.println("bestIndex ="+ r.calcBestIndex());
		return new Commande(1,r.getAngles()[r.getBestIndex()]*2/Math.PI);
		
	}
	
	
	
}
