package mvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import algo.Radar;
import algo.RadarClassique;
import commande.Commande;
import dijkstra.Dijkstra;
import dijkstra.RadarDijkstra;
import li260.view.observeurs.DijkstraObserveur;
import li260.view.observeurs.RadarObserveur;
import li260.view.observeurs.TrajectoireObserveur;
import li260.view.observeurs.VoitureObserveur;
import li260.view.observeurs.VoitureObserveurAvance;
import selector.Selector;
import selector.SelectorArrivee;
import selector.SelectorPointAPoint;
import simulation.SimulationMVC;
import strategy.Strategy;
import strategy.StrategyArrivee;
import strategy.StrategyListCommande;
import strategy.StrategyPointAPoint;
import strategy.StrategyRadar;
import strategy.StrategySelector;

public class Lancer {
	
	public Lancer() {}
	
	public void lancer(IHMSwing controleurSwing, Vue vue, Modele modele) {
		
		
		//attendre que l'utilisateur ait cliqué sur la stratégie qu'il veut. 
		do
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!controleurSwing.getListeCommandeGO()  && 
				 !controleurSwing.getRadarClassiqueGO() &&
				 !controleurSwing.getRadarDijkstraGO()  &&
				 !controleurSwing.getSelectorGO() 			);
		
		Strategy strat=null;
		String filenameSave = null;
		
		//on lance la stratégie choisie par l'utilisateur
		if(controleurSwing.getListeCommandeGO()) {
			//declaration Stratégie ListeCommades
			ArrayList <Commande> com1 = new ArrayList <Commande>();
			for (int i=0; i<150; i++ ) com1.add(new Commande(1,0));
			for (int i=0; i<50; i++ ) com1.add(new Commande(0.75,-Math.PI/6.0*2/Math.PI));
			for (int i=0; i<200; i++ ) com1.add(new Commande(1,0));
			//declaration de la stratégie 
			strat = new StrategyListCommande(com1);
			
			filenameSave="Simu Final Liste Commandes";
			
		}else if(controleurSwing.getRadarClassiqueGO()) {
			//declaration du radar et de la stratégie 
			RadarClassique r= new RadarClassique(modele.getAngles(), modele.getVoit(), modele.getCirc());
			strat = new StrategyRadar(r);
			//declaration de l'observeur et son ajout dans la liste de listenersSwing
			RadarObserveur ro = new RadarObserveur(r);
			VoitureObserveur voito=null;
			TrajectoireObserveur trajecto =null;
			VoitureObserveurAvance voitoav=null;
			for (int i=0; i<controleurSwing.getListeObserveurs().size(); i++) {
				if (controleurSwing.getListeObserveurs().get(i) instanceof VoitureObserveur) {
					voito = (VoitureObserveur) controleurSwing.getListeObserveurs().get(i);
				} else if(controleurSwing.getListeObserveurs().get(i) instanceof VoitureObserveurAvance) {
					voitoav = (VoitureObserveurAvance) controleurSwing.getListeObserveurs().get(i);
				} else if(controleurSwing.getListeObserveurs().get(i) instanceof TrajectoireObserveur) {
					trajecto = (TrajectoireObserveur) controleurSwing.getListeObserveurs().get(i);
				}
			}
			for (int i=0; i<controleurSwing.getListeObserveurs().size(); i++) {
				if (controleurSwing.getListeObserveurs().get(i) ==voito) {
					controleurSwing.getListeObserveurs().remove(i);
				}else if(controleurSwing.getListeObserveurs().get(i)==voitoav) {
					controleurSwing.getListeObserveurs().remove(i);
				} else	if(controleurSwing.getListeObserveurs().get(i) ==trajecto) {
					controleurSwing.getListeObserveurs().remove(i);
				}
			}
			
			controleurSwing.add(ro);
			controleurSwing.add(trajecto);
			controleurSwing.add(voito);
			controleurSwing.add(voitoav);
			
			filenameSave="Simu Final Radar Classique";
			
		}else if (controleurSwing.getRadarDijkstraGO()) {
			//déclaration Stratégie RadarDijkstra
			Dijkstra dijk= new Dijkstra(modele.getCirc());
			Radar rdijk = new RadarDijkstra(modele.getAngles(), modele.getVoit(), modele.getCirc(), dijk);
			strat = new StrategyRadar(rdijk);
			//declaration de l'observeur et son ajout dans la liste de listenersSwing
			RadarObserveur ro = new RadarObserveur(rdijk);
			DijkstraObserveur dijko = new DijkstraObserveur(dijk);
			VoitureObserveur voito=null;
			TrajectoireObserveur trajecto =null;
			VoitureObserveurAvance voitoav=null;
			
			for (int i=0; i<controleurSwing.getListeObserveurs().size(); i++) {
				if (controleurSwing.getListeObserveurs().get(i) instanceof VoitureObserveur) {
					voito = (VoitureObserveur) controleurSwing.getListeObserveurs().get(i);
				}else if(controleurSwing.getListeObserveurs().get(i) instanceof VoitureObserveurAvance) {
					voitoav = (VoitureObserveurAvance) controleurSwing.getListeObserveurs().get(i);
				} else	if(controleurSwing.getListeObserveurs().get(i) instanceof TrajectoireObserveur) {
					trajecto = (TrajectoireObserveur) controleurSwing.getListeObserveurs().get(i);
				}
			}
			for (int i=0; i<controleurSwing.getListeObserveurs().size(); i++) {
				if (controleurSwing.getListeObserveurs().get(i) ==voito) {
					controleurSwing.getListeObserveurs().remove(i);
				}else if(controleurSwing.getListeObserveurs().get(i)==voitoav) {
					controleurSwing.getListeObserveurs().remove(i);
				} else	if(controleurSwing.getListeObserveurs().get(i) ==trajecto) {
					controleurSwing.getListeObserveurs().remove(i);
				}
			}
			
			controleurSwing.add(dijko);
			controleurSwing.add(ro);
			controleurSwing.add(trajecto);
			controleurSwing.add(voito);
			controleurSwing.add(voitoav);
			
			
			filenameSave="Simu Final Radar Dijkstra";
			
		}else if (controleurSwing.getSelectorGO()) {
			//attendre tant que tous les points ne sont pas cliqués et terminés en cliquant sur Fin Points
			do
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!controleurSwing.getFinPointsOK());
				
			//déclaration Stratégie RadarClassique = stratégie par défaut, il n'y a pas de selector pour cette stratégie
			RadarClassique r= new RadarClassique(modele.getAngles(), modele.getVoit(), modele.getCirc());
			Strategy stratRadar = new StrategyRadar(r);
			RadarObserveur ro = new RadarObserveur(r);
			VoitureObserveur voito=null;
			VoitureObserveurAvance voitoav=null;
			for (int i=0; i<controleurSwing.getListeObserveurs().size(); i++) {
				if (controleurSwing.getListeObserveurs().get(i) instanceof VoitureObserveur) {
					voito = (VoitureObserveur) controleurSwing.getListeObserveurs().get(i);
				}else if(controleurSwing.getListeObserveurs().get(i) instanceof VoitureObserveurAvance) {
					voitoav = (VoitureObserveurAvance) controleurSwing.getListeObserveurs().get(i);
				}
			}
			for (int i=0; i<controleurSwing.getListeObserveurs().size(); i++) {
				if (controleurSwing.getListeObserveurs().get(i) ==voito) {
					controleurSwing.getListeObserveurs().remove(i);
				}
			}
			for (int i=0; i<controleurSwing.getListeObserveurs().size(); i++) {		
				if(controleurSwing.getListeObserveurs().get(i)==voitoav) {
					controleurSwing.getListeObserveurs().remove(i);
				}
			}
			controleurSwing.add(ro);
			controleurSwing.add(voito);
			controleurSwing.add(voitoav);
			
			//declaration Stratégie Arrivée + selector strategie arrivee
			Selector selectArr = new SelectorArrivee(modele.getVoit(), modele.getCirc());
			Strategy stratArr = new StrategyArrivee(modele.getCirc(), modele.getVoit(), selectArr);
			
			//déclaration Stratégie Point a Point + selector stratégie point a point 
			Selector selectPAP = new SelectorPointAPoint(modele.getCirc(), modele.getVoit(), vue.getListePoints());
			Strategy stratPAP = new StrategyPointAPoint(modele.getVoit(), selectPAP);
			
			//declaration de la stratégie selector (en paramètre du constructeur la Stratégie par défaut)+ ajout des stratégies et selectors précédents
			strat = new StrategySelector(stratRadar);
			((StrategySelector) strat).add(stratArr, selectArr);
			((StrategySelector) strat).add(stratPAP, selectPAP);
			
			filenameSave="Simu Final Selector";
		}
		//mise a jour de la couleur de la trajectoire
		for (int i=0; i<controleurSwing.getListeObserveurs().size(); i++) {
			if(controleurSwing.getListeObserveurs().get(i) instanceof TrajectoireObserveur) {
				((TrajectoireObserveur) controleurSwing.getListeObserveurs().get(i)).setColor(controleurSwing.getColor());
				break;
			}
		}
		//declaration de la simulation
		SimulationMVC simu = new SimulationMVC(modele.getVoit(),modele.getCirc(),strat);
		
		//ajout des controleurs Swing et Image dans la liste de listeners de simulation
		simu.add(controleurSwing);
		
		//lancer simulation
		try {
			simu.play();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			File test = new File(filenameSave);
			ImageIO.write(controleurSwing.getImage(),"png",test);
		
		}catch(IOException e) {
			System.out.println("erreur lors de la sauvegarde");
		}
		
		//fermer la fenetre
		vue.getFenetre().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//System.exit(0);
		
	}
}
