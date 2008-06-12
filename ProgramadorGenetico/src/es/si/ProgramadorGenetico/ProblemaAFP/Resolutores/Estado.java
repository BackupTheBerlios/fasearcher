package es.si.ProgramadorGenetico.ProblemaAFP.Resolutores;

/**
 * Clase que representa el estado de la pila para el resolutor de
 * AFP que utiliza dicho método.<P>
 *
 */
public class Estado {
	
	/**
	 * El estado en el que esta el AFP
	 */
	private int estadoAF;
	
	/**
	 * Cadena que queda por reconocer
	 */
	private String cadena;
	
	/**
	 * Probabilidad de que el AFP se encuentre en esta situación
	 */
	private double prob;
	
	/**
	 * Constructor de un nuevo estado de la pila.
	 * 
	 * @param estadoAF
	 * Estado en el que se encuentra el AFP
	 * @param cadena
	 * Cadena que queda por reconocer
	 * @param prob
	 * Probabilidad de que el AFP se encuentre en esta situación
	 * 
	 */
	public Estado(int estadoAF, String cadena, double prob) {
		this.estadoAF = estadoAF;
		this.cadena = cadena;
		this.prob = prob;
	}
	
	public boolean equals(Object estado) {
		if (estado == null) return false;
		if (estado.getClass() != this.getClass()) return false;
		if (((Estado) estado).estadoAF != estadoAF) return false;
		if ((((Estado) estado).cadena == null && cadena != null) ||
				(((Estado) estado).cadena != null && cadena == null)) 
			return false;
		else 
			if ((((Estado) estado).cadena != null) && (((Estado) estado).cadena.equals(cadena))) return true;
		return false;
	}
	
	public double getProb() {
		return prob;
	}
	
	public void setProb(double prob) {
		this.prob = prob;
	}
	
	public String getCadena() {
		return cadena;
	}
	
	public int getEstadoAF() {
		return estadoAF;
	}
}
