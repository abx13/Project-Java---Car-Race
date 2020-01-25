package simulation;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainException;
import circuit.TerrainTools;
import commande.Commande;
import geometrie.Vecteur;
import mvc.UpdateEventListener;
import mvc.UpdateEventSender;
import strategy.Strategy;
import voiture.Voiture;

public class SimulationMVC implements UpdateEventSender{
	
	private Voiture voit;
	private Circuit circ;
	private Strategy strat;
	private ArrayList<Commande> record;
	private ArrayList<UpdateEventListener> list;
	private static int nbCoups =0;
	
	
	public SimulationMVC(Voiture voit,Circuit circ, Strategy strat ) {
		this.voit=voit;
		this.circ=circ;
		this.strat=strat;
		this.list= new ArrayList<UpdateEventListener>();
		this.record=new ArrayList<Commande>();
		
	}
	
	
	
	//affichage de la course de la voiture en direct, uniquement utilisable avec le modèle MVC
	public void play() throws IOException, TerrainException, Exception{
				
		boolean runnable = true;
		
		//tant que la voiture reste dans le circuit runnable
		while(voit.getPosition().getY() >0 && voit.getPosition().getX() >0 && voit.getPosition().getY() < circ.getHeight() && voit.getPosition().getX() < circ.getWidth() && runnable)
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
					//on fait un clonage pour être sur de ne pas modifier la direction
					Vecteur direction = voit.getDirection().clonage();
					Vecteur position = voit.getPosition().clonage();
					//System.out.println(position.toString());
					//on rajoute une condition disant que la voiture doit être proche de la ligne d'arrivee pour qu'elle n'aille pas en arrière parce que sinon la voiture ne peut pas faire le circuit en entier et est bloquée
					if(Vecteur.produitScal(direction,circ.getDirectionArrivee())<=0 && circ.getDist((int)(Math.abs(position.getX()- circ.getArrivees().get(circ.getArrivees().size()/2).getX())),(int)(Math.abs(position.getY()- circ.getArrivees().get(circ.getArrivees().size()/2).getY()))) <10){
						System.out.println("dans simu, mauvais côté ligne arrivée");
						break;
					}
					//mises a jour
					update();
					runnable=this.runnable();
					
				} catch (RuntimeException e1) {
					//System.out.println("Exception dans simulation");
					//dans le drive de voiture impl si la rotation est sup au max turn ou si  l'acceleration est supérieure à 1 ou inférieure à -1 on throw une RunTime Exception 
					//si la rotation est supérieure au max turn on la remet au max turn et on met l'acceleration à -1
					if (com.getRotation() > voit.getMaxTurn()) {
						com.setRotation(voit.getMaxTurn());
						com.setAcceleration(-1);
					}
					//si la rotation est inferieur au max turn on la remet au max turn et on met l'acceleration à -1	
					if (com.getRotation() < -voit.getMaxTurn()) {
						com.setRotation(-voit.getMaxTurn());
						com.setAcceleration(-1);
					}
					//si l'acceleration est supérieure a 1 on la met à 1
					if(com.getAcceleration()>1) {
						com.setAcceleration(1);	
					}
					//si l'acceleration est inférieure a 1 on la met à 1
					if(com.getAcceleration()<-1) {
						com.setAcceleration(-1);
					}
					
					this.voit.drive(com);
					//System.out.println(voit.getPosition().toString());
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//mises a jour
					update();
					runnable=this.runnable();
					
					//verification passage ligne d'arrivée
					int i= (int) voit.getPosition().getX();
					int j= (int) voit.getPosition().getY();
					if (circ.getTerrainImage(i,j)== Terrain.EndLine) {
						System.out.println("A passé ligne arrivée ");
						break;
					}
			
				}
				//verification: pourquoi sort de la boucle while
				if (voit.getPosition().getY() > circ.getHeight() && voit.getPosition().getX() > circ.getWidth()&& voit.getPosition().getY() <0 && voit.getPosition().getX() <0) {
					System.out.println("sorti du terrain");
				}
			}else {
				System.out.println("Fin de la liste de commandes");
				return;
			}
		}
		saveListeCommande(record, "Commande.txt");
	}
	
	
	 public boolean runnable() throws TerrainException {
		if(!TerrainTools.isRunnable(this.circ.getTerrainImage(voit.getPosition())))
		{
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
	
	//update pour MVC
	public void update() throws IOException {
			for (UpdateEventListener listener : list) {
				listener.manageUpdate();
			}
	}
	
	//ajout des listeners dans la liste	
	public void add(UpdateEventListener l) {
		list.add(l);
	}
	
	//Accesseur:
	public Circuit getCirc() {
		return this.circ;
	}
	public Voiture getVoit() {
		return voit;
	}
	
	public ArrayList<Commande> getRecord(){
     return record;
    }
	
	public int getNbCoups() {
		return nbCoups;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	//sauvegarder la liste de commandes
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
	//charger la liste de commandes
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


