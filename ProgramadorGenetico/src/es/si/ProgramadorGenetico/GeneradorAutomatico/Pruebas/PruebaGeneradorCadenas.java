package es.si.ProgramadorGenetico.GeneradorAutomatico.Pruebas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.si.ProgramadorGenetico.GeneradorAutomatico.AF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorAF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorCadenas;
import es.si.ProgramadorGenetico.Interfaz.DibujanteAF;
import es.si.ProgramadorGenetico.Interfaz.DibujanteAFP;
import es.si.ProgramadorGenetico.Interfaz.FramePrincipal;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public class PruebaGeneradorCadenas {

	private static int estados;
	private static double[][][] transiciones;
	private static double[] probabilidadFinal;
	private static DibujanteAF dibujante;
	private static GeneradorAF generador;
	
	public static void main (String[] args) {
		JFrame f = new JFrame("Dibujante automatas");		
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
        f.pack();
        f.setVisible(true);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);
        inicializar();
        
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1000,1000));
		panel.add(dibujante);						
		f.add(panel);
		f.paintComponents(f.getGraphics());
				
		
	}
	
	public static void inicializar () {				
		estados = 5;
		generador = new GeneradorAF (estados);		
		System.out.println(generador.getAF().toString());
		GeneradorCadenas genCad = new GeneradorCadenas (generador.getAF());
		System.out.println(genCad.toString());
		AF mejor = generador.getAF();			
		dibujante = new DibujanteAF(mejor);		
	}
		
}




