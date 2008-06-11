package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.Dimension;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import es.si.ProgramadorGenetico.Interfaz.componentes.Transicion;
/**
 * Panel que muestra la tabla con los detalles de las transiciones
 *
 */
public class PanelTablaTransiciones extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2349064315517135706L;
	/**
	 * Tabla que muestra los datos
	 */
	JTable tabla;
	/**
	 * Constructora que inicializa la tabla
	 */
	public PanelTablaTransiciones () {
		tabla = new JTable();
	}
	/**
	 * Constructora que inicializa la tabla a los valores
	 * pasados por defecto
	 * @param transiciones
	 * @param probabilidadFinal
	 * @param estados
	 */
	public PanelTablaTransiciones (float[][][] transiciones, float[] probabilidadFinal, int estados) {
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
	/**
	 * Constructora que toma un panel y coge los datos a partir de el
	 * @param dibujanteAFP
	 */
	public PanelTablaTransiciones (SubPanelAF dibujanteAFP) {				
		Transicion [][] transiciones = dibujanteAFP.getTransicionesArray();
		int estados = dibujanteAFP.getNumEstados();		
		String [] columnas = {"Origen", "Destino", "Valor", "Probabilidad"};
		String[][] datos = new String [estados*(estados+1)*2][4];
		NumberFormat form = NumberFormat.getInstance();
		form.setMinimumFractionDigits(4);
		form.setMaximumFractionDigits(4);
		int fila = 0;					
		for (int i=0; i<transiciones.length; i++) {
			for (int j=0; j<transiciones[i].length; j++) {
				Transicion t = transiciones[i][j];
				if (t.getProbabilidad0()>0.1) {
					datos[fila][0] = t.getOrigen().getLabel().getText();
					datos[fila][1] = t.getDestino().getLabel().getText();
					datos[fila][2] = "0";
					datos[fila][3] = form.format(t.getProbabilidad0());
					fila++;
				}
				if (t.getProbabilidad1()>0.1) {
					datos[fila][0] = t.getOrigen().getLabel().getText();
					datos[fila][1] = t.getDestino().getLabel().getText();
					datos[fila][2] = "1";
					datos[fila][3] = form.format(t.getProbabilidad1());
					fila++;
				}					
			}
		}
		tabla = new JTable(datos, columnas);
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		tabla.setBorder(loweredetched);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(tabla);
        //scrollPane.setLayout(new FlowLayout());
        scrollPane.setMaximumSize(new Dimension (250,250));
        //Add the scroll pane to this panel.
        add(scrollPane);        
	}
}
