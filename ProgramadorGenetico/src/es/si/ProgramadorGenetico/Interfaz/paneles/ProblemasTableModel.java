package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.si.ProgramadorGenetico.Interfaz.data.Problema;
/**
 * Modelo de la tabla en la que se muestran
 * los problemas de la base de datos
 *
 */
public class ProblemasTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2586674768039821010L;
	/**
	 * Descripcion de las columnas de la tabla
	 */
	private String[] columnas = {"Id", "Descripcion", "No. Soluciones"};
	/**
	 * Lista que almacena las filas de la tabla
	 */
	private List<FilaString> filas;
	
	/**
	 * Constructora que inicializa las filas
	 */
	public ProblemasTableModel() {
		filas = new ArrayList<FilaString>();
	}
	
	/**
	 * Devuelve el numero de columnas
	 */
	public int getColumnCount() {
		return 3;
	}

	/**
	 * Devuelve el numero de filas
	 */
	public int getRowCount() {
		return filas.size();
	}

	/**
	 * Devuelve el valor de la tabla en la fila y columna
	 * pasadas por parametro
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return filas.get(rowIndex).labels[columnIndex];
	}
	/**
	 * Devuelve el nombre de la columna escogida
	 */
	public String getColumnName(int col) {
		return columnas[col];
	}
	/**
	 * Añade un problema a la tabla mediante su id,
	 * descripcion y soluciones
	 * @param id
	 * @param descripcion
	 * @param soluciones
	 */
	public void addProblema(String id, String descripcion, Integer soluciones) {
		filas.add(new FilaString(id, descripcion, soluciones));
	}
	/**
	 * Añade un problema a la tabla mediante un objeto
	 * de la clase Problema
	 * @param problema
	 */
	public void addProblema(Problema problema) {
		filas.add(new FilaString(problema.getId(), problema.getDescripcion(), null));
	}
	/**
	 * Clase que representa las filas de la tabla
	 *
	 */
	private class FilaString {
		/**
		 * Labels de la fila
		 */
		String[] labels;
		/**
		 * Constructora que crea 3 labels y las da valor segun los parametros
		 * pasados
		 * @param id
		 * @param descripcion
		 * @param soluciones
		 */
		public FilaString(String id, String descripcion, Integer soluciones) {
			labels = new String[3];
			labels[0] = id;
			labels[1] = descripcion;
			labels[2] = (soluciones != null ? "" + soluciones : "-");
		}
	}
	/**
	 * Elimina un problema de la tabla
	 * @param selectedRow
	 */
	public void removeProblema(int selectedRow) {
		filas.remove(selectedRow);
	}
	/**
	 * Borra la tabla
	 */
	public void clear() {
		filas = new ArrayList<FilaString>();
	}
}
