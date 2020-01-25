package li260.view.observeurs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import voiture.Voiture;


public class VoitureObserveur implements ObserveurImage, ObserveurSWING {
	private Voiture voiture;
	//private Color color = Color.yellow; 

	public VoitureObserveur(Voiture voit) {
		super();
		this.voiture = voit;
	}
	
	public void reinit(Voiture voit) {
		this.voiture = voit;
	}
	

	public int getX(){ 
		return (int) voiture.getPosition().getX();
	}
	public int getY(){
		return (int) voiture.getPosition().getY();
	}

	public Color getColor() {
		//couleur différentes selon la vitess de la voiture
            if(voiture.getVitesse()<0.3) // vitesse faible -> cyan 
               return new Color(0, (int)(voiture.getVitesse()*255*2), (int) (voiture.getVitesse()*255*2));

            if(voiture.getVitesse() == 0.9)
              return new Color((int)(voiture.getVitesse()*255),  (int) (voiture.getVitesse()*255), 0);

            return new Color((int)(voiture.getVitesse()*255), 0, (int) (voiture.getVitesse()*255));
        }

	public void print(BufferedImage im) {
		//on ne devait pas changer les X et les Y ici car on le fait déjà avec la position de la voiture
		im.setRGB(this.getX(), this.getY(), this.getColor().getRGB());	
	}
	
	public void print(Graphics g) {
		//on trace un rectangle rouge pour la voiture
		g.setColor(Color.red);		
		g.drawRect(getX()-2, getY()-2, 4, 4);
		
		

		
		//on trace la direction de la voiture
		/*g.setColor(Color.yellow);		
		g.drawLine((int) voiture.getPosition().getY(), 
					(int) voiture.getPosition().getX(), 
					(int) (voiture.getPosition().getX()+voiture.getDirection().getY()*10), 
					(int) (voiture.getPosition().getY()+voiture.getDirection().getX()*10));*/

		
		


	}
}