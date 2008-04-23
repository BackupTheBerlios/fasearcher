package es.si.ProgramadorGenetico.Interfaz.Pruebas;

import javax.swing.JFrame;

import es.si.ProgramadorGenetico.Interfaz.paneles.PanelDatos;

public class PruebaTablaDatos {

	private static PanelDatos panel;
	
	public static void main (String[] args) {
		JFrame f = new JFrame();		
		panel = new PanelDatos();
		f.add(panel);
		f.pack();
		f.setVisible(true);
	
	}
}
