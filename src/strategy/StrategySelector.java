package strategy;

import java.util.ArrayList;

import commande.Commande;
import selector.Selector;

public class StrategySelector implements Strategy{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Strategy> listeStrategy;
	private ArrayList<Selector> listeSelector;
	private Strategy stratRadar;
	
	public StrategySelector(Strategy stratRadar) {
		listeStrategy= new ArrayList<Strategy> ();
		listeSelector= new ArrayList<Selector> ();
		this.stratRadar=stratRadar;
	}
	
	public void add(Strategy strat, Selector select) {
		listeStrategy.add(strat);
		listeSelector.add(select);
	}
	
	
	//on choisit la stratégie choisie par le selecteur et si aucune des stratégie n'est possible, on utilise la stratégie radar par défaut
	@SuppressWarnings("unused")
	public Commande getCommande() {
		for (int i=0; i<listeStrategy.size();i++) {
			if(listeSelector.get(i).isSelected()) {
				return listeStrategy.get(i).getCommande();
			}
			else {
				return stratRadar.getCommande();
			}
		}
		//normalement on ne passe pas par ici...
		return listeStrategy.get(listeStrategy.size()-1).getCommande();
	}
}
