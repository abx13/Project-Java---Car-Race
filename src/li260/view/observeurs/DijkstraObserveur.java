package li260.view.observeurs;

import java.awt.Color;

//Est ce qu'on veut sauvegarder une image avec ça ou pas? 

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dijkstra.Dijkstra;


public class DijkstraObserveur implements ObserveurSWING, ObserveurImage{
	private Dijkstra dijk;
	
	public DijkstraObserveur (Dijkstra dijk) {
		super();
		this.dijk = dijk;
		
	}
	
	public void reinit(Dijkstra dijk) {
		this.dijk = dijk;
	}

	
	public void print(Graphics g) {
		for(int i = 0; i<dijk.getDistance().length; i++) {
			for(int j = 0; j<dijk.getDistance()[0].length; j++) {
				if (dijk.getDistance()[i][j]<(int)Double.POSITIVE_INFINITY) {
					g.setColor(new Color((int) dijk.getDistance()[i][j]%255,0,0));;
					g.drawLine(j, i, j, i);
					
				}
			}
		}
		
	}


	@Override
	public void print(BufferedImage im) {
		Graphics g=im.getGraphics();
		for(int i = 0; i<dijk.getDistance().length; i++) {
			for(int j = 0; j<dijk.getDistance()[0].length; j++) {
				if (dijk.getDistance()[i][j]<(int)Double.POSITIVE_INFINITY) {
					g.setColor(new Color((int) dijk.getDistance()[i][j]%255,0,0));;
					g.drawLine(j, i, j, i);
					
				}
			}
		}
		
	}
	
	
	
}
