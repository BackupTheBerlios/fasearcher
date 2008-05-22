package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.si.ProgramadorGenetico.Interfaz.data.Problema;

public class ProblemasTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2586674768039821010L;

	private String[] columnas = {"Id", "Descripcion", "No. Soluciones"};

	private List<FilaString> filas;
	

	public ProblemasTableModel() {
		filas = new ArrayList<FilaString>();
	}
	
	@Override
	public int getColumnCount() {
		return 3;
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
	
	public void addProblema(String id, String descripcion, Integer soluciones) {
		filas.add(new FilaString(id, descripcion, soluciones));
	}
	
	public void addProblema(Problema problema) {
		filas.add(new FilaString(problema.getId(), problema.getDescripcion(), null));
	}
	
	private class FilaString {
		
		String[] labels;
		
		public FilaString(String id, String descripcion, Integer soluciones) {
			labels = new String[3];
			labels[0] = id;
			labels[1] = descripcion;
			labels[2] = (soluciones != null ? "" + soluciones : "-");
		}
	}

	public void removeProblema(int selectedRow) {
		filas.remove(selectedRow);
	}

	public void clear() {
		filas = new ArrayList<FilaString>();
	}
}
