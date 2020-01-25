package mains;
import geometrie.Vecteur;

public class VecteurTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vecteur v1 = new Vecteur(1, 2);
		Vecteur v2 = new Vecteur(1, 3);
		double produitscal;
		
		//Test additionNouveau
		Vecteur v3 = Vecteur.additionNouveau(v1, v2);
		System.out.println(" v3 X "+v3.getX());
		System.out.println(" v3 Y "+v3.getY());
		
		/*//test additionAuto
		v1.additionAuto(v2);
		System.out.println(" v1 X "+v1.getX());
		System.out.println(" v1 Y "+v1.getY());*/
		
		//Test soustrationNouveau
		Vecteur v4 = Vecteur.soustractionNouveau(v1, v2);
		System.out.println(" v4 X "+v4.getX());
		System.out.println(" v4 Y "+v4.getY());
		
		/*//Test soustractionAuto
		v2.soustractionAuto(v1);
		System.out.println(" v2 X "+v2.getX());
		System.out.println(" v2 Y "+v2.getY());*/
		
		//Test produitScal
		produitscal=Vecteur.produitScal(v1, v2);
		System.out.println(" produit scalaire = "+produitscal);

		
		//Test multiplescal
		Vecteur vec = v1.multipleScal(3);
		System.out.println(" vec X "+vec.getX());
		System.out.println(" vec Y "+vec.getY());
		
		//Test rotation
		Vecteur vec2 = v1.rotation(Math.PI);
		System.out.println(" vec2 X "+vec2.getX());
		System.out.println(" vec2 Y "+vec2.getY());
		
		//Test calculNorme
		double norme = Vecteur.calculNorme(v1);
		System.out.println(" norme = "+norme);
		
		//Test clonage
		Vecteur vec3 = v1.clonage();
		System.out.println(" vec3 X "+vec3.getX());
		System.out.println(" vec3 Y "+vec3.getY());
		
		//Test égalité 
		boolean egalite = v1.equals(v2);
		System.out.println(" egalite = "+egalite);
	}
	

}
