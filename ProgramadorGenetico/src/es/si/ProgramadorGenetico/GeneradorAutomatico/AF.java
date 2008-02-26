package es.si.ProgramadorGenetico.GeneradorAutomatico;

public class AF {
	/**
	 * Numero de estados
	 */
	private int estados;
	
	/**
	 * El primer ídice representa el estado origen, el segundo
	 * la entrada y el tercero el estado destino
	 */
	private double[][][] transiciones;
	
	public AF (int estados) {
		this.estados = estados;
		transiciones = new double[estados][2][estados + 1];
	}
	
	public void setTransicion (int origen, int valor, int destino) {
		transiciones[origen][valor][destino]=1;
	}
}
