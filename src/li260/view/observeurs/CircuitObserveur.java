package li260.view.observeurs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import circuit.Circuit;
import circuit.TerrainTools;

public class CircuitObserveur implements ObserveurSWING {
	private BufferedImage imageTerrain;
	
	public CircuitObserveur (Circuit circ) {
		super();
		imageTerrain = TerrainTools.imageFromCircuit(circ.getTerrain());
	}

	public void print(Graphics g) {
		g.drawImage(imageTerrain, 0, 0, null);
		
	}
	
	public void reinit(Circuit circ) {
		imageTerrain = TerrainTools.imageFromCircuit(circ.getTerrain());
	}
	

	public BufferedImage getImageCircObs() {
		return imageTerrain;
	}
}
