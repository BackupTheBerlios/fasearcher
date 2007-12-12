package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;
import Dibujante;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class PruebaDibujante3 {

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

		estados = 6;
		transiciones= new double[estados][2][estados+1];
		
		transiciones[0][1][0]=1.0;
		transiciones[0][1][3]=1.0;
		
		transiciones[1][0][2]=0.6;
		transiciones[1][1][2]=0.6;
		
		/*
		transiciones[0][1][0]=1.0;
		transiciones[1][1][0]=1.0;
		transiciones[2][1][0]=1.0;
		transiciones[3][1][0]=1.0;
		
		transiciones [4][0][5]=1.0;
		transiciones[2][0][4]=1.0;
		
		
		transiciones[0][1][3]=1.0;
		transiciones[1][0][2]=1.0;
		transiciones[1][1][2]=1.0;
		*/
		
		transiciones[1][0][1]=1.0;
		transiciones[2][0][2]=1.0;
		transiciones[0][0][0]=1.0;
		
		//transiciones[5][1][5]=1.0;
		
		
		//transiciones[0][1][2]=1.0;
		transiciones[2][0][0]=1.0;
		//transiciones[3][0][1]=1.0;

		
	}

}

