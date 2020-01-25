package mains;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import circuit.TerrainException;
import commande.Commande;
import geometrie.Vecteur;
import voiture.Voiture;
import voiture.VoitureFactory;

public class TestListeCommandes {
	
	public static void main(String[] args) throws IOException, TerrainException  {
	
		Circuit circ = CircuitFactoryFromFile.build("resources/fichier/2_safe.trk");
		Voiture voit =  VoitureFactory.build(circ);		
		BufferedImage im = circuit.TerrainTools.imageFromCircuit(circ.getTerrain());
		
		//une liste de 100 accélérations sans rotations 
		ArrayList <Commande> com1 = new ArrayList <Commande>();
		for (int i=0; i<100; i++ ) com1.add(new Commande(1,0));
		//une liste de 100 accélérations sans rotations alternée avec une liste de 100 accélération/rotation à droite (à fond) 
		ArrayList <Commande> com2 = new ArrayList <Commande>();
		for (int i=0; i<100; i++ ) {
			com2.add(new Commande(1,0));
			com2.add(new Commande(1,-1));
		}
		//une liste de 100 accélérations sans rotations alternée avec une liste de 100 accélération/rotation à gauche (à fond) 
		ArrayList <Commande> com3 = new ArrayList <Commande>();
		for (int i=0; i<100; i++ ) {
			com3.add(new Commande(1,0));
			com3.add(new Commande(1,1));
		}
		
		File test = new File("TestCommandes.png");
		//ça fait le même chose que la fonction simu.play
		for (int i=1; i<100; i++) {
			voit.drive(com1.get(i));
			Vecteur position = voit.getPosition();
			System.out.println("position : "+ voit.getPosition());
			im.setRGB((int) position.x, (int) position.y, Color.yellow.getRGB());
			ImageIO.write(im, "png", test);
		}
		for (int i=1; i<200; i++) {
			voit.drive(com2.get(i));
			Vecteur position = voit.getPosition();
			System.out.println("position : "+ voit.getPosition());
			im.setRGB((int) position.x, (int) position.y, Color.yellow.getRGB());
			ImageIO.write(im, "png", test);
		}
		//la liste de commande 3 ne fonctionne pas car on tourne a fond et la vitesse de la voiture ne lui permet pas de tourner à fond 
		for (int i=1; i<200; i++) {
			voit.drive(com3.get(i));
			Vecteur position = voit.getPosition();
			System.out.println("position : "+ voit.getPosition());
			im.setRGB((int) position.x, (int) position.y, Color.yellow.getRGB());
			ImageIO.write(im, "png", test);
		}
		
		
		//im.setRGB(0,0, Color.orange.getRGB()); le point (0,0) est colorié en orange
		
		/*
		Strategy strat= new StrategyListCommande(com1);
		Simulation simu = new Simulation(voit, circ, strat);
		simu.play();
		System.out.println("fin simu1");
		Strategy strat2 = new StrategyListCommande(com2);
		Simulation simu2 = new Simulation(voit, circ, strat2);
		simu2.play();
		System.out.println("fin simu2");
		Strategy strat3 = new StrategyListCommande(com3);
		Simulation simu3 = new Simulation(voit, circ, strat3);
		simu3.play();
		System.out.println("fin simu3");
		im = circuit.TerrainTools.imageFromCircuit(circ.getTerrain());
		*/
	}

}
