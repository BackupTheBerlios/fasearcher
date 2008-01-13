package es.si.ProgramadorGenetico.Interfaz;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanelTablaTransiciones extends JPanel{
	
	JTable tabla;
	
	public PanelTablaTransiciones () {
		tabla = new JTable();
	}
	
	public PanelTablaTransiciones (double[][][] transiciones, double[] probabilidadFinal, int estados) {
		String [] columnas = {"Origen", "Destino", "Valor", "Probabilidad"};
		Object[][] datos = new Object [estados*(estados+1)*2][4];
		int fila = 0;
		for (int i=0; i<transiciones.length; i++) {
			for (int j=0; j<transiciones[i].length; j++) {
				for (int k=0; k<transiciones[i][j].length; k++) {
					datos[fila][0] = "Q"+i;
					if (k==estados) 
						datos[fila][1] = "Trampa";
					else
						datos[fila][1] = "Q"+k;
					datos[fila][2] = j;
					datos[fila][3] = transiciones[i][j][k];					
					fila++;
				}
			}
		}
		tabla = new JTable(datos, columnas);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(tabla);
        //Add the scroll pane to this panel.
        add(scrollPane);
	}
}
