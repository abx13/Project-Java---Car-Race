package li260.view.observeurs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import geometrie.Vecteur;

public class PointsObserveur implements ObserveurSWING, ObserveurImage{
	
	private Vecteur point;
	
	public PointsObserveur (Vecteur point) {
		super();
		this.point=point ;
		
	}

	
	public void print(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawOval((int)point.getX()-15, (int) point.getY()-15, 30, 30);
		
	}


	
	public void print(BufferedImage im) {
		try {
            File outputfile = new File("savedPointAPoint.png");
            Graphics g = im.getGraphics();
    			g.drawOval((int)point.getX()-1, (int) point.getY()-1, 2, 2);
    		
            ImageIO.write(im, "png", outputfile);
         } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde");
         }
		
	}
	
	
	
}
