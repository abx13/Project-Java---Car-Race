package circuit;
import java.awt.Color;

public enum Terrain {
	  Route, Herbe, Eau, Obstacle, BandeRouge, BandeBlanche, 
	     StartPoint, EndLine, Boue;

	   public static char[] conversion = 
	            {'.', 'g', 'b', 'o', 'r', 'w', '*', '!', 'm'};

	   public static Color[] convColor = {Color.gray, Color.green, 
	       Color.blue, Color.black, Color.red, Color.white, 
	       Color.cyan, Color.cyan, new Color(200, 150, 128)};
}
