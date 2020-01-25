package voiture;

import java.io.Serializable;

import geometrie.Vecteur;

public interface Voiture extends Serializable{
	// pour le pilotage
	public void drive(commande.Commande c);		//execute la commande
	public double getMaxTurn(); // retourne la rotation maximale en fonction de la vitesse
	// pour l'observation : accesseurs
	public double getVitesse();
	public Vecteur getPosition();
	public Vecteur getDirection();
	public double getBraquage();
	public String getDerapage();
	public void setPosition(Vecteur position);
		
}
