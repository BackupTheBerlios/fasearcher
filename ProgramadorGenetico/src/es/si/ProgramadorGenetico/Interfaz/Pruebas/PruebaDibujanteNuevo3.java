package es.si.ProgramadorGenetico.Interfaz.Pruebas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.si.ProgramadorGenetico.Interfaz.DibujanteNuevo;
import es.si.ProgramadorGenetico.Interfaz.FramePrincipal;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public class PruebaDibujanteNuevo3 {

	private static int estados;
	private static double[][][] transiciones;
	private static double[] probabilidadFinal;
	private static DibujanteNuevo dibujante;
	
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
		AFP mejor;
		setValoresEntrada();
		mejor = new AFP (estados);
		mejor.setTransiciones(transiciones);
		mejor.setProbabilidadFinal(probabilidadFinal);
		dibujante = new DibujanteNuevo(mejor);		
	}
	
	
	public static void setValoresEntrada() {		
		estados = 4;		
		transiciones= new double[estados][2][estados+1];
		transiciones[0][0][0]=0.7;
		transiciones[0][1][0]=0.5;
		transiciones[1][1][0]=0.4;
		transiciones[2][1][0]=1.0;
		transiciones[2][0][0]=0.5;
		transiciones[3][0][0]=0.8;
		transiciones[3][1][0]=0.2;
		transiciones[1][0][2]=0.9;
		transiciones[2][1][2]=0.3;
		probabilidadFinal = new double[estados];
		probabilidadFinal[0]=0.0;
		probabilidadFinal[1]=0.0;
		probabilidadFinal[2]=1.0;
		probabilidadFinal[3]=1.0;		
		
			
	}
	
}
