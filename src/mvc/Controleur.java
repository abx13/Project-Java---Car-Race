package mvc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import circuit.Circuit;
import li260.view.observeurs.ObserveurImage;

public class Controleur implements UpdateEventListener {
	private ArrayList<ObserveurImage> listeImage; 
	private BufferedImage im;
	
	public Controleur(Circuit circ, Vue vue) {
		this.im=circuit.TerrainTools.imageFromCircuit(circ.getTerrain());
		this.listeImage= new ArrayList<ObserveurImage>();
	}
	
	public void manageUpdate() throws IOException {
		for(ObserveurImage o: listeImage) {
			o.print(im);
		}
	}
	
	public void add(ObserveurImage l) {
		listeImage.add(l);
	}
	
	public BufferedImage getImage() {
		return im;
	}
	
}