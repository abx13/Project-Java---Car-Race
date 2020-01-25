package li260.view.observeurs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import java.io.FileInputStream;

import java.io.IOException;


import javax.imageio.ImageIO;

import geometrie.Vecteur;
import voiture.Voiture;


public class VoitureObserveurAvance implements ObserveurSWING, ObserveurImage {
	
	Voiture voit;
	FileInputStream name; 
	private BufferedImage imCar;
	
	public VoitureObserveurAvance(Voiture voit, String filename) throws IOException {
		this.voit=voit;
		this.name = new FileInputStream(filename);
		//redimensioner image
		  this.imCar=ImageIO.read(name); // lecture fichier original
		  int dim1=78, dim2=100; // nouvelles dimensions
		  int delta=25; // bords à laisser vierges
		  BufferedImage resizedImage=new BufferedImage(dim1, dim2, BufferedImage.TYPE_4BYTE_ABGR);
		  Graphics2D g=resizedImage.createGraphics();
		  g.drawImage(imCar, delta, delta, dim1-2*delta, dim2-2*delta, null);
		  imCar=resizedImage; // image de bonnes dimensions + bords transparents
	}
	
	public void reinit(Voiture voit) {
		this.voit=voit;
	}


	public void print(Graphics g) {
		// calcul de l'angle à appliquer sur l'image de la voiture pour la rendre
		  // cohérente avec la simulation à chaque instant
		  // -> dépend de l'image et de l'affichage de l'interface (horizontale ou vertical)
		  double angle = Vecteur.calculAngle(voit.getDirection(), new Vecteur(1, 0)); 

		  // construction d'une transformation 
		  AffineTransform transform = new AffineTransform();
		  // rotation par rapport à un centre à définir (cf javadoc)
		  
		  transform.rotate(angle, imCar.getHeight()/2, imCar.getWidth()/2);
		  // transformation => transformation d'image
		  AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);

		  // image finale
		  BufferedImage carMod = op.filter(imCar, null);
		  //encore une fois, ne pas changer X et Y car déjà fait avec la position + on a changé la position en Y car elle n'était pas bonne avec notre voiture
		  //le -40 vient de -25 (delta de la resizedImage) -25/2 (car on veut que le point soit au milieu de la voiture et non pas le coin haut gauche
		  g.drawImage(carMod, (int) voit.getPosition().getX()-40, (int) voit.getPosition().getY()-40, null);
		  
		  g.setColor(Color.black);
			g.drawString(String.format("v: %.2f d: (%6.2f, %6.2f) ", voit.getVitesse(), 
					voit.getDirection().getX(), voit.getDirection().getY()) ,
					10, 10);
		
	}


	@Override
	public void print(BufferedImage im) throws IOException {
		Graphics g = im.getGraphics();
		// calcul de l'angle à appliquer sur l'image de la voiture pour la rendre
		  // cohérente avec la simulation à chaque instant
		  // -> dépend de l'image et de l'affichage de l'interface (horizontale ou vertical)
		//on a enlevé la soustraction de PI/2 car notre image est déjà à l'horizontale 
		  double angle =  Vecteur.calculAngle(voit.getDirection(), new Vecteur(1, 0)); 
		  
		  // construction d'une transformation 
		  AffineTransform transform = new AffineTransform();
		  // rotation par rapport à un centre à définir (cf javadoc)
		  transform.rotate(angle, imCar.getHeight()/2, imCar.getWidth()/2);
		  // transformation => transformation d'image
		  AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);

		  // image finale: on a modifié la position en Y car elle n'était pas bonne avec notre voiture
		  BufferedImage carMod = op.filter(imCar, null);
		  g.drawImage(carMod, (int) voit.getPosition().getX()-10, (int) voit.getPosition().getY()-50, null);
		
	}
}
