package es.si.ProgramadorGenetico.Interfaz.Pruebas;
import es.si.ProgramadorGenetico.Interfaz.Dibujante;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class PruebaDibujante2 {

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
		estados = 8;
		transiciones= new double[estados][2][estados+1];
		transiciones[0][1][7]=1.0;
		transiciones[6][1][7]=0.4;
		transiciones[2][1][5]=1.0;
		transiciones[2][0][5]=0.6;
				
	}

}

