package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.si.ProgramadorGenetico.Interfaz.Dibujante;


public class PruebaDibujante {

	private static double[][][] transiciones;
	
	private static double[] probabilidadFinal;
	
	private static int estados;
		
	
	
	public static void main (String[] args) {	
		
		setValoresEntrada();		
		JFrame f = new JFrame("Dibujante automatas");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
        f.add(new Dibujante(transiciones,probabilidadFinal,estados));
        f.pack();
        f.setVisible(true);	      	   	   	   	
	}
	
	public static void setValoresEntrada() {
		
		estados = 3;
		transiciones= new double[estados][2][estados+1];
		transiciones[0][1][1]=1.0;
		//transiciones[0][1][3]=1.0;
		transiciones[1][0][2]=1.0;
		transiciones[1][1][2]=1.0;
		
			
	}
	
}
