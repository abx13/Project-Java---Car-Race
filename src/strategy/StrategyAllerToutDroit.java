package strategy;

import commande.Commande;

public class StrategyAllerToutDroit implements Strategy{
	
	private static final long serialVersionUID = 1L;//pour que ce soit serializable

	public Commande getCommande() {		//retourne la commande à exécuter
		return new Commande(1,0);
	}

}
