package simulation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainException;
import circuit.TerrainTools;
import commande.Commande;
import geometrie.Vecteur;
import strategy.Strategy;
import voiture.Voiture;

public class Simulation  {
	private Voiture voit;
	private Circuit circ;
	private Strategy strat;
	private ArrayList<Commande> record;
	private static int nbCoups =0;
	
	public Simulation(Voiture voit,Circuit circ, Strategy strat ) {
		this.voit=voit;
		this.circ=circ;
		this.strat=strat;
		this.record=new ArrayList<Commande>();
		
	}
	
	
	//sauvegarde la course de la voiture dans une image, pas d'affichage en direct pour cela voir SimulationMVC
	public void play() throws IOException, TerrainException{
		//création d’une image à partir du circuit	
		BufferedImage im = circuit.TerrainTools.imageFromCircuit(this.circ.getTerrain());
		File test = new File("TestSimu.png");
		
		boolean runnable = true;
		
		//tant que la voiture reste dans le circuit runnable
		while(voit.getPosition().getY() >=0 && voit.getPosition().getX() >=0 && voit.getPosition().getY() < circ.getHeight() && voit.getPosition().getX() < circ.getWidth() && runnable)
		{
			Commande com = this.strat.getCommande();
			//cette condition s'applique uniquement pour la strategie liste de commande
			if(com!=null) {
				record.add(com);
				nbCoups++;	
				try {
					this.voit.drive(com);
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//on fait des clonages pour être sur que la position et la direction de la voiture ne sont pas modifiées
					Vecteur position = voit.getPosition().clonage();
					Vecteur direction = voit.getDirection().clonage();
					//on rajoute une condition disant que la voiture doit être proche de la ligne d'arrivee pour qu'elle n'aille pas en arrière parce que sinon la voiture ne peut pas faire le circuit en entier et est bloquée
					if(Vecteur.produitScal(direction,circ.getDirectionArrivee())<=0 && Vecteur.calculNorme(Vecteur.soustractionNouveau(position, circ.getArrivees().get(circ.getArrivees().size()/2)))<100){
						System.out.println("dans simu, mauvais côté ligne arrivée");
						break;
					}
					//modification de l'image
					im.setRGB((int) position.x, (int) position.y, Color.yellow.getRGB());
					ImageIO.write(im, "png", test);
					//mise a jour de runnable
					runnable=this.runnable();
					
				} catch (RuntimeException e1) {
					//System.out.println("Exception dans simulation");
					if (com.getRotation() > voit.getMaxTurn()) {
						com.setRotation(voit.getMaxTurn());
					}
						
					if (com.getRotation() < -voit.getMaxTurn()) {
						com.setRotation(-voit.getMaxTurn());
					}
				
					if(com.getAcceleration()>1) {
						com.setAcceleration(1);	
					}
					if(com.getAcceleration()<-1) {
						com.setAcceleration(-1);
					}
					
					this.voit.drive(com);
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//mise a jour runnable
					runnable=this.runnable();
					//clonage pour ne pas modifier la valeur de la position de la voiture + modification de l'image
					Vecteur position = voit.getPosition().clonage();
					im.setRGB((int) position.x, (int) position.y, Color.yellow.getRGB());
					ImageIO.write(im, "png", test);
					
				}catch(IOException e) {
					System.out.println("erreur lors de la sauvegarde");
				}
				
				//System.out.println("position : "+ voit.getPosition());
				//verification ligne d'arrivee
				int i= (int) voit.getPosition().getX();
				int j= (int) voit.getPosition().getY();
				if (circ.getTerrainImage(i,j)== Terrain.EndLine) {
					System.out.println("A passé ligne arrivée ");
					break;
				}
				//verification : pourquoi sort de la boucle while 
				if (voit.getPosition().getY() > circ.getHeight() && voit.getPosition().getX() > circ.getWidth()&& voit.getPosition().getY() <0 && voit.getPosition().getX() <0) {
					System.out.println("sorti du terrain");
				}
			}else {
				System.out.println("Fin de la liste de commandes");
				break;
			}
		}
	}
	
	
	 public boolean runnable() throws TerrainException {
		if(!TerrainTools.isRunnable(this.circ.getTerrainImage(voit.getPosition()))){
			System.out.println("no runnable");
			System.out.println("position au moment ou c pas runnable= "+ this.circ.getTerrainImage(voit.getPosition()));
			return false;
		}
		if(circ.getTerrainImage(voit.getPosition())== Terrain.EndLine) {
			System.out.println("endline");
			return false;
		}
		return true;
	}
	

	//Accesseur:
	public Circuit getCirc() {
			return this.circ;
	}
	public Voiture getVoit() {
			return voit;
	}
	
	public int getNbCoups() {
		return nbCoups;
	}
	
	public ArrayList<Commande> getRecord(){
     return record;
    }
	
	////////////////////////////////////////////////////////////////////////////////////////
	//pour savegarder la liste de commandes
	public static void saveListeCommande(ArrayList<Commande> liste, String filename){
        try {
                DataOutputStream os = new DataOutputStream(new FileOutputStream(filename));
                for(Commande c:liste){
                        os.writeDouble(c.getAcceleration());
                        os.writeDouble(c.getRotation());
                }
                os.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
	}
	//pour charger la liste de commandes
	public static ArrayList<Commande> loadListeCommande(  String filename) throws IOException{
        ArrayList<Commande> liste = null;
        DataInputStream os =null;
        try {
                os = new DataInputStream(new FileInputStream(filename));

                liste = new ArrayList<Commande>();
                double a,t;
                while(true){ // on attend la fin de fichier
                        a = os.readDouble();
                        t = os.readDouble();
                        liste.add(new Commande(a,t));
                }

        } catch (EOFException e){
                return liste;
        } finally {
        	if (os!= null) os.close();
        }

	}
	
	
}
