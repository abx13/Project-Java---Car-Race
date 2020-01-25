package voiture;



import geometrie.Vecteur;

public class VoitureImpl implements Voiture {

	private static final long serialVersionUID = 1L;
	// outils pour la gestion des limites de rotation en fonction de la vitesse
	private double[] tabVitesse;    
	private double[] tabTurn;   

	// capacités	
	private final double vmax, braquage;
	private final double alpha_c, alpha_f, beta_f;

	// état à l'instant t
	private double vitesse;
	private Vecteur position;
	private Vecteur direction;
	
		
	//constructeur prenant en compte tous les arguments
	public VoitureImpl(double[] tabVitesse, double[] tabTurn, double vmax, double braquage, double alpha_c,
			double alpha_f, double beta_f, double vitesse, Vecteur position, Vecteur direction) {
		this.tabVitesse = tabVitesse;
		this.tabTurn = tabTurn;
		this.vmax = vmax;
		this.braquage = braquage;
		this.alpha_c = alpha_c;
		this.alpha_f = alpha_f;
		this.beta_f = beta_f;
		this.vitesse = vitesse;
		this.position = position;
		this.direction = direction;
	}

	//renvoie la rotation maximale en fonction de la vitesse de la voiture
	public double getMaxTurn() {
		double a=-2;
		for(int i=0; i<tabVitesse.length; i++){
			if(vitesse< tabVitesse[i]) {
				return tabTurn[i];
			}
		}
		
		return a;
	}
	
	//c'est la seule méthode qui doit modifier la position de la voiture
	public void drive(commande.Commande c) {
		
        //Est ce que la rotation et l'accélération sont entre -1 et 1, sinon, throw new RuntimeException
        if(c.getAcceleration()>1 || c.getAcceleration()<-1 || c.getRotation()>1 || c.getRotation()<-1) {
        	//System.out.println("accélération ou rotation out of bounds");
        	throw new RuntimeException();
        }
       
        
        //modifier la vitesse selon l'angle de rotation de la voiture
       if (c.getRotation()>-Math.PI/3 && c.getRotation()<Math.PI/3) {
        	c.setAcceleration(0.5);
        	if(c.getRotation()>-Math.PI/6 && c.getRotation()<Math.PI/6) {
        		c.setAcceleration(1);
        	}
        }
       
		// approche normale: gestion du volant
        direction = direction.rotation(c.getRotation() * braquage);
		// modif de direction. Note: on remarque bien que l'on tourne d'un pourcentage des capacités de la voiture
		//garanties, bornes...
        Vecteur nvlldirection = direction.clonage();	//on clone la direction pour ne pas modifier la direction de la voiture
        nvlldirection = nvlldirection.normalisationVecteur(); // renormalisation pour éviter les approximations
		
		//gestion des frottements, on soustrait à la vitesse les forces de frottements
		vitesse -= alpha_f;
		vitesse -= vitesse*beta_f;
		
		//gestion de l'acceleration/freinage
		vitesse += c.getAcceleration() * alpha_c;	//on ajoute l’acceleration multipliée par la force d’inertie à la vitesse 
		vitesse = Math.max(0., vitesse); // pas de vitesse négative (= pas de marche arriere)
		vitesse = Math.min(vmax, vitesse); // pas de dépassement des capacités
		
		// 3) mise à jour de la position
		Vecteur dirXvitesse = nvlldirection.multipleScal(vitesse);
		
		//on s'assure que ça reste dans les bornes pour changer la position de la voiture
		if (Vecteur.additionNouveau(position, dirXvitesse).x >0 && Vecteur.additionNouveau(position, dirXvitesse).x<1024 && Vecteur.additionNouveau(position, dirXvitesse).y>0 &&Vecteur.additionNouveau(position, dirXvitesse).y<768) {
			position = Vecteur.additionNouveau(position, dirXvitesse);
			//System.out.println(position.toString());
		}else {
			System.out.println("la voiture sort du cadre");
			throw new RuntimeException();
		}
	}
	
	//Accesseurs
	public double getVitesse() {
		return this.vitesse;
	}
	public Vecteur getPosition() {
		return this.position;
	}
	
	public void setPosition(Vecteur v) {
		this.position=v;
	}
	public Vecteur getDirection() {
		return this.direction;
	}
	public double getBraquage() {
		return this.braquage;
	}

	@Override
	public String getDerapage() {
		// TODO Auto-generated method stub
		return null;
	}

}
