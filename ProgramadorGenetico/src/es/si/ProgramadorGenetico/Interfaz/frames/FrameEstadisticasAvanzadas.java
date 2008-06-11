package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.Interfaz.data.Stats;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelElegir;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelSeleccionarConfiguraciones;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelSeleccionarProblemas;
import es.si.ProgramadorGenetico.WS.GetAdvancedStatsWS;
import es.si.ProgramadorGenetico.WS.GetValidValuesWS;
/**
 * Frame que muestra las estadisticas avanzadas de los
 * problemas de la base de datos
 *
 */
public class FrameEstadisticasAvanzadas extends JFrame {
	
	private static final long serialVersionUID = -4916806235119316525L;
	/**
	 * Lista de todos los problemas
	 */
	private List<Problema> problemas;
	/**
	 * Lista de todas las configuraciones
	 */
	private List<Configuracion> configuraciones;
	/**
	 * Panel en el que seleccionan los problemas
	 */
	private PanelSeleccionarProblemas panelSeleccionarProblemas;
	/**
	 * Panel en el que se seleccionan las configuraciones
	 */
	private PanelSeleccionarConfiguraciones panelSeleccionarConfiguraciones;
	/**
	 * Panel para elegir los cruzadores
	 */
	private PanelElegir curzadores;
	/**
	 * Panel para elegir los mutadores
	 */
	private PanelElegir mutadores;
	/**
	 * Panel para elegir la fucnion de bondad
	 */
	private PanelElegir funcBondad;
	/**
	 * Panel para elegir el numero de estado
	 */
	private PanelElegir estados;
	/**
	 * Panel para elegir la poblacion maxima
	 */
	private PanelElegir pobMax;
	/**
	 * Panel para elegir las muestras
	 */
	private PanelElegir muestras;
	/**
	 * Panel para elegir el numero de pasos
	 */
	private PanelElegir pasos;
	
	/**
	 * Constructora que inicializa el panel
	 */
	public FrameEstadisticasAvanzadas() {
		panelSeleccionarProblemas = new PanelSeleccionarProblemas(this);
		add(panelSeleccionarProblemas);
		setVisible(true);
		setSize(600,600);
	}
	/**
	 * Guarda los problemas seleccionados
	 * @param seleccionados
	 */
	public void problemasElegidos(List<Problema> seleccionados) {
		problemas = seleccionados;
		remove(panelSeleccionarProblemas);
		if (seleccionados != null && seleccionados.size() == 0) {
				seleccionados = null;
		}
		if (seleccionados != null && seleccionados.size() == 1)
			elegirConfiguracion();
		else
			configuracionesElegidas(null);
	}
	/**
	 * Muestra el panel de seleccionar configuraciones 
	 */
	private void elegirConfiguracion() {
		panelSeleccionarConfiguraciones = new PanelSeleccionarConfiguraciones(this, problemas.get(0));
		add(panelSeleccionarConfiguraciones);
		validate();
		repaint();
	}
	/**
	 * Se guardan las configuraciones elegidas
	 * @param configuraciones
	 */
	public void configuracionesElegidas(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
		
		if (panelSeleccionarConfiguraciones != null)
			remove(panelSeleccionarConfiguraciones);
		GetValidValuesWS ws = new GetValidValuesWS();
		
		if (configuraciones != null && configuraciones.size() == 1)
			ws.setConfiguracion(configuraciones.get(0).getId());
		List<String> problemas_string = new ArrayList<String>();
		if (problemas != null) {
			for (Problema prob : problemas)
				problemas_string.add(prob.getId());
		}
		ws.setProblemas(problemas_string);
		
		ws.ejecutar();
		
		setLayout(new GridLayout(8, 1));
		
		curzadores = new PanelElegir("Cruzadores");
		curzadores.setLista_string(ws.getCruzadores());
		add(curzadores);		
		
		mutadores = new PanelElegir("Mutadores");
		mutadores.setLista_string(ws.getMutadores());
		add(mutadores);
		
		funcBondad = new PanelElegir("Calculadores de Bondad");
		funcBondad.setLista_string(ws.getFuncBondad());
		add(funcBondad);
		
		pobMax = new PanelElegir("Población Máxima");
		pobMax.setLista_integer(ws.getPobMax());
		add(pobMax);
		
		estados = new PanelElegir("Población Máxima");
		estados.setLista_integer(ws.getEstados());
		add(estados);
	
		muestras = new PanelElegir("Muestras");
		muestras.setLista_integer(ws.getMuestras());
		add(muestras);

		pasos = new PanelElegir("Pasos");
		pasos.setLista_integer(ws.getPasos());
		add(pasos);
		
		JButton boton = new JButton("Buscar");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEstadisticasAvanzadas.this.buscar();
			}
		});
		add(boton);
		
		validate();
		repaint();
		
		
	}
	/**
	 * Busca los datos correspondientes a los datos seleccionados 
	 */
	protected void buscar() {
		GetAdvancedStatsWS ws = new GetAdvancedStatsWS();
		
		if (configuraciones != null && configuraciones.size() > 0) {
			List<Integer> temp = new ArrayList<Integer>();
			for (Configuracion conf : configuraciones)
				temp.add(conf.getId());
			ws.setConfiguraciones(temp);
		}
		if (problemas != null && problemas.size() > 0) {
			List<String> temp = new ArrayList<String>();
			for (Problema prob : problemas)
				temp.add(prob.getId());
			ws.setProblemas(temp);
		}
		ws.setCalculadoresBondad(funcBondad.getSeleccion_string());
		ws.setCruzadores(curzadores.getSeleccion_string());
		ws.setEstados(estados.getSeleccion_integer());
		ws.setMuestras(muestras.getSeleccion_integer());
		ws.setMutadores(mutadores.getSeleccion_string());
		ws.setPasos(pasos.getSeleccion_integer());
		ws.setPobMax(pobMax.getSeleccion_integer());
		
		
		ws.ejecutar();
		
		Stats stats = new Stats();
		
		stats.setHistParecido(ws.getHistParecido());
		
		stats.setHistReconocimiento(ws.getHistReconocimiento());
		
		stats.setMediaSoluciones(ws.getMediaSoluciones());
		
		stats.setNumProblemas(ws.getNumProblemas());
		
		new FrameStats(stats);
		
		
	}
	
	
	
	
}
