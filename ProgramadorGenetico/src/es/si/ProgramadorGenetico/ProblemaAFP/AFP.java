package es.si.ProgramadorGenetico.ProblemaAFP;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Interfaz.componentes.AF;

public class AFP implements Individuo {

	private int estados;
	
	/**
	 * El primer ídice representa el estado origen, el segundo
	 * la entrada y el tercero el estado destino
	 */
	private float[][][] transiciones;
	
	/**
	 * Probabilidad de que el estado i-1 sea final
	 */
	private float[] probabilidadFinal;
		
	public AFP(int estados) {
		this.estados = estados;
		transiciones = new float[estados][2][estados + 1];
		probabilidadFinal = new float[estados];
	}
	
	public AFP (AF automataFinito) {
		estados = automataFinito.getEstados();
		transiciones = new float[estados][2][estados + 1];
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j < 2; j++) {
				float temp = 0;
				for (int k = 1; k < estados+1; k++) {
					transiciones[i][j][k] = automataFinito.getTransiciones()[i][j][k-1];
					temp += transiciones[i][j][k];
				}
				transiciones[i][j][0] = 1 - temp;
			}
		}
		probabilidadFinal = automataFinito.getFinales();
	}
	
	@Override
	public boolean equals(Individuo otro) {
		if (otro == null || otro.getClass() != this.getClass())
			return false;
		AFP otroafp = (AFP) otro;
		if (otro == this) return true;
		if (otroafp.estados != estados) return false;
		for (int i = 0; i < estados; i++) {
			if (Math.abs(otroafp.probabilidadFinal[i] - probabilidadFinal[i]) > 0.001)
				return false;
		}
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j <= estados; j++) {
				if (Math.abs(otroafp.transiciones[i][0][j] - transiciones[i][0][j]) > 0.001)
					return false;
				if (Math.abs(otroafp.transiciones[i][1][j] - transiciones[i][1][j]) > 0.001)
					return false;
			}
		}
		return true;
	}

	public float getProbabilidad(int origen, int entrada, int destino) {
		return transiciones[origen - 1][entrada][destino];
	}
	
	public float[][][] getTransiciones() {
		return transiciones;
	}
	
	public float[] getProbabilidadesFinal() {
		return probabilidadFinal;
	}
	
	public void setTransiciones(float[][][] transiciones){
		this.transiciones = transiciones;
	}
	
	public void setProbabilidadFinal(float[] probabilidadFinal) {
		this.probabilidadFinal = probabilidadFinal;
	}
	
	public float getProbabilidadFinal(int estado) {
		return probabilidadFinal[estado - 1];
	}
	
	public int getEstados() {
		return estados;
	}
	
	public String toString() {
		String afp = new String();
		// form tiene formato "0.0000"
		NumberFormat form = NumberFormat.getInstance();
		form.setMinimumFractionDigits(4);
		form.setMaximumFractionDigits(4);
		for (int i = 0; i < estados;i++) {
			afp += ""+i +":0:- >> " + form.format(transiciones[i][0][0]);
			afp += " || "+i +":1:- >> " + form.format(transiciones[i][1][0]) + "\n";

			for (int j = 1; j < estados+1; j++) {
				afp += ""+i +":0:" + (j-1) +" >> " + form.format(transiciones[i][0][j]);
				afp += " || "+i +":1:" + (j-1) +" >> " + form.format(transiciones[i][1][j]) + "\n";
			}
		}
		for (int i = 0; i < estados; i++) {
			afp += "" + i + " >> " + this.probabilidadFinal[i] + " || ";
		}
		return afp;
	}
}
