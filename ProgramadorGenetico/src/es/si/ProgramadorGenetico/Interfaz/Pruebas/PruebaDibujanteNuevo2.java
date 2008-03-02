package es.si.ProgramadorGenetico.Interfaz.Pruebas;
import es.si.ProgramadorGenetico.Interfaz.DibujanteNuevo;
import es.si.ProgramadorGenetico.Interfaz.PanelTablaTransiciones;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class PruebaDibujanteNuevo2 {

	private static double[][][] transiciones;
	
	private static double[] probabilidadFinal;
	
	private static int estados;
		
	
	
	public static void main (String[] args) {	
		
		setValoresEntrada();		
		JFrame f = new JFrame("Dibujante automatas");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new FlowLayout());
        
        //JComponent newContentPane = new DibujanteNuevo(transiciones,probabilidadFinal,estados);
        //newContentPane.setOpaque(true); //content panes must be opaque
        //f.setContentPane(newContentPane);                
        f.add(new DibujanteNuevo(transiciones,probabilidadFinal,estados));
        f.add(new PanelTablaTransiciones(transiciones,probabilidadFinal,estados));
        f.pack();
        f.setVisible(true);	      	   	   	   	
	}
	
	public static void setValoresEntrada() {
		
		estados = 4;
		transiciones= new double[estados][2][estados+1];
		transiciones[0][0][0]=1.0;
		transiciones[0][1][1]=1.0;
		transiciones[1][1][2]=0.4;
		transiciones[2][1][3]=1.0;
		transiciones[3][0][0]=1.0;
		
			
	}
	
}

