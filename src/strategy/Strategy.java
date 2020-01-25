package strategy;
import java.io.Serializable;


import commande.Commande;

public interface Strategy extends Serializable{
	public Commande getCommande();	//retourne la commande à exécuter 

}
