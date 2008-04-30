package es.si.ProgramadorGenetico.Interfaz.Pruebas;

import es.si.ProgramadorGenetico.Interfaz.paneles.SubPanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelTablaTransiciones;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class PruebaDibujanteNuevo1 {

	private static float[][][] transiciones;
	
	private static float[] probabilidadFinal;
	
	private static int estados;
	
	public static void main (String[] args) {	
		setValoresEntrada();		
		JFrame f = new JFrame("Dibujante automatas");
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
        f.add(new SubPanelAF(transiciones,probabilidadFinal,estados));
        f.add(new PanelTablaTransiciones(transiciones,probabilidadFinal,estados));
        f.pack();
        f.setVisible(true);	      	   	   	   	
	}
	
	public static void setValoresEntrada() {
		estados = 4;
		transiciones= new float[estados][2][estados+1];
		transiciones[0][1][0]=1.0f;
		transiciones[1][1][0]=0.4f;
		transiciones[2][1][0]=1.0f;
		transiciones[2][0][0]=1.0f;
		transiciones[3][1][0]=1.0f;
		transiciones[1][0][2]=0.9f;
		transiciones[2][1][2]=0.3f;		
			
	}
	
}
