package commande;

public class Commande {
	private double acc;
	private double turn;
	
	//Constructeur
	public Commande(double acc, double turn) {
		this.acc=acc;
		this.turn=turn;
	}
	
	//Accesseurs
	public double getRotation() {
		return turn;
	}

	public void setRotation(double d) {
		turn = d;
		
	}

	public double getAcceleration() {
		return acc;
	}

	public void setAcceleration(double i) {
		acc=i;
		
	}
}
