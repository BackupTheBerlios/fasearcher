package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.si.ProgramadorGenetico.Interfaz.data.Solucion;
/**
 * Modelo de la tabla que contiene las soluciones de
 * los problemas de la base de datos
 */
public class SolucionesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2586674768039821010L;
	/**
	 * Nombres de las columnas
	 */
	private String[] columnas = {"Estados", "Reconocimiento", "ParecidoAF", "Pasos", "PobMax", "Muestras", "Mutador", "CalculadorBondad", "Cruzador"};
	/**
	 * Filas
	 */
	private List<FilaString> filas;
	/**
	 * Constructora que inicializa las filas
	 */
	public SolucionesTableModel() {
		filas = new ArrayList<FilaString>();
	}
	/**
	 * Devuelve el numero de columnas 
	 */
	public int getColumnCount() {
		return 9;
	}

	/**
	 * Devuelve el numero de filas
	 */
	public int getRowCount() {
		return filas.size();
	}

	/**
	 * Devuelve el valor de la tabla en la fila y columna
	 * indicadas
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
	 * Añade una solucion a la tabla
	 * @param config
	 */
	public void addSolucion(Solucion config) {
		filas.add(new FilaString(config));
	}
	/**
	 * Clase privada que representa las filas de la tabla
	 * @author Pablo
	 *
	 */
	private class FilaString {
		/**
		 * Labels de la fila con los datos
		 */
		String[] labels;
		/**
		 * Constructora que toma la solucion y copia
		 * sus datos en el array de String
		 * @param sol
		 */
		public FilaString(Solucion sol) {
			labels = new String[9];
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(3);
			nf.setMinimumFractionDigits(3);
			labels[0] = ""+sol.getEstados();
			labels[1] = nf.format(sol.getReconocmiento());
			labels[2] = nf.format(sol.getParecidoAF());
			labels[3] = ""+sol.getPasos();
			labels[4] = ""+sol.getPobMax();
			labels[5] = ""+sol.getMuestras();
			labels[6] = ""+sol.getMutador();
			labels[7] = ""+sol.getCalculadorBondad();
			labels[8] = ""+sol.getCruzador();
		}
	}
}
