package li260.view.observeurs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import geometrie.Vecteur;
import voiture.Voiture;

public class TrajectoireObserveur implements ObserveurSWING, ObserveurImage{
	
	private Voiture voit;
	private ArrayList<Vecteur> points;
	private Color color;
	

	
	public TrajectoireObserveur(Voiture voit, Color color) {
		super();
		this.voit=voit;
		this.color=color;
		this.points= new ArrayList<Vecteur>();
	}
	
	public void reinit(Voiture voit, Color color) {
		this.voit=voit;
		this.color=color;
		this.points= new ArrayList<Vecteur>();
	}
	
	public void setColor(Color color) {
		this.color=color;
	}

	public void print(Graphics g) {
		//BufferedImage im = new BufferedImage(simuMvc.getCirc().getWidth(),simuMvc.getCirc().getHeight(),BufferedImage.TYPE_INT_ARGB);
		//on dessine le pixel jaune pour la position de la voiture
		g.setColor(color);
		points.add(voit.getPosition().clonage());
		for (Vecteur pos : points) {
			g.drawLine((int)pos.getX(), (int) pos.getY(), (int)pos.getX(), (int) pos.getY());
			
		}
		
	}

	
	@Override
	public void print(BufferedImage im) throws IOException {
		File test = new File("TestTrajectoireObs.png");
		//points.add(simuMvc.getVoit().getPosition().clonage());
		for (Vecteur pos : points) {
			im.setRGB((int)pos.getX(), (int) pos.getY(), Color.yellow.getRGB());
		}
		//on dessine le pixel jaune pour la position de la voiture
		//im.setRGB((int)simuMvc.getVoit().getPosition().getX(), (int) simuMvc.getVoit().getPosition().getY(), Color.yellow.getRGB());
		ImageIO.write(im, "png", test);
		
	}
}
