package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameEstadisticasAvanzadas;
import es.si.ProgramadorGenetico.WS.GetProblemasWS;

public class PanelSeleccionarConfiguraciones extends JPanel {

	private static final long serialVersionUID = -2750855309074355489L;

	private JTable tabla_problemas;

	private ProblemasTableModel model_problemas;

	private JTable tabla_seleccionados;
	
	private ProblemasTableModel model_seleccionados;
	
	private List<Problema> problemas;

	private List<Problema> seleccionados = new ArrayList<Problema>();
	
	private FrameEstadisticasAvanzadas frame;
	public PanelSeleccionarConfiguraciones(FrameEstadisticasAvanzadas frameEstadisticasAvanzadas, Problema problema) {
		
		frame = frameEstadisticasAvanzadas;
		
		setLayout(new BorderLayout());
		
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		
		model_problemas = new ProblemasTableModel();
		tabla_problemas = new JTable(model_problemas);
		JScrollPane panelTabla = new JScrollPane(tabla_problemas);
		panelTabla.setSize(400, 100);
		llenarTabla();
		temp.add(panelTabla);
		
		model_seleccionados = new ProblemasTableModel();
		tabla_seleccionados = new JTable(model_seleccionados);
		panelTabla = new JScrollPane(tabla_seleccionados);
		panelTabla.setSize(400, 100);
		temp.add(panelTabla);

		add(temp, BorderLayout.CENTER);
		
		
		temp = new JPanel();
		
		JButton boton = new JButton("Añadir problema");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelSeleccionarConfiguraciones.this.agergarProblema();
			}
		});
		temp.add(boton);
		
		boton = new JButton("Usar seleccionados");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelSeleccionarConfiguraciones.this.usarSeleccionados();
			}
		});
		temp.add(boton);

		boton = new JButton("Usar todos");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelSeleccionarConfiguraciones.this.usarTodos();
			}
		});
		temp.add(boton);

		add(temp, BorderLayout.SOUTH);
	}
	
	protected void agergarProblema() {
		if (tabla_problemas.getSelectedRow() != -1) {
			seleccionados.add(problemas.get(tabla_problemas.getSelectedRow()));
		}
		llenarTablaSeleccionado();
		revalidate();
		repaint();
	}

	protected void usarSeleccionados() {
		frame.problemasElegidos(seleccionados);
	}

	protected void usarTodos() {
		frame.problemasElegidos(null);
	}

	private void llenarTabla() {
		for (int i = 0; i < model_problemas.getColumnCount(); i++)
			tabla_problemas.getColumnModel().getColumn(i).setHeaderValue(model_problemas.getColumnName(i));
		tabla_problemas.getTableHeader().resizeAndRepaint();
		GetProblemasWS getProblemasWS = new GetProblemasWS();
		getProblemasWS.ejecutar();
		problemas = new ArrayList<Problema>();
		for (GetProblemasWS.Problema problema : getProblemasWS.getProblemas()) {
			model_problemas.addProblema(problema.getId(), problema.getDescripcion(), problema.getSoluciones());
			Problema prob = new Problema();
			prob.setId(problema.getId());
			prob.setDescripcion(problema.getDescripcion());
			problemas.add(prob);
		}
	}
	
	private void llenarTablaSeleccionado() {
		for (int i = 0; i < model_seleccionados.getColumnCount(); i++)
			tabla_seleccionados.getColumnModel().getColumn(i).setHeaderValue(model_problemas.getColumnName(i));
		tabla_seleccionados.getTableHeader().resizeAndRepaint();
		model_seleccionados.clear();
		for (Problema problema : seleccionados) {
			model_seleccionados.addProblema(problema);
		}
	}
}
