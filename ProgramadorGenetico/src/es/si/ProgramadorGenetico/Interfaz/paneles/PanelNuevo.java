package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelNuevo extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1557985157193018240L;

	public PanelNuevo () {
		super();
		setLayout(new FlowLayout());		
		JLabel label1 = new JLabel("Muestras");
		add(label1);
		label1 = new JLabel("Población máxima");
		add(label1);
		label1 = new JLabel("Estados");
		add(label1);
		label1 = new JLabel("Cadenas aceptadas");
		add(label1);
		label1 = new JLabel("Cadenas rechazadas");
		add(label1);				
	}
}
