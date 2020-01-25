package filtre;

import circuit.Terrain;
import circuit.TerrainTools;

public class FiltreGenerique implements Filtre{  
	  private boolean[][] window;
	  
	  public FiltreGenerique(boolean[][] window){
		  this.window = window;
	  }

	  public void filtre(Terrain[][] mat){
	    for(int i=0; i<mat.length-window.length-1; i++)
	      for(int j=0; j<mat[0].length-window[0].length-1; j++){        
	        if(matching( mat, i, j)){ // test
	          for(int m=0; m<window.length; m++)
	            for(int n=0; n<window[0].length; n++)
	              mat[i+m][j+n] = Terrain.Herbe; // remplacement
	        }        
	      }
	  }

	  private boolean matching(Terrain[][] mat, int i, int j) {
	    for(int m=0; m<window.length; m++)
	      for(int n=0; n<window[0].length; n++)
	        if(TerrainTools.isRunnable(mat[i+m][j+n]) != window[m][n])
	          return false;
	    return true;
	  }
	}
