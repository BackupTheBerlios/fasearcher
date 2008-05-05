package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;

public class FrameCadenas extends JFrame {

	private static final long serialVersionUID = -5265797306677288029L;
	
	private JList aceptadas;
	
	private JList rechazadas;
	
	private Problema problema;
	
	private AF af;
	
	public FrameCadenas(AF af, Problema problema) {
		super("Manipular cadenas");
		this.problema = problema;
		this.af = af;
		
		setLayout(new GridLayout(2,3));
		aceptadas = new JList();
		rechazadas = new JList();
		add(aceptadas);
		
		JButton quitar = new JButton("Quitar aceptada");
		quitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.quitarAceptada();
			}
		});		
		add(quitar);
		
		add(rechazadas);
		
		quitar = new JButton("Quitar rechazada");
		quitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.quitarRechazada();
			}
		});		
		add(quitar);
		
		JButton agergar = new JButton("Añadir cadena");
		agergar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.agregarCadena();
			}
		});
		add(agergar);
		
		JButton cerrar = new JButton("Terminar");
		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.cerrar();
			}
		});
		add(cerrar);
		
		llenarListas();
		
		setVisible(true);
		validate();
		pack();
		
	}

	private void llenarListas() {
		List<String> listaAceptadas = new ArrayList<String>();
		List<String> listaRechazadas = new ArrayList<String>();
		for (String cadena : problema.getCadenas()) {
			if (af.acepta(cadena))
				listaAceptadas.add(cadena);
			else
				listaRechazadas.add(cadena);
		}
		
		aceptadas.setListData(listaAceptadas.toArray());
		rechazadas.setListData(listaRechazadas.toArray());
		validate();
	}

	protected void quitarRechazada() {
		if (rechazadas.getSelectedValue() != null) {
			problema.getCadenas().remove(rechazadas.getSelectedValue());
			llenarListas();
		}
	}

	protected void quitarAceptada() {
		if (aceptadas.getSelectedValue() != null) {
			problema.getCadenas().remove(aceptadas.getSelectedValue());
			llenarListas();
		}
	}

	protected void cerrar() {
		setVisible(false);
	}

	protected void agregarCadena() {
		String cadena = obtenerCadena();
		if (cadena != null) {
			problema.getCadenas().add(cadena);
			llenarListas();
		} else {
			JOptionPane.showMessageDialog(this, "Cadena no valida");
		}
	}

	private String obtenerCadena() {
		String temp = JOptionPane.showInputDialog("Cadena");
		if (temp.matches("[01]+"))
			return temp;
		return null;
	}

	
}
