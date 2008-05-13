package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.si.ProgramadorGenetico.Interfaz.InterfazAdministrador;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.Interfaz.paneles.ProblemasTableModel;
import es.si.ProgramadorGenetico.WS.GetProblemaWS;
import es.si.ProgramadorGenetico.WS.GetProblemasWS;
import es.si.ProgramadorGenetico.WS.RemoveProblemaWS;

public class FrameCargarProblema extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5084997267681444349L;

	
	private ProblemasTableModel model;
	
	private JTable tabla;
	
	private InterfazAdministrador interfaz;
	
	public FrameCargarProblema(InterfazAdministrador interfaz) {
		super("Cargar Problema");
		this.interfaz = interfaz;
		
		setLayout(new BorderLayout());
		
		model = new ProblemasTableModel();
		tabla = new JTable(model);
		llenarTabla();
		JScrollPane panelTabla = new JScrollPane(tabla);
		panelTabla.setSize(400, 200);
		add(panelTabla, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		JButton boton = new JButton("Abrir Problema");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCargarProblema.this.abrirProblema();
			}
		});
		panel.add(boton);
		
		boton = new JButton("Quitar Problema");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCargarProblema.this.quitarProblema();
			}
		});
		panel.add(boton);
		
		add(panel, BorderLayout.SOUTH);
		
		pack();
		setSize(500, 500);
		setVisible(true);
	}

	protected void quitarProblema() {
		if (tabla.getSelectedRow() != -1) {
			int s = JOptionPane.showConfirmDialog(this, "Seguro que desea quitar el problema?", "Quitar Problema", JOptionPane.YES_NO_OPTION);
			if (s == JOptionPane.NO_OPTION)
				return;
			RemoveProblemaWS removeProblemaWS = new RemoveProblemaWS();
			removeProblemaWS.setId((String) tabla.getValueAt(tabla.getSelectedRow(), 0));
			removeProblemaWS.ejecutar();
			
			model.removeProblema(tabla.getSelectedRow());
			tabla.revalidate();
			tabla.repaint();
		}
	}

	protected void abrirProblema() {
		if (tabla.getSelectedRow() != -1) {
			GetProblemaWS getProblemaWS = new GetProblemaWS();
			getProblemaWS.setId((String) tabla.getValueAt(tabla.getSelectedRow(), 0));
			getProblemaWS.ejecutar();
			
			if (!getProblemaWS.getId().equals("-1")) {
				Problema problema = new Problema();
				List<String> cadenas = getProblemaWS.getAceptadas();
				cadenas.addAll(getProblemaWS.getRechazadas());
				problema.setCadenas(cadenas);
				
				problema.setConfiguraciones(getProblemaWS.getConfiguraciones());
				
				problema.setId(getProblemaWS.getId());
				
				problema.setDescripcion(getProblemaWS.getDescripcion());
				
				interfaz.terminoCargar(getProblemaWS.getAfp(), problema);
				setVisible(false);
			}
		}
	}

	private void llenarTabla() {
		for (int i = 0; i < model.getColumnCount(); i++)
			tabla.getColumnModel().getColumn(i).setHeaderValue(model.getColumnName(i));
		tabla.getTableHeader().resizeAndRepaint();
		GetProblemasWS getProblemasWS = new GetProblemasWS();
		getProblemasWS.ejecutar();
		for (GetProblemasWS.Problema problema : getProblemasWS.getProblemas())
			model.addProblema(problema.getId(), problema.getDescripcion(), problema.getSoluciones());
	}
	
}
