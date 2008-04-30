package es.si.ProgramadorGenetico.GeneradorAutomatico.Pruebas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorAF;
import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
import es.si.ProgramadorGenetico.Interfaz.paneles.SubPanelAF;

public class PruebaGenerador {

	private static int estados;
	private static SubPanelAF dibujante;
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
		setValoresEntrada();
		AF mejor = generador.getAF();			
		dibujante = new SubPanelAF(mejor);		
	}
	
	
	public static void setValoresEntrada() {		
		estados = 5;
		generador = new GeneradorAF (estados);
		System.out.println(generador.getAF().toString());
		
	}
	
}
