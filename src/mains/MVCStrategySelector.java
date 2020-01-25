package mains;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Container;
import algo.RadarClassique;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import circuit.TerrainException;

import li260.view.observeurs.CircuitObserveur;
import li260.view.observeurs.RadarObserveur;
import li260.view.observeurs.TrajectoireObserveur;
import li260.view.observeurs.VoitureObserveur;
import li260.view.observeurs.VoitureObserveurAvance;
import mvc.Controleur;
import mvc.IHMSwing;
import mvc.Modele;
import mvc.Vue;
import selector.Selector;
import selector.SelectorArrivee;
import selector.SelectorPointAPoint;
import simulation.SimulationMVC;
import strategy.Strategy;
import strategy.StrategyArrivee;
import strategy.StrategyPointAPoint;
import strategy.StrategyRadar;
import strategy.StrategySelector;
import voiture.Voiture;
import voiture.VoitureFactory;

@SuppressWarnings("unused")
public class MVCStrategySelector {
	public static void main (String[] args) throws IOException, TerrainException, InterruptedException {
				
		//declaration fichier, angles,circuit, voiture, image
		String filename="resources/fichier/1_safe.trk";
		double[] angles = new double [] {Math.PI/2.0,Math.PI/4.0,Math.PI/3.0,Math.PI/6.0,Math.PI/12.0,Math.PI/24.0,0,-Math.PI/24.0,-Math.PI/12.0,-Math.PI/6.0,-Math.PI/3.0, -Math.PI/4.0,-Math.PI/2.0};
		Circuit circ = CircuitFactoryFromFile.build(filename);
		Voiture voit = VoitureFactory.build(circ);
		
		//declaration modele mvc
		Modele modele = new Modele(voit, circ, angles);
		IHMSwing controleurSwing = new IHMSwing();
		Vue vue = new Vue(controleurSwing);
		controleurSwing.initVueModele(vue, modele);
		controleurSwing.setPreferredSize(new Dimension(1024,768));
		controleurSwing.initVueModele(vue, modele);
		vue.initIHM();
		
		//declaration du radar
		RadarClassique r= new RadarClassique(angles, voit, circ);

		
		//ajout du label pour le mouseListener: le controleurSwing reçoit les event envoyés par le JPanel image de vue. 
		vue.getImageIHM().addMouseListener(controleurSwing);
		


		//declaration des observeurs
		CircuitObserveur circo = new CircuitObserveur(circ);
		VoitureObserveur voito = new VoitureObserveur(voit);
		VoitureObserveurAvance voitoav  = new VoitureObserveurAvance(voit,"resources/voiture/Car_Top_Red-1.png");
		RadarObserveur ro = new RadarObserveur(r);
		
		
		//ajout des observeurs dans le controleurSwing
		controleurSwing.add(circo);
		controleurSwing.add(voito);
		controleurSwing.add(voitoav);
		controleurSwing.add(ro);
		
		/*
		//ajout des observeurs dans le controleurImage
		controleurImage.add(circo);
		controleurImage.add(voito);
		controleurImage.add(voitoav);
		controleurImage.add(ro);
		*/
		
			
		//ajout des boutons dans les listeners du controleur Swing
		JButton[] boutons = vue.getBouttons();
		for (int i=0; i<boutons.length; i++) {
			boutons[i].addActionListener(controleurSwing);
		}
		
		//initialisation du conteneur principal, qui initialise le graphics g appelé dans le paint et les print selon Nour
		Container conteneurPrincipal = vue.getFenetre().getContentPane();
				
		//il faut que la fenetre apparaisse avant de lancer la simulation pour pouvoir créer la liste de points 
		//activation fenetre 
		vue.getFenetre().pack();
		vue.getFenetre().setTitle("Simulation");
		vue.getFenetre().setVisible(true);
		

		//attendre tant que tous les points ne sont pas cliqués et terminés en cliquant sur Fin Points
		do
		{
			Thread.sleep(10);
		} while (!controleurSwing.getFinPointsOK());
		
		
		//déclaration Stratégie RadarClassique = stratégie par défaut, il n'y a pas de selector pour cette stratégie
		
		Strategy stratRadar = new StrategyRadar(r);
		
		//declaration Stratégie Arrivée + selector strategie arrivee
		Selector selectArr = new SelectorArrivee(voit, circ);
		Strategy stratArr = new StrategyArrivee(circ, voit, selectArr);
		
		//déclaration Stratégie Point a Point + selector stratégie point a point 
		Selector selectPAP = new SelectorPointAPoint(circ, voit, vue.getListePoints());
		Strategy stratPAP = new StrategyPointAPoint(voit, selectPAP);
		
		//declaration de la stratégie selector (en paramètre du constructeur la Stratégie par défaut)+ ajout des stratégies et selectors précédents
		StrategySelector stratSelect = new StrategySelector(stratRadar);
		stratSelect.add(stratArr, selectArr);
		stratSelect.add(stratPAP, selectPAP);
		
		
		//declaration Simulation
		SimulationMVC simu = new SimulationMVC(voit,circ,stratSelect);
		
		//ajout des controleurs Swing et Image dans la liste de listeners de simulation
		simu.add(controleurSwing);
		//simu.add(controleurImage);
		//observeur de la trajectoire qui prend en argument la simulation
		TrajectoireObserveur trajecto = new TrajectoireObserveur(voit, Color.YELLOW);
		controleurSwing.add(trajecto);
		//controleurImage.add(trajecto);
		
		
		//lancer simulation
		try {
			simu.play();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//ajout de l'image du circuit dans la fenetre
		conteneurPrincipal.setPreferredSize(new Dimension(1024,800));
		conteneurPrincipal.add(controleurSwing);
		//vue.getFenetre().add(conteneurPrincipal, BorderLayout.SOUTH);
				
		
		
		try {
			File test1 = new File("simu mvc Image");
			File test2 = new File("simu mvc Swing");
			//ImageIO.write(controleurImage.getImage(),"png",test1);
			ImageIO.write(controleurSwing.getImage(),"png",test2);
		
		}catch(IOException e) {
			System.out.println("erreur lors de la sauvegarde");
		}
		
		//fermer la fenetre
		vue.getFenetre().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//System.exit(0);
		
	}	
}

