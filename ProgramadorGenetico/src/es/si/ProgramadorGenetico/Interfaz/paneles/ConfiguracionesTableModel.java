package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;
/**
 * Modelo para la tabla de configuraciones 
 *
 */
public class ConfiguracionesTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2586674768039821010L;
	/**
	 * Columnas de la tabla
	 */
	private String[] columnas = {"Estados", "Muestras", "PobMax", "Cruzador", "Mutador", "CalculadorBondad", "Resolver"};
	/**
	 * Lista de las filas de la tabla
	 */
	private List<FilaString> filas;
	/**
	 * Constructora que inicializa las filas
	 */
	public ConfiguracionesTableModel() {
		filas = new ArrayList<FilaString>();
	}
	/**
	 * Devuelve el numero de columnas
	 */
	public int getColumnCount() {
		return 7;
	}
	/**
	 * Devuelve el numero de filas
	 */
	public int getRowCount() {
		return filas.size();
	}
	/**
	 * Devuelve el valor de la fila y columna indicadas
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return filas.get(rowIndex).labels[columnIndex];
	}
	/**
	 * Devuelve el nombre de la columna indicada
	 */
	public String getColumnName(int col) {
		return columnas[col];
	}
	/**
	 * Añade la configuracion indicada
	 * @param config
	 */
	public void addConfig(Configuracion config) {
		filas.add(new FilaString(config));
	}
	/**
	 * Elimina la configuracion indicada
	 * @param index
	 */
	public void removeConfig(int index) {
		filas.remove(index);
	}
	/**
	 * Clase privada que guarda una fila de la
	 * tabla con todos los datos
	 *
	 */
	private class FilaString {
		/**
		 * Lista de labels que muestran los datos
		 */
		String[] labels;
		/**
		 * Constructora que a partir de la configuracion
		 * utiliza los datos para guardarlos en la lista de labels
		 * @param config
		 */
		public FilaString(Configuracion config) {
			labels = new String[7];
			labels[0] = ""+config.getEstados();
			labels[1] = ""+config.getMuestras();
			labels[2] = ""+config.getPobMax();
			labels[3] = ""+config.getCruzador();
			labels[4] = ""+config.getMutador();
			labels[5] = ""+config.getCalculadorBondad();
			labels[6] = ""+config.getResolver();
		}
	}
	/**
	 * Reinicia la lista de las filas
	 */
	public void clear() {
		filas = new ArrayList<FilaString>();
	}
}
