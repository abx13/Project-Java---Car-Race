package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import geometrie.Vecteur;
import li260.view.observeurs.DijkstraObserveur;
import li260.view.observeurs.ObserveurSWING;
import li260.view.observeurs.PointsObserveur;
import li260.view.observeurs.RadarObserveur;

public class IHMSwing extends JPanel implements UpdateEventListener, ActionListener, MouseListener {
	
	private ArrayList<ObserveurSWING> listeSwing;
	private Vue vue;
	private Modele modele;
	private ArrayList<Vecteur> listePoints;
	private static final long serialVersionUID = 1L;

	private boolean finPointsOK;
	private boolean listeCommandeGO;
	private boolean radarClassiqueGO;
	private boolean radarDijkstraGO;
	private boolean selectorGO;
	private boolean reset;
	private Color color;
	private String filename;
	

	//nouveau constructeur selon la logique calculette
	public IHMSwing() { 
		this.listeSwing=new ArrayList<ObserveurSWING> ();
		this.listePoints= new ArrayList<Vecteur>();
		finPointsOK=false;
		listeCommandeGO=false;
		radarClassiqueGO=false;
		radarDijkstraGO=false;
		selectorGO=false;
		reset=false;
	}
	
	public void reinit() {
		this.listePoints= new ArrayList<Vecteur>();
		finPointsOK=false;
		listeCommandeGO=false;
		radarClassiqueGO=false;
		radarDijkstraGO=false;
		selectorGO=false;
		reset=false;
		int nbpo=0;
		for (int i=0; i<listeSwing.size(); i++) {
			if (listeSwing.get(i) instanceof PointsObserveur ) {
				nbpo++;
			}
		}
		PointsObserveur[] po= new PointsObserveur[nbpo+1];
		nbpo=0;
		for (int i=0; i<listeSwing.size(); i++) {
			if (listeSwing.get(i) instanceof PointsObserveur ) {
				po[nbpo]=(PointsObserveur) listeSwing.get(i);
				nbpo++;
			}
		}
		
		for (int i=0; i<listeSwing.size(); i++) {
			for (int j=0; j<po.length; j++) {
				if (listeSwing.get(i)==po[j])
					listeSwing.remove(i);
			}
		}
		
		for (int i=0; i<listeSwing.size(); i++) {
			if (listeSwing.get(i) instanceof RadarObserveur) {
				listeSwing.remove(i);
				break;
			}
		}
		for (int i=0; i<listeSwing.size(); i++) {
			if (listeSwing.get(i) instanceof DijkstraObserveur) {
				listeSwing.remove(i);
				break;
			}
		}
		
	}
	
	//initialisation de la vue, infaisable avant car la vue n'est pas encore créée. 
	public void initVueModele(Vue vue, Modele modele) {
		this.vue=vue;
		this.modele=modele;
	}
	
	//gestion de l'update de la simulation 
	public void manageUpdate() {
		try {
			Thread.sleep(1);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});
	}
	
	//ajout es observeurs Swing
	public void add(ObserveurSWING l) {
		listeSwing.add(l);
	}
	
	//effectue les print pour tous les observeursSwing
	public void paint(Graphics g) {
		super.paint(g);
		try {
			for (ObserveurSWING o : listeSwing) {
				o.print(g);
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	//gestion des évènements de la souris
	public void mouseClicked(MouseEvent e) {	
		PointsObserveur po = new PointsObserveur (new Vecteur(e.getX(), e.getY()));
		this.add(po);
		listePoints.add(new Vecteur(e.getX(),e.getY()));
		System.out.println("{"+e.getX()+","+e.getY()+"}");
        repaint();
		
	}
	//ne pas enlever même si on ne les utilise pas parce que sinon erreur unimplemented methods
	public void mouseEntered(MouseEvent argO) {}
	public void mouseExited(MouseEvent argO) {}
	public void mousePressed(MouseEvent argO) {}
	public void mouseReleased(MouseEvent argO) {}
	
	
	//gestion des évènements des boutons
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().contentEquals("Fin Points")) {
			vue.setListePoints(listePoints);
			finPointsOK=true;
		}else 	if (e.getActionCommand().contentEquals("Stratégie Liste Commandes")) {
			listeCommandeGO=true;
		} else 	if (e.getActionCommand().contentEquals("Stratégie Radar Classique")) {
			radarClassiqueGO=true;
		} else 	if (e.getActionCommand().contentEquals("Stratégie Radar Dijkstra")) {
			radarDijkstraGO=true;
		} else 	if (e.getActionCommand().contentEquals("Stratégie Selector")) {
			selectorGO=true;
		} else if (e.getActionCommand().contentEquals("Reset")) {
			reset=true;
		} else if (e.getSource() instanceof JComboBox<?>) {
			@SuppressWarnings("unchecked")
			JComboBox<Color> cb = (JComboBox<Color>)e.getSource();
	       
	        switch ((String)cb.getSelectedItem()){
	        case "Jaune" : 
	        	color=Color.YELLOW ;
	        	break;
	        case "Bleu" : 
	        	color=Color.BLUE;
	        	break;
	        case "Orange" : 
	        	color=Color.ORANGE;
	        	break;
	        case "Noir" : 
	        	color=Color.BLACK;
	        	break;
	        case "Rouge" : 
	        	color=Color.RED;
	        	break;
	        case "1_safe.trk":
	        	filename = "resources/fichier/1_safe.trk";
	        	break;
	        case "2_safe.trk":
	        	filename = "resources/fichier/2_safe.trk";
	        	break;
	        case "3_safe.trk":
	        	filename = "resources/fichier/3_safe.trk";
	        	break;
	        case "4_safe.trk":
	        	filename = "resources/fichier/4_safe.trk";
	        	break;
	        case "5_safe.trk":
	        	filename = "resources/fichier/5_safe.trk";
	        	break;
	        case "6_safe.trk":
	        	filename = "resources/fichier/6_safe.trk";
	        	break;
	        case "7_safe.trk":
	        	filename = "resources/fichier/7_safe.trk";
	        	break;
	        case "8_safe.trk":
	        	filename = "resources/fichier/8_safe.trk";
	        	break;
	        }
		}
		
	}
	
	public boolean getFinPointsOK() {
		return finPointsOK;
	}
	
	public boolean getListeCommandeGO() {
		return listeCommandeGO;
	}
	
	public boolean getRadarClassiqueGO() {
		return radarClassiqueGO;
	}
	
	public boolean getRadarDijkstraGO() {
		return radarDijkstraGO;
	}
	
	public boolean getSelectorGO() {
		return selectorGO;
	}
	
	public boolean getReset() {
		return reset;
	}
	
	public PointsObserveur getPointsObserveur() {
		for (int i=0; i<listeSwing.size(); i++) {
			if (listeSwing.get(i) instanceof PointsObserveur) {
				return (PointsObserveur) listeSwing.get(i);
			}
		}
		return null;
	}
	
	public BufferedImage getImage() {
		return circuit.TerrainTools.imageFromCircuit(modele.getCirc().getTerrain());
	}
	
	public ArrayList<ObserveurSWING> getListeObserveurs(){
		return listeSwing;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getFilename() {
		return filename;
	}
}