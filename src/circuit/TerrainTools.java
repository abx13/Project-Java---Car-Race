package circuit;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class TerrainTools {
	public static Terrain terrainFromChar(char c) throws TerrainException {
		for(int i=0; i<9; i++) {
			if(c==Terrain.conversion[i]) {
				Terrain t=Terrain.values()[i];
				return t;
			}
		}
		new TerrainException() ;
		return null;
		
	}
	
	public static char charFromTerrain(Terrain t) {
		for(int i=0; i<9; i++) {
			if(t==Terrain.values()[i]) {
				char d =Terrain.conversion[i];
				return d;
			}
		}
		return 0;
	}
	
	public static Color terrainToRGB(Terrain t) {
		for(int i=0; i<9; i++) {
			if(t==Terrain.values()[i]) {
				Color color =Terrain.convColor[i];
				return color;
			}
		}
		return null;
	}
	
	
	
	public static boolean isRunnable(Terrain t) { 
		
		switch (TerrainTools.charFromTerrain(t)){
		case '.': return true;
		case 'g': return false;
		case 'b': return false;
		case 'o': return false;
		case 'r': return false;
		case 'w': return false;
		case '*': return true;
		case '!': return true;
		case 'm': return true;

		}
		return false;
	}
	
	
	public static BufferedImage imageFromCircuit(Terrain[][] track) {
		int nColonnes = track[0].length;
        int nLignes = track.length;
        BufferedImage im = new BufferedImage(nColonnes, nLignes, BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<nColonnes;i++) {
        	for(int j=0;j<nLignes;j++) {
        	
        		im.setRGB(i, j, terrainToRGB(track[j][i]).getRGB());
        		
        	}
        }
       /* //sauver l'image dans un fichier
        try {
            File outputfile = new File("saved.png");
            ImageIO.write(im, "png", outputfile);
         } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde");
         }*/
        return im;
	}
	
}
