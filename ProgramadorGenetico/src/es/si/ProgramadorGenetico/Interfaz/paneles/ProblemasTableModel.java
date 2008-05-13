package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProblemasTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2586674768039821010L;

	private String[] columnas = {"Id", "Descripcion"};

	private List<FilaString> filas;
	

	public ProblemasTableModel() {
		filas = new ArrayList<FilaString>();
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return filas.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return filas.get(rowIndex).labels[columnIndex];
	}
	
	public String getColumnName(int col) {
		return columnas[col];
	}
	
	public void addProblema(String id, String descripcion) {
		filas.add(new FilaString(id, descripcion));
	}
	
	private class FilaString {
		
		String[] labels;
		
		public FilaString(String id, String descripcion) {
			labels = new String[2];
			labels[0] = id;
			labels[1] = descripcion;
		}
	}
}
