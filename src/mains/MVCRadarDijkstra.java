package mains;

import java.awt.Color;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import algo.Radar;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import circuit.TerrainException;
import dijkstra.Dijkstra;
import dijkstra.RadarDijkstra;
import li260.view.observeurs.CircuitObserveur;
import li260.view.observeurs.DijkstraObserveur;
import li260.view.observeurs.RadarObserveur;
import li260.view.observeurs.TrajectoireObserveur;
import li260.view.observeurs.VoitureObserveur;
import li260.view.observeurs.VoitureObserveurAvance;
import mvc.Controleur;
import mvc.IHMSwing;
import mvc.Modele;
import mvc.Vue;
import simulation.SimulationMVC;
import strategy.Strategy;
import strategy.StrategyRadar;
import voiture.Voiture;
import voiture.VoitureFactory;

@SuppressWarnings("unused")
public class MVCRadarDijkstra {
	public static void main(String[] args) throws IOException, TerrainException {
		
		//declaration fichier, angles,circuit, voiture, image
		String filename="resources/fichier/1_safe.trk";
		double[] angles = new double [] {Math.PI/2.0,Math.PI/4.0,Math.PI/3.0,Math.PI/6.0,Math.PI/12.0,Math.PI/24.0,0,-Math.PI/24.0,-Math.PI/12.0,-Math.PI/6.0,-Math.PI/3.0, -Math.PI/4.0,-Math.PI/2.0};
		Circuit circ = CircuitFactoryFromFile.build(filename);
		Voiture voit = VoitureFactory.build(circ);
		BufferedImage im = circuit.TerrainTools.imageFromCircuit(circ.getTerrain());
		
		//declaration modele mvc
		Modele modele = new Modele(voit, circ, angles);
		IHMSwing controleurSwing = new IHMSwing();
		Vue vue = new Vue(controleurSwing);
		controleurSwing.initVueModele(vue, modele);
		controleurSwing.initVueModele(vue, modele);
		vue.initIHM();
		
		//Controleur controleurImage = new Controleur(circ, vue);

		//déclaration Stratégie RadarDijkstra
		Dijkstra dijk= new Dijkstra(circ);
		Radar rdijk = new RadarDijkstra(angles, voit, circ, dijk);
		Strategy strat = new StrategyRadar(rdijk);
		
		//declaration Simulation
		SimulationMVC simu = new SimulationMVC(voit,circ,strat);
		
		
		//declaration des observeurs
		CircuitObserveur circo = new CircuitObserveur(circ);
		VoitureObserveur voito = new VoitureObserveur(voit);
		VoitureObserveurAvance voitoav  = new VoitureObserveurAvance(voit,"resources/voiture/Car_Top_Red-1.png");
		RadarObserveur ro = new RadarObserveur(rdijk);
		TrajectoireObserveur trajecto = new TrajectoireObserveur(voit, Color.YELLOW);
		DijkstraObserveur dijko = new DijkstraObserveur(dijk);
		
		//ajout des observeurs dans le controleurSwing
		controleurSwing.add(circo);
		controleurSwing.add(dijko);
		controleurSwing.add(voito);
		controleurSwing.add(voitoav);
		//controleurSwing.add(ro);
		controleurSwing.add(trajecto);
		controleurSwing.add(dijko);
		
		/*
		//ajout des observeurs dans le controleurImage
		controleurImage.add(circo);
		controleurImage.add(voito);
		controleurImage.add(voitoav);
		controleurImage.add(ro);
		controleurImage.add(trajecto);
		controleurImage.add(dijko);
		*/
		//ajout des controleurs Swing et Image dans la liste de listeners de simulation
		simu.add(controleurSwing);
		//simu.add(controleurImage);
		
		
		//ajout des boutons dans les listeners du controleur Swing
		JButton[] boutons = vue.getBouttons();
		for (int i=0; i<boutons.length; i++) {
			boutons[i].addActionListener(controleurSwing);
		}
		
		//initialisation du conteneur principal, qui initialise le graphics g appelé dans le paint et les print selon Nour
		Container conteneurPrincipal = vue.getFenetre().getContentPane();
		vue.getFenetre().setVisible(true);
		
		//lancer simulation
		try {
			simu.play();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
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