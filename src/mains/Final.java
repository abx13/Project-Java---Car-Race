package mains;

import java.awt.Container;

import java.io.IOException;

import circuit.Circuit;
import circuit.CircuitFactoryFromFile;

import li260.view.observeurs.CircuitObserveur;

import li260.view.observeurs.TrajectoireObserveur;
import li260.view.observeurs.VoitureObserveur;
import li260.view.observeurs.VoitureObserveurAvance;
import mvc.IHMSwing;
import mvc.Lancer;
import mvc.Modele;
import mvc.Vue;

import voiture.Voiture;
import voiture.VoitureFactory;

public class Final {
	public static void main (String[] args) throws IOException {
		
		//affichage de la vue
		IHMSwing controleurSwing = new IHMSwing();
		Vue vue = new Vue(controleurSwing);
		vue.getFenetre().setVisible(true);
		
		do
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (controleurSwing.getFilename()==null || controleurSwing.getColor()==null );
		
		//declaration fichier, angles,circuit, voiture
		String filename=controleurSwing.getFilename();
		double[] angles = new double [] {Math.PI/2.0,Math.PI/4.0,Math.PI/3.0,Math.PI/6.0,Math.PI/12.0,Math.PI/24.0,0,-Math.PI/24.0,-Math.PI/12.0,-Math.PI/6.0,-Math.PI/3.0, -Math.PI/4.0,-Math.PI/2.0};
		Circuit circ = CircuitFactoryFromFile.build(filename);
		Voiture voit = VoitureFactory.build(circ);
		
		//ajout du label pour le mouseListener: le controleurSwing reçoit les event envoyés par le JPanel image de vue. 
		vue.getImageIHM().addMouseListener(controleurSwing);
		
		//declaration des observeurs
		CircuitObserveur circo = new CircuitObserveur(circ);
		VoitureObserveur voito = new VoitureObserveur(voit);
		VoitureObserveurAvance voitoav  = new VoitureObserveurAvance(voit,"resources/voiture/Car_Top_Red-1.png");
		TrajectoireObserveur trajecto = new TrajectoireObserveur(voit, controleurSwing.getColor());
		
		
		//ajout des observeurs dans le controleurSwing
		controleurSwing.add(circo);
		controleurSwing.add(trajecto);
		controleurSwing.add(voito);
		controleurSwing.add(voitoav);
		
		//declaration de la suite du modele mvc
		Modele modele = new Modele(voit, circ, angles);
		controleurSwing.initVueModele(vue, modele);
		vue.initIHM();
		
		
		//initialisation du conteneur principal, qui initialise le graphics g appelé dans le paint et les print selon Nour
		@SuppressWarnings("unused")
		Container conteneurPrincipal = vue.getFenetre().getContentPane();
		//lancer
		Lancer exec=new Lancer();
		exec.lancer(controleurSwing, vue, modele);
		
		//RESET
		while (vue.getFenetre().isShowing()) {
			controleurSwing.reinit();
			do
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (!controleurSwing.getReset());
			
			if (controleurSwing.getReset()){
				
				
				do
				{
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (controleurSwing.getFilename()==null || controleurSwing.getColor()==null );
				
				//renitialisation du circuit et de la voiture
				filename = controleurSwing.getFilename();
				circ = CircuitFactoryFromFile.build(filename);
				voit = VoitureFactory.build(circ);
				
				circo.reinit(circ);
				voito.reinit(voit);
				voitoav.reinit(voit);
				trajecto.reinit(voit, controleurSwing.getColor());
				
				controleurSwing.repaint();
				
				modele.reinit(voit, circ);
				
				exec.lancer(controleurSwing, vue, modele);
			}
		}
		
	}
}
