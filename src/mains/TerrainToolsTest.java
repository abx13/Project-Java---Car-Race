package mains;

import java.awt.Color;


import circuit.Terrain;
import circuit.TerrainException;
import circuit.TerrainTools;

public class TerrainToolsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Terrain t = TerrainTools.terrainFromChar('u');
			System.out.println("terrain ="+t);
		} catch (TerrainException e) {
			return ;
		}
		
		char c = TerrainTools.charFromTerrain(Terrain.BandeBlanche);
		System.out.println("char ="+c);
		
		Color color = TerrainTools.terrainToRGB(Terrain.BandeBlanche);
		System.out.println("color ="+color);
		
		System.out.println("isRunnable ="+TerrainTools.isRunnable(Terrain.BandeBlanche));
	}
}
	


