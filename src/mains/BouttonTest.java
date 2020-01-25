package mains;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BouttonTest {

	public static void main(String[] args){
		JFrame fenetre=new JFrame("Simulation 2I013");
		fenetre.setSize(1200,805);
		JPanel j=new JPanel();
		fenetre.add(j);
		int nb_circuits=12;
		JButton[] boutons=new JButton[nb_circuits];
		for(int i=1;i<nb_circuits;i++){
			boutons[i]=new JButton("Circuit"+(i));
			j.add(boutons[i]);
		}
		@SuppressWarnings("unused")
		Container conteneurPrincipal=fenetre.getContentPane();
		
	fenetre.setVisible(true);
	fenetre.pack();
	}
}
