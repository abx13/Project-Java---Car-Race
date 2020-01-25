package strategy;


import java.util.ArrayList;


import commande.Commande;


public class StrategyListCommande implements Strategy{
	
	private static final long serialVersionUID = 1L;	//pour que ce soit serializable
	private ArrayList <Commande> liste; //un tableau de commandes
	private static int index=0;		    //l’index du tableau
	
	
	//construcuteur du tableau, l’index est initialisé à 0 par défaut
	public StrategyListCommande(ArrayList <Commande> liste) {
		this.liste=liste;
	}
	
	//retourne la commande stocké dans le tableau à l’index donné si et seulement si l’index est inférieur à la taille du tableau; car sinon il n’y a pas de commande dans le tableau
	public Commande getCommande() {
		if (index == liste.size()) return null;
		Commande com = liste.get(index);
		index++;
		return com;
	}



}
