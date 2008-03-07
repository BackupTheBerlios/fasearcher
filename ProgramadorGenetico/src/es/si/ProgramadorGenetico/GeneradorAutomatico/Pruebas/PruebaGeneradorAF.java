package es.si.ProgramadorGenetico.GeneradorAutomatico.Pruebas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.si.ProgramadorGenetico.GeneradorAutomatico.AF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorAF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorCadenas;
import es.si.ProgramadorGenetico.Interfaz.DibujanteAF;

public class PruebaGeneradorAF {
	private static int estados;
	private static double[][][] transiciones;
	private static double[] probabilidadFinal;	
	private static GeneradorAF generador;
	
	public static void main (String[] args) {
		inicializar();	
	}
	
	public static void inicializar () {		
		setValoresEntrada();				
	}
	
	
	public static void setValoresEntrada() {		
		estados = 4;
		AF af1 = new AF(estados);
		af1.setTransicion(0,0,1);
		af1.setTransicion(0,1,2);
		af1.setTransicion(1,0,1);
		af1.setTransicion(1,1,2);
		af1.setTransicion(2,0,3);
		af1.setTransicion(2,1,3);
		af1.setTransicion(2,0,3);
		af1.setTransicion(2,1,3);
		af1.setTransicion(3,0,3);
		af1.setTransicion(3,1,3);
		af1.setFinal(0);
		af1.setFinal(2);
		af1.setFinal(3);		
		GeneradorCadenas genCad = new GeneradorCadenas (af1);
		System.out.println(genCad.toString());		
	}
}
