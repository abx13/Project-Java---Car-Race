package li260.view.observeurs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import algo.Radar;
import geometrie.Vecteur;


public class RadarObserveur implements ObserveurSWING, ObserveurImage{
private Radar r;
	
	public RadarObserveur(Radar r) {
		this.r=r;
	}
	
	public void reinit(Radar r) {
		this.r=r;
	}

	
	public void print(Graphics g) { 
		if(r==null) return;	
		//affichage des faisceaux
		Vecteur position =r.getVoit().getPosition().clonage();
		Vecteur dir = r.getVoit().getDirection().clonage();
		double[] tabAngles = r.getAngles();
		r.scores();
		for(int i=0; i<tabAngles.length; i++) {
			//rotation de l'angle donné + longueur du faisceau établie avec la distance in pixel de cet angle
			//on crée une nouvelle variable nvlldir pour ne pas modifier la valeur de dir a chaque fois
			Vecteur nvlldir=dir.rotation(tabAngles[i]);
			nvlldir=nvlldir.multipleScal(r.distancesInPixels()[i]);
			nvlldir=Vecteur.additionNouveau(position, nvlldir);
			//couleur différente du faisceau si jamais c'est celui qui est choisi
			if (i==r.getBestIndex()) {
				g.setColor(Color.red);
			}else {
				g.setColor(Color.blue);
			}
			//dessin du faisceau
			g.drawLine((int) position.getX(), (int) position.getY(), 
						(int) nvlldir.getX(), (int) nvlldir.getY());
		}
	}


	@Override
	public void print(BufferedImage im) {
		Graphics g = im.getGraphics();
		Vecteur position =r.getVoit().getPosition().clonage();
		Vecteur dir = r.getVoit().getDirection().clonage();
		double[] tabAngles = r.getAngles();
		
		for(int i=0; i<tabAngles.length; i++) {
			//rotation de l'angle donné + longueur du faisceau établie avec la distance in pixel de cet angle
			//on crée une nouvelle variable nvlldir pour ne pas modifier la valeur de dir a chaque fois
			Vecteur nvlldir=dir.rotation(tabAngles[i]);
			nvlldir=nvlldir.multipleScal(r.getDistancesPixels()[i]);
			nvlldir=Vecteur.additionNouveau(position, nvlldir);
			//couleur différente du faisceau si jamais c'est celui qui est choisi
			if (i==r.getBestIndex()) {
				g.setColor(Color.red);
			}else {
				g.setColor(Color.blue);
			}
			//dessin du faisceau
			g.drawLine((int) position.getX(), (int) position.getY(), 
						(int) nvlldir.getX(), (int) nvlldir.getY());
		}
		
	}
}