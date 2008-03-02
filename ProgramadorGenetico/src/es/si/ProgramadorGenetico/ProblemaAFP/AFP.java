package es.si.ProgramadorGenetico.ProblemaAFP;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.GeneradorAutomatico.AF;

public class AFP implements Individuo {

	private int estados;
	
	/**
	 * El primer ídice representa el estado origen, el segundo
	 * la entrada y el tercero el estado destino
	 */
	private double[][][] transiciones;
	
	/**
	 * Probabilidad de que el estado i-1 sea final
	 */
	private double[] probabilidadFinal;
		
	public AFP(int estados) {
		this.estados = estados;
		transiciones = new double[estados][2][estados + 1];
		probabilidadFinal = new double[estados];
	}
	
	public AFP (AF automataFinito) {
		estados = automataFinito.getEstados();
		transiciones = automataFinito.getTransiciones();
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

	public double getProbabilidad(int origen, int entrada, int destino) {
		return transiciones[origen - 1][entrada][destino];
	}
	
	public double[][][] getTransiciones() {
		return transiciones;
	}
	
	public double[] getProbabilidadesFinal() {
		return probabilidadFinal;
	}
	
	public void setTransiciones(double[][][] transiciones){
		this.transiciones = transiciones;
	}
	
	public void setProbabilidadFinal(double[] probabilidadFinal) {
		this.probabilidadFinal = probabilidadFinal;
	}
	
	public double getProbabilidadFinal(int estado) {
		return probabilidadFinal[estado - 1];
	}
	
	public int getEstados() {
		return estados;
	}
	
	public String toString() {
		String afp = new String();
		NumberFormat form = new DecimalFormat("0.0000");
		
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
