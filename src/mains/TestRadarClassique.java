package mains;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import algo.RadarClassique;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import circuit.TerrainException;
import commande.Commande;
import geometrie.Vecteur;
import simulation.Simulation;
import strategy.Strategy;
import strategy.StrategyRadar;
import voiture.Voiture;
import voiture.VoitureFactory;

public class TestRadarClassique {
	
	public static void main(String[] args) throws IOException, TerrainException {
	
		Circuit circ = CircuitFactoryFromFile.build("resources/fichier/7_safe.trk");
		Voiture voit =  VoitureFactory.build(circ);
		BufferedImage im = circuit.TerrainTools.imageFromCircuit(circ.getTerrain());
		double[] tabAngles = new double [] {Math.PI/2.0,Math.PI/4.0,Math.PI/3.0,Math.PI/6.0,Math.PI/12.0,Math.PI/24.0,0,-Math.PI/24.0,-Math.PI/12.0,-Math.PI/6.0,-Math.PI/3.0, -Math.PI/4.0,-Math.PI/2.0};
		//fonctionnement du radar et affichage de l'index du tableau de scores qui donne la distance la plus longue
		RadarClassique r = new RadarClassique(tabAngles, voit, circ);
		
		//CHOISIR UN DES TESTS ET METTRE LES AUTRES EN COMMENTAIRE
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//PREMIER TEST : faire comme la simulation et le drive mais sans les utiliser
		//angles
		System.out.println("Radar: Angles=");
		for (int i = 0; i<r.getAngles().length; i++) {
			System.out.print(r.getAngles()[i] +"\t");
		}
		System.out.println();
		//score
		double[] scores = r.scores();
		for (int i = 0; i<scores.length; i++) {
			System.out.print(scores[i] +"\t");
		}
		System.out.println();
		//scores après conversion des distances en pixels
		int[] distances = r.distancesInPixels();
		for (int i = 0; i<distances.length; i++) {
			System.out.print(distances[i] +"\t");
		}
		System.out.println();
		//bestIndex et distances en pixels correspondante
		System.out.println("bestIndex ="+ r.getBestIndex());
		System.out.println("bestDistance du Radar ="+ r.getDistancesPixels()[r.getBestIndex()]);
		
		File test = new File("TestSimu_Main.png");
		ImageIO.write(im, "png", test);
		Vecteur posIni = voit.getPosition().clonage();
		Vecteur direction = voit.getDirection().clonage();
		direction = direction.rotation(r.getDistancesPixels()[r.getBestIndex()]*2/Math.PI);
		//tant que la difference entre la nouvelle position et la position initiale est inférieur à la distance en pixel maximale du radar, on return la commande de l'angle donné
		while ((int) Vecteur.calculNorme(Vecteur.soustractionNouveau(r.getVoit().getPosition(), posIni))< r.getDistancesPixels()[r.getBestIndex()]) {
			double difference = (int) Vecteur.calculNorme(Vecteur.soustractionNouveau(r.getVoit().getPosition(), posIni));
			System.out.println(voit.getPosition().toString()+" "+difference+" "+r.getDistancesPixels()[r.getBestIndex()] );
			@SuppressWarnings("unused")
			Commande com = new Commande(1,r.getAngles()[r.getBestIndex()]*2/Math.PI);
			Vecteur position = Vecteur.additionNouveau(voit.getPosition(), direction);
			voit.setPosition(position);
			im.setRGB((int) voit.getPosition().x, (int) voit.getPosition().y, Color.yellow.getRGB());
			ImageIO.write(im, "png", test);
		}
		System.out.println(r.getDistancesPixels()[r.getBestIndex()]);
		System.out.println(Vecteur.calculNorme(Vecteur.soustractionNouveau(r.getVoit().getPosition(), posIni)));
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//DEUXIEME TEST : affichage des scores avant et après le drive
		//score
		scores = r.scores();
		for (int i = 0; i<scores.length; i++) {
			System.out.print(scores[i] +"\t");
		}
		System.out.println();
		//scores après conversion des distances en pixels
		distances = r.distancesInPixels();
		for (int i = 0; i<distances.length; i++) {
			System.out.print(distances[i] +"\t");
		}
		System.out.println();
		//bestIndex et distances en pixels correspondante
		System.out.println("bestIndex ="+ r.calcBestIndex());
		System.out.println("bestDistance du Radar ="+ distances[r.getBestIndex()]);
		
		posIni = voit.getPosition().clonage();
		//tant que la difference entre la nouvelle position et la position initiale est inférieur à la distance en pixel maximale du radar, on return la commande de l'angle donné
		while (Vecteur.calculNorme(Vecteur.soustractionNouveau(r.getVoit().getPosition(), posIni))< r.getDistancesPixels()[r.getBestIndex()]) {
			System.out.println(voit.getPosition().toString());
			Commande com = new Commande(1,r.getAngles()[r.getBestIndex()]*2/Math.PI);
			voit.drive(com);
		}
		
		//score
		scores = r.scores();
		for (int i = 0; i<scores.length; i++) {
			System.out.print(scores[i] +"\t");
		}
		System.out.println();
		//scores après conversion des distances en pixels
		distances = r.distancesInPixels();
		for (int i = 0; i<distances.length; i++) {
			System.out.print(distances[i] +"\t");
		}
		System.out.println();
		//bestIndex et distances en pixels correspondante
		System.out.println("bestIndex ="+ r.calcBestIndex());
		System.out.println("bestDistance du Radar ="+ distances[r.getBestIndex()]);
		
		posIni = voit.getPosition().clonage();
		//tant que la difference entre la nouvelle position et la position initiale est inférieur à la distance en pixel maximale du radar, on return la commande de l'angle donné
		while (Vecteur.calculNorme(Vecteur.soustractionNouveau(r.getVoit().getPosition(), posIni))< r.getDistancesPixels()[r.getBestIndex()]) {
			System.out.println(voit.getPosition().toString());
			Commande com = new Commande(1,r.getAngles()[r.getBestIndex()]*2/Math.PI);
			voit.drive(com);
		}
		System.out.println(voit.getPosition().toString());
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//TROISIEME TEST : avec la stratégie radar
		//stratégie du radar
		Strategy stratRadar = new StrategyRadar(r);
		Simulation simuRadar = new Simulation(voit, circ, stratRadar);	
		simuRadar.play();
		System.out.println("fin simuRadar: nbCoups ="+ simuRadar.getNbCoups());
		
		
	}
}
