package es.si.ProgramadorGenetico.GeneradorAutomatico;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AF {
	/**
	 * Numero de estados
	 */
	private int estados;
	
	/**
	 * El primer �dice representa el estado origen, el segundo
	 * la entrada y el tercero el estado destino
	 */
	private double[][][] transiciones;
	
	private double[] finales;
	
	public AF (int estados) {
		this.estados = estados;
		transiciones = new double[estados][2][estados];
		finales = new double[estados];
	}
	
	public void setTransicion (int origen, int valor, int destino) {
		transiciones[origen][valor][destino]=1;
	}
	
	public int getEstados () {
		return estados;
	}
	
	public double getTransicion (int origen, int valor, int destino) {
		return transiciones[origen][valor][destino];
	}
	
	public double [][][] getTransiciones () {
		return transiciones;
	}
	
	public double[] getFinales () {
		return finales;
	}
	
	public void setFinal (int estado) {
		finales[estado] = 1;
	}
	
	public String toString() {
		String af = new String();
		NumberFormat form = new DecimalFormat("0.0000");
		
		for (int i = 0; i < estados;i++) {
			for (int j = 0; j < estados; j++) {
				af += ""+i +":0:"+j+" >> " + form.format(transiciones[i][0][j]);
				af += " || "+i +":1:"+j+" >> " + form.format(transiciones[i][1][j]) + "\n";

			}
		}

		return af;
	}

}
