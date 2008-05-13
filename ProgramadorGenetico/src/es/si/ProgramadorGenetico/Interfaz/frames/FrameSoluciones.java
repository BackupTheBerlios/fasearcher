package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;
import es.si.ProgramadorGenetico.Interfaz.data.Solucion;
import es.si.ProgramadorGenetico.Interfaz.paneles.ConfiguracionesTableModel;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelConfiguracion;
import es.si.ProgramadorGenetico.Interfaz.paneles.ProblemasTableModel;
import es.si.ProgramadorGenetico.Interfaz.paneles.SolucionesTableModel;
import es.si.ProgramadorGenetico.WS.GetProblemasWS;

public class FrameSoluciones extends JFrame {

	private static final long serialVersionUID = 3652374573223865373L;

	private ProblemasTableModel model_problemas;
	
	private JTable tabla_problemas;
		
	private ConfiguracionesTableModel model_configuraciones;
	
	private JTable tabla_configuraciones;
	
	private SolucionesTableModel model_soluciones;
	
	private JTable tabla_soluciones;
	
	private List<Solucion> soluciones;
	
	private List<Configuracion> configuraciones;
	
	public FrameSoluciones() {
		super("Explorar soluciones");
		setLayout(new BorderLayout());
		
		model_problemas = new ProblemasTableModel();
		tabla_problemas = new JTable(model_problemas);
		JScrollPane panelTabla = new JScrollPane(tabla_problemas);
		panelTabla.setSize(400, 100);
		add(panelTabla, BorderLayout.WEST);
		
		JPanel panel_configuraciones = new JPanel();
		panel_configuraciones.setLayout(new BorderLayout());
		model_configuraciones = new ConfiguracionesTableModel();
		tabla_configuraciones = new JTable(model_configuraciones);
		panelTabla = new JScrollPane(tabla_configuraciones);
		panelTabla.setSize(400, 200);
		panel_configuraciones.add(panelTabla, BorderLayout.CENTER);
		
		JButton boton = new JButton("Mostrar soluciones con la configuracion seleccionada");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameSoluciones.this.mostrarSolucionesSeleccion();
			}
		});
		panel_configuraciones.add(boton, BorderLayout.SOUTH);
		
	
		JPanel panel_soluciones = new JPanel();
		panel_soluciones.setLayout(new BorderLayout());
		model_soluciones = new SolucionesTableModel();
		tabla_soluciones = new JTable(model_soluciones);
		panelTabla = new JScrollPane(tabla_soluciones);
		panelTabla.setSize(400, 200);
		panel_soluciones.add(panelTabla, BorderLayout.CENTER);
		
		boton = new JButton("Mostrar AF de la solucion seleccionada");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameSoluciones.this.mostrarAFSeleccion();
			}
		});
		panel_soluciones.add(boton, BorderLayout.SOUTH);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		panel.add(panel_configuraciones);
		panel.add(panel_soluciones);
		
		add(panel, BorderLayout.CENTER);
		
		setSize(600,600);
		setVisible(true);
		//inicializar();
	}
	
	protected void mostrarAFSeleccion() {
		// TODO Auto-generated method stub
		
	}

	protected void mostrarSolucionesSeleccion() {
		// TODO Auto-generated method stub
		
	}

	private void inicializar() {
		llenarTabla();
		getInfoProblema((String) tabla_problemas.getValueAt(1, 1));
		llenarTablaConfiguraciones();
		llenarTablaSoluciones();
	}

	private void getInfoProblema(String id) {
		// TODO Auto-generated method stub
		
	}

	private void llenarTablaSoluciones() {
		// TODO Auto-generated method stub
		
	}

	private void llenarTabla() {
		for (int i = 0; i < model_problemas.getColumnCount(); i++)
			tabla_problemas.getColumnModel().getColumn(i).setHeaderValue(model_problemas.getColumnName(i));
		tabla_problemas.getTableHeader().resizeAndRepaint();
		GetProblemasWS getProblemasWS = new GetProblemasWS();
		getProblemasWS.ejecutar();
		for (GetProblemasWS.Problema problema : getProblemasWS.getProblemas())
			model_problemas.addProblema(problema.getId(), problema.getDescripcion(), problema.getSoluciones());
	}
	
	private void llenarTablaConfiguraciones() {
		for (int i = 0; i < model_configuraciones.getColumnCount(); i++)
			tabla_configuraciones.getColumnModel().getColumn(i).setHeaderValue(model_configuraciones.getColumnName(i));
		tabla_configuraciones.getTableHeader().resizeAndRepaint();
		
		if (configuraciones != null) {
			for (Configuracion config : configuraciones)
				model_configuraciones.addConfig(config);
		}
	}
}
