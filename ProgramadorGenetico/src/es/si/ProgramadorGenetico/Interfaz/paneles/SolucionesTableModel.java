package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.si.ProgramadorGenetico.Interfaz.data.Solucion;

public class SolucionesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2586674768039821010L;

	private String[] columnas = {"Estados", "Reconocimiento", "ParecidoAF", "Pasos", "PobMax", "Muestras", "Mutador", "CalculadorBondad", "Cruzador"};

	private List<FilaString> filas;
	
	public SolucionesTableModel() {
		filas = new ArrayList<FilaString>();
	}
	
	@Override
	public int getColumnCount() {
		return 9;
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
	
	public void addSolucion(Solucion config) {
		filas.add(new FilaString(config));
	}
	
	private class FilaString {
		
		String[] labels;
		
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
