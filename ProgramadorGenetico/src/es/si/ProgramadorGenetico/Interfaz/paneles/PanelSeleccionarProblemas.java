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
/**
 * Panel en el que se pueden seleccionar los problemas a tratar
 * con las estadisticas
 *
 */
public class PanelSeleccionarProblemas extends JPanel {

	private static final long serialVersionUID = -2750855309074355489L;
	/**
	 * Tabla de problemas
	 */
	private JTable tabla_problemas;
	/**
	 * Modelo de la tabla de los problemas
	 */
	private ProblemasTableModel model_problemas;
	/**
	 * Tabla de problemas seleccionados
	 */
	private JTable tabla_seleccionados;
	/**
	 * Modelo de la tabla de problemas seleccionados
	 */
	private ProblemasTableModel model_seleccionados;
	/**
	 * Lista de problemas
	 */
	private List<Problema> problemas;
	/**
	 * Lista de problemas seleccionados
	 */
	private List<Problema> seleccionados = new ArrayList<Problema>();
	/**
	 * Frame de estadisticas avanzadas
	 */
	private FrameEstadisticasAvanzadas frame;
	/**
	 * Constructora que inicializa los frames, paneles y tablas
	 * @param frameEstadisticasAvanzadas
	 */
	public PanelSeleccionarProblemas(FrameEstadisticasAvanzadas frameEstadisticasAvanzadas) {
		
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
				PanelSeleccionarProblemas.this.agergarProblema();
			}
		});
		temp.add(boton);
		
		boton = new JButton("Usar seleccionados");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelSeleccionarProblemas.this.usarSeleccionados();
			}
		});
		temp.add(boton);

		boton = new JButton("Usar todos");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelSeleccionarProblemas.this.usarTodos();
			}
		});
		temp.add(boton);

		add(temp, BorderLayout.SOUTH);
	}
	/**
	 * Agrega el problema seleccionado a la lista de problemas
	 * seleccionados
	 */
	protected void agergarProblema() {
		if (tabla_problemas.getSelectedRow() != -1) {
			seleccionados.add(problemas.get(tabla_problemas.getSelectedRow()));
		}
		llenarTablaSeleccionado();
		revalidate();
		repaint();
	}
	/**
	 * Indica que se deben usar los problemas seleccionados
	 * para mostrar las estadisticas
	 */
	protected void usarSeleccionados() {
		frame.problemasElegidos(seleccionados);
	}
	/**
	 * Indica que se usaran todos los problemas para mostrar
	 * las estadisticas
	 */
	protected void usarTodos() {
		frame.problemasElegidos(null);
	}
	/**
	 * Llena la tabla de problemas con los datos de
	 * la base de datos
	 */
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
	/**
	 * Llena la tabla de los problemas seleccionados
	 */
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
