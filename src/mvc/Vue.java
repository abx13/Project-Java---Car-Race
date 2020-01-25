package mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import geometrie.Vecteur;

public class Vue  extends JPanel{

	private static final long serialVersionUID = 1L;

	private JFrame fenetre;
	private JButton[] boutons;
	private int nb_boutons;
	@SuppressWarnings("unused")
	private BufferedImage im;
	private JPanel image;	
	private ArrayList<Vecteur> listePoints;
	private IHMSwing ihm;
	private JComboBox<String> menuCouleurs;
	private JComboBox<String> menuCircuits;
	
	public Vue(IHMSwing ihm) throws IOException { 
		this.ihm=ihm;
		listePoints = new ArrayList<Vecteur>();
		//creation de la fenetre
		this.fenetre = new JFrame();
		fenetre.setTitle("Simulation");
		
		//creation panel global 
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		//ajout phrase explicative
		//JLabel explication= new JLabel("<html>Choisissez le circuit, la couleur de la trajectoire et la stratégie que vous voulez.<br>Pour la Stratégie Selector vous devez cliquer avec la souris sur les points que vous voulez ajouter à la liste de points puis cliquer sur Fin Points.<br>Si vous voulez changer de circuit ou de couleur de trajectoire, changez puis cliquez sur Reset.<br> A vous de jouer!</html>");
		//panel1.add(explication, BorderLayout.NORTH);
		//création du panel + choix layout : on peut faire avec flow, border ou grid
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		
		//ajouer un menu déroulant pour les fichiers circuits
		String[] circuits = {"1_safe.trk", "2_safe.trk", "3_safe.trk", "4_safe.trk", "5_safe.trk","6_safe.trk", "7_safe.trk", "8_safe.trk"};
		menuCircuits = new JComboBox<String>(circuits);
		menuCircuits.addActionListener(ihm);
		panel2.add(menuCircuits);
		//ajouter un menu déroulant pour la couleur de la trajectoire
		String[] couleurs = {"Jaune", "Bleu", "Orange", "Noir", "Rouge"};
		menuCouleurs = new JComboBox<String>(couleurs);
		menuCouleurs.addActionListener(ihm);
		panel2.add(menuCouleurs);
				
		//creation des boutons
		nb_boutons=6;
		boutons = new JButton[nb_boutons];
		boutons[0]= new JButton("Stratégie Liste Commandes");
		boutons[1]= new JButton("Stratégie Radar Classique");
		boutons[2]= new JButton("Stratégie Radar Dijkstra");
		boutons[3]= new JButton("Stratégie Selector");
		boutons[4]= new JButton("Fin Points");
		boutons[5]= new JButton("Reset");
		
		for (int i=0; i<boutons.length; i++) {
			boutons[i].addActionListener(ihm);
		}
		//ajout des boutons dans le panel
		for (int i=0; i<boutons.length; i++) {
			panel2.add(boutons[i]);
		}
		
		panel1.add(panel2, BorderLayout.CENTER);
		fenetre.add(panel1, BorderLayout.NORTH);
		
		image= new JPanel();
		image.setPreferredSize(new Dimension(1024,768));
		fenetre.add(image, BorderLayout.CENTER);
		//on met le pack ici pour une fois qu'on a mis tous les composants dans la fenetre, on adapte la taille de la fenetre en conséquence
		fenetre.pack();
	}
	
	public void initIHM() {
		//ajouter l'image dans la fenêtre
		ihm.setPreferredSize(new Dimension(1024,768));
		image.add(ihm);
		fenetre.add(image, BorderLayout.CENTER);
		fenetre.pack();
	}
	//getters et setters
	public JFrame getFenetre() {
		return fenetre;
	}

	public JButton[] getBouttons() {
		return boutons;
	}
	
	public void setNbBoutons(int i) {
		nb_boutons=i;
	}
	
	public JPanel getImageIHM() {
		return image;
	}
	
	public void setListePoints(ArrayList<Vecteur> listePoints) {
		this.listePoints=listePoints; 
	}
	
	public ArrayList<Vecteur> getListePoints(){
		return listePoints;
	}
}