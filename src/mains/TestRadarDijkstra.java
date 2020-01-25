package mains;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import circuit.TerrainException;
import circuit.TerrainTools;
import dijkstra.Dijkstra;
import dijkstra.RadarDijkstra;
import simulation.Simulation;
import strategy.Strategy;
import strategy.StrategyRadar;
import voiture.Voiture;
import voiture.VoitureFactory;

public class TestRadarDijkstra {
	public static void main(String[] args) throws IOException, TerrainException {
		
		Circuit circ = CircuitFactoryFromFile.build("resources/fichier/1_safe.trk");
		Voiture voit =  VoitureFactory.build(circ);
		double[] tabAngles = new double [] {Math.PI/2.0,Math.PI/4.0,Math.PI/3.0,Math.PI/6.0,Math.PI/12.0,Math.PI/24.0,0,-Math.PI/24.0,-Math.PI/12.0,-Math.PI/6.0,-Math.PI/3.0, -Math.PI/4.0,-Math.PI/2.0};
		BufferedImage im = circuit.TerrainTools.imageFromCircuit(circ.getTerrain());
		Dijkstra dijk = new Dijkstra(circ);
		
		for(int i=0; i<circ.getHeight(); i++) {
			for(int j=0; j<circ.getWidth();j++) {
				if (TerrainTools.isRunnable(circ.getTerrain(i, j))) {
					//System.out.println(dijk.getDistance()[i][j]);					
					im.setRGB(j, i, new Color((int) (dijk.getDistance()[i][j]%255),0,0).getRGB() );
				}
			}
		}
		try {
            File outputfile = new File("saved_TestDijkstra.png");
            ImageIO.write(im, "png", outputfile);
         } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde");
         }
	
			
		//création radar djikstra
		RadarDijkstra rdijk = new RadarDijkstra (tabAngles, voit, circ, dijk);
		Strategy stratDijk = new StrategyRadar(rdijk);
		Simulation simuDijk = new Simulation (voit, circ,stratDijk);
		simuDijk.play();
		System.out.println("fin simuDijk: nbCoups ="+simuDijk.getNbCoups());
		
		try {
            File outputfile = new File("saved_TestDijkstraSimu.png");
            ImageIO.write(im, "png", outputfile);
         } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde");
         }
	}
}
