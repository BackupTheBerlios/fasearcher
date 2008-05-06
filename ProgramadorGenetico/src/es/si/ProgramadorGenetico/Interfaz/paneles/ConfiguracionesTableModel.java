package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;

public class ConfiguracionesTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2586674768039821010L;

	private String[] columnas = {"Estados", "Muestras", "PobMax", "Cruzador", "Mutador", "CalculadorBondad", "Resolver"};

	private List<FilaString> filas;
	

	public ConfiguracionesTableModel() {
		filas = new ArrayList<FilaString>();
	}
	
	@Override
	public int getColumnCount() {
		return 7;
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
	
	public void addConfig(Configuracion config) {
		filas.add(new FilaString(config));
	}
	
	private class FilaString {
		
		String[] labels;
		
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
}
