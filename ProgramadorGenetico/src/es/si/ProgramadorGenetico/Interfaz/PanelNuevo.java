package es.si.ProgramadorGenetico.Interfaz;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelNuevo extends JPanel{
	
	public PanelNuevo () {
		super();
		setLayout(new FlowLayout());		
		JLabel label1 = new JLabel("Muestras");
		add(label1);
		label1 = new JLabel("Poblaci�n m�xima");
		add(label1);
		label1 = new JLabel("Estados");
		add(label1);
		label1 = new JLabel("Cadenas aceptadas");
		add(label1);
		label1 = new JLabel("Cadenas rechazadas");
		add(label1);				
	}
}
