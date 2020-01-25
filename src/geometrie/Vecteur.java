package geometrie;

public class Vecteur {
	public final double x, y;

	//Constructeurs
	public Vecteur(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Vecteur(Vecteur a, Vecteur b){
		// les coordonnées correspondent à B - A
		this.x= b.x-a.x;
		this.y= b.y-a.y;
		}
	
	//Accesseurs
	public double getX() {
		return x;
	}

	/*public void setX(double x) {
		this.x = x;
	}*/

	public double getY() {
		return y;
	}

	/*public void setY(double y) {
		this.y = y;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	//égalité de 2 vecteurs?
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vecteur [x=" + x + ", y=" + y + "]";
	}
	
	//méthode de calcul à partir des vecteurs
	public static Vecteur additionNouveau(Vecteur v1, Vecteur v2) {
		
		return new Vecteur(v1.x+v2.x, v1.y+v2.y);
	}
	
	/*public void additionAuto(Vecteur v2) {
		this.x=this.x+v2.x;
		this.y=this.y+v2.y;
	}*/
	
	public static Vecteur soustractionNouveau(Vecteur v1, Vecteur v2) {
		
		return new Vecteur(v1.x-v2.x, v1.y-v2.y);
	}
	
	/*public void soustractionAuto(Vecteur v2) {
		this.x=this.x-v2.x;
		this.y=this.y-v2.y;
	}*/
	
	public static double produitScal(Vecteur v1, Vecteur v2) {
		double pd;
		pd=v1.x*v2.x+v1.y*v2.y;
		return pd;
	}
	
	public static double calculAngle(Vecteur a, Vecteur b) {
		double pds = produitScal(a,b);
		double pdNorme= calculNorme(a)*calculNorme(b);
		double q = pds/pdNorme;
		if (q<1 && q>-1) {
			return Math.acos(q);
		}
		else {
			return 0;
		}
		
	}
	
	public Vecteur multipleScal( double a) {
		Vecteur vec = new Vecteur(this.x*a, this.y*a);
		return vec;
	}
	
	public  Vecteur rotation(double radians) {
		double sin = Math.sin(radians);
		double cos = Math.cos(radians);
		
		Vecteur nouv = new Vecteur (this.x*cos-this.y*sin, this.x*sin+this.y*cos);
		
		return nouv;
	}
	
	public static double calculNorme(Vecteur v1) {
		return Math.sqrt(v1.x*v1.x+v1.y*v1.y);
	}
	public double calculNorme() {
		return Math.sqrt(this.x*this.x+this.y*this.y);
	}
	
	public Vecteur normalisationVecteur () {
		Vecteur nouv = new Vecteur (this.x/this.calculNorme(), this.y/this.calculNorme());
		return nouv;
	}
	
	public  Vecteur clonage() {
		return new Vecteur(x, y);
	}
	
}
