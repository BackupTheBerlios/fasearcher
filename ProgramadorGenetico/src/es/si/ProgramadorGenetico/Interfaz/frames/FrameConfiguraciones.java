package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.Interfaz.paneles.ConfiguracionesTableModel;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelConfiguracion;
/**
 * Frame que muestra la lista de configuraciones
 * asociadas al problema actual 
 *
 */
public class FrameConfiguraciones extends JFrame {

	private static final long serialVersionUID = 5084997267681444349L;
	/**
	 * Problema al que estan asociadas las configuraciones
	 */
	private Problema problema;
	/**
	 * Panel de las configuraciones
	 */
	private PanelConfiguracion panelConfiguracion;
	/**
	 * TableModel de las configuraciones
	 */
	private ConfiguracionesTableModel model;
	
	private JTable tabla;
	/**
	 * Constructora que construye el panel a partir de los datos
	 * del problema
	 * @param problema
	 */
	public FrameConfiguraciones(Problema problema) {
		super("Modificar configuraciones");
		this.problema = problema;
		
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		setLayout(new BorderLayout());
		model = new ConfiguracionesTableModel();
		tabla = new JTable(model);
		llenarTabla();
		JScrollPane panelTabla = new JScrollPane(tabla);
		panelTabla.setSize(400, 200);
		add(panelTabla, BorderLayout.CENTER);
		
		JButton boton = new JButton("Quitar configuracion");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameConfiguraciones.this.quitarConfiguracion();
			}
		});
		panel2.add(boton);
		
		panelConfiguracion = new PanelConfiguracion();
		
		panel.add(panelConfiguracion);
		
		boton = new JButton("Añadir configuracion");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameConfiguraciones.this.agregarConfiguracion();
			}
		});
		panel2.add(boton);
		panel.add(panel2); 
		add(panel, BorderLayout.SOUTH);
		
		pack();
		setSize(500, 500);
		setVisible(true);
		
		
	}
	/**
	 * Agrega una configuracion a la lista de configuraciones
	 */
	protected void agregarConfiguracion() {
		if (panelConfiguracion.getConfiguracion() != null) {
			problema.getConfiguraciones().add(panelConfiguracion.getConfiguracion());
			model.addConfig(panelConfiguracion.getConfiguracion());
			tabla.revalidate();
			tabla.repaint();
		}
	}
	/**
	 * Elimina una configuracion
	 */
	protected void quitarConfiguracion() {
		if (panelConfiguracion.getConfiguracion() != null && tabla.getSelectedRow() != -1) {
			problema.getConfiguraciones().remove(tabla.getSelectedRow());
			model.removeConfig(tabla.getSelectedRow());
			tabla.revalidate();
			tabla.repaint();
		}
	}
	/**
	 * Llena la tabla con las configuraciones
	 */
	private void llenarTabla() {
		for (int i = 0; i < model.getColumnCount(); i++)
			tabla.getColumnModel().getColumn(i).setHeaderValue(model.getColumnName(i));
		tabla.getTableHeader().resizeAndRepaint();
		for (Configuracion config : problema.getConfiguraciones())
			model.addConfig(config);
	}
	
}
