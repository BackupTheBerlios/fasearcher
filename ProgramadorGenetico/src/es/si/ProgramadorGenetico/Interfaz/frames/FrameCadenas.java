package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;

public class FrameCadenas extends JFrame {

	private static final long serialVersionUID = -5265797306677288029L;
	
	private JList aceptadas;
	
	private JList rechazadas;
	
	private Problema problema;
	
	private List<String> listAceptadas;
	
	private List<String> listRechazadas;
	
	private AF af;
	
	public FrameCadenas(AF af, Problema problema) {
		super("Manipular cadenas");
		this.problema = problema;
		this.af = af;
		
		setLayout(new BorderLayout());

		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(1,2));
		temp.add(crearPanelAceptadas());
		
		temp.add(crearPanelRechazadas());
		
		add(temp, BorderLayout.CENTER);
		
		JButton agergar = new JButton("Añadir cadena");
		agergar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.agregarCadena();
			}
		});
		add(agergar, BorderLayout.NORTH);
		
		JButton cerrar = new JButton("Terminar");
		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.cerrar();
			}
		});
		add(cerrar, BorderLayout.SOUTH);
		
		llenarListas();
		
		setVisible(true);
		validate();
		setSize(400,400);
	}

	public FrameCadenas(List<String> listAceptadas, List<String> listRechazadas) {
		super("Manipular cadenas");
		this.listAceptadas = listAceptadas;
		this.listRechazadas = listRechazadas;
		
		setLayout(new BorderLayout());

		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(1,2));
		temp.add(crearPanelAceptadas());
		
		temp.add(crearPanelRechazadas());
		
		add(temp, BorderLayout.CENTER);
		
		temp = new JPanel();
		JButton agergar = new JButton("Añadir aceptada");
		agergar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.agregarAceptada();
			}
		});
		temp.add(agergar);
		
		agergar = new JButton("Añadir rechazada");
		agergar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.agregarRechazada();
			}
		});
		temp.add(agergar);
		
		
		add(temp, BorderLayout.NORTH);
		
		JButton cerrar = new JButton("Terminar");
		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.cerrar();
			}
		});
		add(cerrar, BorderLayout.SOUTH);
		
		llenarListas();
		
		setVisible(true);
		validate();
		setSize(400,400);
	}
	
	protected void agregarRechazada() {
		String cadena = obtenerCadena();
		if (cadena != null && !listAceptadas.contains(cadena)) {
			listRechazadas.add(cadena);
			llenarListas();
		} else {
			JOptionPane.showMessageDialog(this, "Cadena no valida");
		}
	}

	protected void agregarAceptada() {
		String cadena = obtenerCadena();
		if (cadena != null && !listRechazadas.contains(cadena)) {
			listAceptadas.add(cadena);
			llenarListas();
		} else {
			JOptionPane.showMessageDialog(this, "Cadena no valida");
		}
	}

	private JPanel crearPanelRechazadas() {
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.PAGE_AXIS));
		rechazadas = new JList();
		JScrollPane sp = new JScrollPane(rechazadas);
		temp.add(sp);
		
		JButton quitar = new JButton("Quitar rechazada");
		quitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.quitarRechazada();
			}
		});		
		temp.add(quitar);
		return temp;
	}

	private JPanel crearPanelAceptadas() {
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.PAGE_AXIS));
		aceptadas = new JList();
		JScrollPane sp = new JScrollPane(aceptadas);
		temp.add(sp);
		JButton quitar = new JButton("Quitar aceptada");
		quitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCadenas.this.quitarAceptada();
			}
		});		
		temp.add(quitar);
		return temp;
	}

	private void llenarListas() {
		if (problema != null) {
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
		} else {
			aceptadas.setListData(listAceptadas.toArray());
			rechazadas.setListData(listRechazadas.toArray());			
		}

		validate();
	}

	protected void quitarRechazada() {
		if (rechazadas.getSelectedValue() != null) {
			if (problema != null)
				problema.getCadenas().remove(rechazadas.getSelectedValue());
			else
				listRechazadas.remove(rechazadas.getSelectedValue());
			llenarListas();
		}
	}

	protected void quitarAceptada() {
		if (aceptadas.getSelectedValue() != null) {
			if (problema != null)
				problema.getCadenas().remove(aceptadas.getSelectedValue());
			else
				listAceptadas.remove(aceptadas.getSelectedValue());
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
