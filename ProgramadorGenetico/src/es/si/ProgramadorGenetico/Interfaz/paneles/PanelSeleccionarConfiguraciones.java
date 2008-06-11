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

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameEstadisticasAvanzadas;
import es.si.ProgramadorGenetico.WS.GetProblemaWS;
/**
 * Panel para seleccionar configuraciones
 *
 */
public class PanelSeleccionarConfiguraciones extends JPanel {

	private static final long serialVersionUID = -2750855309074355489L;
	/**
	 * Tabla de configuraciones
	 */
	private JTable tabla_configs;
	/**
	 * Modelo de la tabla de configuraciones
	 */
	private ConfiguracionesTableModel model_configs;
	/**
	 * Tabla de los seleccionados
	 */
	private JTable tabla_seleccionados;
	/**
	 * Modelo de la tabla de seleccionados
	 */
	private ConfiguracionesTableModel model_seleccionados;
	/**
	 * Lista de configuraciones
	 */
	private List<Configuracion> configs;
	/**
	 * Lista de configuraciones seleccionadas
	 */
	private List<Configuracion> seleccionados = new ArrayList<Configuracion>();
	/**
	 * Id del problema
	 */
	private String id_problema;
	/**
	 * Frame de estadisticas avanzadas
	 */
	private FrameEstadisticasAvanzadas frame;
	/**
	 * Constructora que inicializa el panel mediante los datos pasados
	 * por parametro
	 * @param frameEstadisticasAvanzadas
	 * @param problema
	 */
	public PanelSeleccionarConfiguraciones(FrameEstadisticasAvanzadas frameEstadisticasAvanzadas, Problema problema) {
		frame = frameEstadisticasAvanzadas;
		id_problema = problema.getId();
		
		setLayout(new BorderLayout());
		
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		
		model_configs = new ConfiguracionesTableModel();
		tabla_configs = new JTable(model_configs);
		JScrollPane panelTabla = new JScrollPane(tabla_configs);
		panelTabla.setSize(400, 100);
		llenarTabla();
		temp.add(panelTabla);
		
		model_seleccionados = new ConfiguracionesTableModel();
		tabla_seleccionados = new JTable(model_seleccionados);
		panelTabla = new JScrollPane(tabla_seleccionados);
		panelTabla.setSize(400, 100);
		temp.add(panelTabla);

		add(temp, BorderLayout.CENTER);
		
		
		temp = new JPanel();
		
		JButton boton = new JButton("Añadir configuracion");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelSeleccionarConfiguraciones.this.agergarConfig();
			}
		});
		temp.add(boton);
		
		boton = new JButton("Usar seleccionas");
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
	/**
	 * Agrega la configuracion seleccionada
	 */
	protected void agergarConfig() {
		if (tabla_configs.getSelectedRow() != -1) {
			seleccionados.add(configs.get(tabla_configs.getSelectedRow()));
		}
		llenarTablaSeleccionado();
		revalidate();
		repaint();
	}
	/**
	 * Indica que se elige usar las configuraciones seleccionadas 
	 */
	protected void usarSeleccionados() {
		frame.configuracionesElegidas(seleccionados);
	}
	/**
	 * Indica que se deben usar todas las configuraciones
	 */
	protected void usarTodos() {
		frame.configuracionesElegidas(null);
	}
	/**
	 * Llena la tabla de configuraciones
	 */
	private void llenarTabla() {
		for (int i = 0; i < model_configs.getColumnCount(); i++)
			tabla_configs.getColumnModel().getColumn(i).setHeaderValue(model_configs.getColumnName(i));
		tabla_configs.getTableHeader().resizeAndRepaint();
		GetProblemaWS getProblemaWS = new GetProblemaWS();
		getProblemaWS.setId(id_problema);
		getProblemaWS.ejecutar();
		configs = new ArrayList<Configuracion>();
		for (Configuracion config : getProblemaWS.getConfiguraciones()) {
			model_configs.addConfig(config);
			configs.add(config);
		}
	}
	/**
	 * Llena la tabla de configuraciones seleccionadas
	 */	
	private void llenarTablaSeleccionado() {
		for (int i = 0; i < model_seleccionados.getColumnCount(); i++)
			tabla_seleccionados.getColumnModel().getColumn(i).setHeaderValue(model_configs.getColumnName(i));
		tabla_seleccionados.getTableHeader().resizeAndRepaint();
		model_seleccionados.clear();
		for (Configuracion config : seleccionados) {
			model_seleccionados.addConfig(config);
		}
	}
}
