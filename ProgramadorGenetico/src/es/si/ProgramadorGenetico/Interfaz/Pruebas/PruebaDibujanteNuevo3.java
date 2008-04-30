package es.si.ProgramadorGenetico.Interfaz.Pruebas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.si.ProgramadorGenetico.Interfaz.paneles.SubPanelAF;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public class PruebaDibujanteNuevo3 {

	private static int estados;
	private static float[][][] transiciones;
	private static float[] probabilidadFinal;
	private static SubPanelAF dibujante;
	
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
		dibujante = new SubPanelAF(mejor);		
	}
	
	
	public static void setValoresEntrada() {		
		estados = 4;		
		transiciones= new float[estados][2][estados+1];
		transiciones[0][0][0]=0.7f;
		transiciones[0][1][0]=0.5f;
		transiciones[1][1][0]=0.4f;
		transiciones[2][1][0]=1.0f;
		transiciones[2][0][0]=0.5f;
		transiciones[3][0][0]=0.8f;
		transiciones[3][1][0]=0.2f;
		transiciones[1][0][2]=0.9f;
		transiciones[2][1][2]=0.3f;
		probabilidadFinal = new float[estados];
		probabilidadFinal[0]=0.0f;
		probabilidadFinal[1]=0.0f;
		probabilidadFinal[2]=1.0f;
		probabilidadFinal[3]=1.0f;		
		
			
	}
	
}
