package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;
import es.si.ProgramadorGenetico.Interfaz.data.Solucion;
import es.si.ProgramadorGenetico.Interfaz.paneles.ConfiguracionesTableModel;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.ProblemasTableModel;
import es.si.ProgramadorGenetico.Interfaz.paneles.SolucionesTableModel;
import es.si.ProgramadorGenetico.WS.GetProblemasWS;
import es.si.ProgramadorGenetico.WS.GetSolucionWS;
import es.si.ProgramadorGenetico.WS.GetSolucionesWS;
/**
 * Frame que muestra las soluciones a los problemas
 * de la base de datos  
 *
 */
public class FrameSoluciones extends JFrame {

	private static final long serialVersionUID = 3652374573223865373L;
	/**
	 * Modelo de la tabla en la que se muestran los problemas
	 */
	private ProblemasTableModel model_problemas;
	/**
	 * Tabla de los problemas
	 */
	private JTable tabla_problemas;
	/**
	 * Modelo de la tabla de configuraciones
	 */
	private ConfiguracionesTableModel model_configuraciones;
	/**
	 * Tabla donde se muestran las configuraciones
	 */
	private JTable tabla_configuraciones;
	/**
	 * Modelo de la tabla donde se muestran las soluciones
	 */
	private SolucionesTableModel model_soluciones;
	/**
	 * Tabla que muestra las soluciones
	 */
	private JTable tabla_soluciones;
	/**
	 * Lista que almacena las soluciones
	 */
	private List<Solucion> soluciones;
	/**
	 * Lista que almacena las configuraciones
	 */
	private List<Configuracion> configuraciones;
	/**
	 * Indica el problema seleccionado
	 */
	private int problema_select;
	/**
	 * Constructora que incializa y construye las tablas
	 * a partir de los modelos y muestra botones y
	 * otros componentes
	 */
	public FrameSoluciones() {
		super("Explorar soluciones");
		setLayout(new BorderLayout());
		
		JPanel temp = new JPanel();
		temp.setLayout(new BorderLayout());
		model_problemas = new ProblemasTableModel();
		tabla_problemas = new JTable(model_problemas);
		JScrollPane panelTabla = new JScrollPane(tabla_problemas);
		panelTabla.setSize(400, 100);
		temp.add(panelTabla, BorderLayout.CENTER);
		
		JButton boton = new JButton("Mostrar soluciones del problema seleccionado");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameSoluciones.this.mostrarSolucionesProblema();
			}
		});
		temp.add(boton, BorderLayout.SOUTH);

		add(temp, BorderLayout.CENTER);
		
		JPanel panel_configuraciones = new JPanel();
		panel_configuraciones.setLayout(new BorderLayout());
		model_configuraciones = new ConfiguracionesTableModel();
		tabla_configuraciones = new JTable(model_configuraciones);
		panelTabla = new JScrollPane(tabla_configuraciones);
		panelTabla.setSize(400, 200);
		panel_configuraciones.add(panelTabla, BorderLayout.CENTER);
		
		boton = new JButton("Mostrar soluciones con la configuracion seleccionada");
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
		
		add(panel, BorderLayout.EAST);
		
		setSize(600,600);
		setVisible(true);
		inicializar();
	}
	/**
	 * Muestra las soluciones del problema seleccionado
	 */
	protected void mostrarSolucionesProblema() {
		if (tabla_problemas.getSelectedRow() != -1) {
			problema_select = tabla_problemas.getSelectedRow();
			model_configuraciones = new ConfiguracionesTableModel();
			tabla_configuraciones.setModel(model_configuraciones);
			model_soluciones = new SolucionesTableModel();
			tabla_soluciones.setModel(model_soluciones);
			getInfoProblema((String) tabla_problemas.getValueAt(tabla_problemas.getSelectedRow(), 0), null);
			llenarTablaConfiguraciones();
			llenarTablaSoluciones();
			tabla_configuraciones.revalidate();
			tabla_configuraciones.repaint();
			tabla_soluciones.revalidate();
			tabla_soluciones.repaint();
		}
	}
	/**
	 * Muestra el AF del problema seleccionado
	 */
	protected void mostrarAFSeleccion() {
		if (tabla_soluciones.getSelectedRow() != -1) {
			GetSolucionWS getSolucionWS = new GetSolucionWS();
			String id = "" + soluciones.get(tabla_soluciones.getSelectedRow()).getId();
			getSolucionWS.setId(id);
			getSolucionWS.ejecutar();
			JFrame frame = new JFrame("Solucion");
			frame.add(new PanelAF(getSolucionWS.getAfp()));
			frame.setSize(600,600);
			frame.setVisible(true);
			frame.validate();
		}
	}
	/**
	 * Muestra las soluciones de los problemas seleccionados
	 */
	protected void mostrarSolucionesSeleccion() {
		if (tabla_configuraciones.getSelectedRow() != -1) {
			Integer id_config = configuraciones.get(tabla_configuraciones.getSelectedRow()).getId();
			model_soluciones = new SolucionesTableModel();
			tabla_soluciones.setModel(model_soluciones);
			getInfoProblema((String) tabla_problemas.getValueAt(problema_select, 0), id_config);
			llenarTablaSoluciones();
			tabla_soluciones.revalidate();
			tabla_soluciones.repaint();
		}
	}
	/**
	 * Inicializa las tabas
	 */
	private void inicializar() {
		llenarTabla();
		getInfoProblema((String) tabla_problemas.getValueAt(0, 0), null);
		problema_select = 0;
		llenarTablaConfiguraciones();
		llenarTablaSoluciones();
		validate();
	}
	/**
	 * Obtiene la informacion de un problema a partir de los
	 * identificadores de problema y de configuracion
	 * @param id
	 * @param id_configuracion
	 */
	private void getInfoProblema(String id, Integer id_configuracion) {
		GetSolucionesWS getSolucionesWS = new GetSolucionesWS();
		getSolucionesWS.setId(id);
		getSolucionesWS.setId_configuracion(id_configuracion);
		getSolucionesWS.ejecutar();
		
		if (getSolucionesWS.getConfiguraciones() != null && getSolucionesWS.getConfiguraciones().size() > 0) {
			configuraciones = new ArrayList<Configuracion>();
			for (int i = 0; i < getSolucionesWS.getConfiguraciones().size(); i++) {
				Configuracion config = new Configuracion();
				config.setCalculadorBondad(getSolucionesWS.getConfiguraciones().get(i).getCalculadorBondad());
				config.setCruzador(getSolucionesWS.getConfiguraciones().get(i).getCruzador());
				config.setEstados(getSolucionesWS.getConfiguraciones().get(i).getEstados());
				config.setId(getSolucionesWS.getConfiguraciones().get(i).getId());
				config.setMuestras(getSolucionesWS.getConfiguraciones().get(i).getMuestras());
				config.setMutador(getSolucionesWS.getConfiguraciones().get(i).getMutador());
				config.setPobMax(getSolucionesWS.getConfiguraciones().get(i).getPobMax());
				config.setResolver(getSolucionesWS.getConfiguraciones().get(i).getResolver());
				configuraciones.add(config);
			}
		}
		
		soluciones = new ArrayList<Solucion>();
		for (int i = 0; i < getSolucionesWS.getSoluciones().size(); i++) {
			Solucion sol = new Solucion();
			sol.setCalculadorBondad(getSolucionesWS.getSoluciones().get(i).getCalculadorBondad());
			sol.setCruzador(getSolucionesWS.getSoluciones().get(i).getCruzador());
			sol.setEstados(getSolucionesWS.getSoluciones().get(i).getEstados());
			sol.setId(Integer.parseInt(getSolucionesWS.getSoluciones().get(i).getId()));
			sol.setMuestras(getSolucionesWS.getSoluciones().get(i).getMuestras());
			sol.setMutador(getSolucionesWS.getSoluciones().get(i).getMutador());
			sol.setParecidoAF(getSolucionesWS.getSoluciones().get(i).getParecidoAF());
			sol.setPasos(getSolucionesWS.getSoluciones().get(i).getPasos());
			sol.setPobMax(""+getSolucionesWS.getSoluciones().get(i).getPobMax());
			sol.setReconocmiento(getSolucionesWS.getSoluciones().get(i).getReconocimiento());
			sol.setTipoAutomata(getSolucionesWS.getSoluciones().get(i).getTipoAutomata());
			soluciones.add(sol);
		}
		
	}
	/**
	 * Llena la tabla de soluciones
	 */
	private void llenarTablaSoluciones() {
		for (int i = 0; i < model_soluciones.getColumnCount(); i++)
			tabla_soluciones.getColumnModel().getColumn(i).setHeaderValue(model_soluciones.getColumnName(i));
		tabla_soluciones.getTableHeader().resizeAndRepaint();
		if (soluciones != null) {
			for (Solucion sol : soluciones)
				model_soluciones.addSolucion(sol);
		}
	}
	/**
	 * Llena la tabla de problemas
	 */
	private void llenarTabla() {
		for (int i = 0; i < model_problemas.getColumnCount(); i++)
			tabla_problemas.getColumnModel().getColumn(i).setHeaderValue(model_problemas.getColumnName(i));
		tabla_problemas.getTableHeader().resizeAndRepaint();
		GetProblemasWS getProblemasWS = new GetProblemasWS();
		getProblemasWS.ejecutar();
		for (GetProblemasWS.Problema problema : getProblemasWS.getProblemas())
			model_problemas.addProblema(problema.getId(), problema.getDescripcion(), problema.getSoluciones());
	}
	/**
	 * Llena la tabla de configuraciones
	 */
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
