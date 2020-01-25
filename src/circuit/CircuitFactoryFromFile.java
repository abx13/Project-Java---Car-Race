package circuit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import geometrie.Vecteur;

public class CircuitFactoryFromFile {
	  public final static Vecteur dirDepart = new Vecteur(1,0);
	  public final static Vecteur dirArrivee = new Vecteur(1,0);
	  
	  public static Circuit build(String filename) {
		  InputStream file=null ;
			try {
			file = new FileInputStream(filename);
		   // ouverture 
		   InputStreamReader fr = new InputStreamReader(file); 
		   // fonction supplémentaire
		   BufferedReader in = new BufferedReader(fr);

		   //on lit les lignes
		   String buf = in.readLine();
		   int nbColonnes = Integer.parseInt(buf);
		   buf= in.readLine();
		   int nbLignes=Integer.parseInt(buf);
		   
		   //on crée la matrice pour nos caractères
		   char[][] matrice= new char[nbLignes][nbColonnes];
		   
		   //on remplit la matrice caractère par caractère
		   int i,j;
		   for (i=0; i<nbLignes; i++) {
			  buf=in.readLine(); 
			  	for (j=0; j<nbColonnes; j++) {
				   
				   char a= buf.charAt(j);	//""+buf.substring(0,0);
				   matrice[i][j]=a;
				   //String b= buf.substring(j+1, nbColonnes);
				   //buf=b;
			   }
		   }
		   
		   in.close(); // penser à la fermeture
		   
		   //rajout pour factory:
		   Terrain[][] t = new Terrain[nbLignes][nbColonnes];
		   
		   for (i=0; i<nbLignes; i++) {
			   for (j=0; j<nbColonnes; j++) {
				   t[i][j]= TerrainTools.terrainFromChar(matrice[i][j]);
			   }
		   }
		   
		   //trouver le point de départ
		   Vecteur pointDepart = null ;
		    for (i=0; i<nbLignes; i++) {
				   for (j=0; j<nbColonnes; j++) {
					   if (t[i][j]==Terrain.StartPoint) {
						   pointDepart = new Vecteur(j, i);
						   System.out.println("point de départ : "+pointDepart.getX()+","+pointDepart.getY());
					   }
					   
				   }
			}
		   System.out.println("track"+t.length+"/"+t[0].length);
		   Circuit circ = new CircuitImpl(t, pointDepart, dirDepart, dirArrivee);
		   
		   //avant rajout pour factory: return;
		   return circ;
		 // dans l'idéal, on sépare la gestion des exceptions
		 } catch (Exception e) {
		   e.printStackTrace();
		   System.err.println("Invalid Format : " + file + "... Loading aborted");
		   return null;
		 }

	  }
	 

}
