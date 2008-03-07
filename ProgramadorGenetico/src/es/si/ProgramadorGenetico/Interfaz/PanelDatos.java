package es.si.ProgramadorGenetico.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PanelDatos extends JPanel {

	JTable tablaDatos;
	JLabel labelIntroduccion;
	JTextField campoPalabra;	
    ButtonGroup aceptada;
	
	public PanelDatos() {
		super(new BorderLayout());
		setPreferredSize(new Dimension(800,800));				
        setVisible(true);
        construyeComponentes();   
        
        
	}
	
	public void construyeComponentes() {
		tablaDatos = new JTable();
		labelIntroduccion = new JLabel("Cadena");
		campoPalabra = new JTextField ();
		aceptada = new ButtonGroup();
		JRadioButton radioAceptada = new JRadioButton("Aceptada");
		JRadioButton radioRechazada = new JRadioButton("Rechazada");
		aceptada.add(radioAceptada);
		aceptada.add(radioRechazada);
		JPanel panelRadio = new JPanel();
		panelRadio.add(radioAceptada);
		panelRadio.add(radioRechazada);
		add(panelRadio);	
		
		labelIntroduccion.setBounds(200,200,100,100);
        labelIntroduccion.setVisible(true);
        campoPalabra.setBounds(300,300,200,50);        
        campoPalabra.setVisible(true);
        campoPalabra.setSize(80, 30);
        add(labelIntroduccion);
        add(campoPalabra);
	}
}
