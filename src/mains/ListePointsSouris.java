package mains;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import circuit.TerrainTools;
import geometrie.Vecteur;

public class ListePointsSouris {
	public static void main(String[] args)  {
        String filename = "resources/fichier/2_safe.trk";
        Circuit circ = CircuitFactoryFromFile.build(filename);

        JFrame fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final BufferedImage im = TerrainTools.imageFromCircuit(circ.getTerrain()); 
        final JLabel lab = new JLabel(new ImageIcon(im));
        fenetre.add(lab);
        final ArrayList<Vecteur> liste = new ArrayList<Vecteur>();

        lab.addMouseListener(new MouseListener() {

            public void mouseReleased(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == 1) {
                    liste.add(new Vecteur(e.getX(),e.getY()));
                    for(Vecteur v:liste) System.out.print(v+" ");
                    System.out.println();
                    Graphics g = im.getGraphics();
                    g.drawOval(e.getX()-1, e.getY()-1, 2, 2);
                    lab.repaint();
                   
                }              
            }
        });


        fenetre.setVisible(true);
        fenetre.pack();
   }

}
