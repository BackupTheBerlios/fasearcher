package es.si.ProgramadorGenetico.ProblemaAFP;


import es.si.ProgramadorGenetico.Individuo;

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
	
	@Override
	public boolean equals(Individuo otro) {
		if (otro == null || otro.getClass() != this.getClass())
			return false;
		AFP otroafp = (AFP) otro;
		if (otro == this) return true;
		if (otroafp.estados != estados) return false;
		for (int i = 0; i < estados; i++) {
			if (otroafp.probabilidadFinal[i] != probabilidadFinal[i])
				return false;
		}
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j <= estados; j++) {
				if (otroafp.transiciones[i][0][j] != transiciones[i][0][j])
					return false;
				if (otroafp.transiciones[i][1][j] != transiciones[i][1][j])
					return false;
			}
		}
		return false;
	}

	public double getProbabilidad(int origen, int entrada, int destino) {
		return transiciones[origen - 1][entrada][destino];
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
}
