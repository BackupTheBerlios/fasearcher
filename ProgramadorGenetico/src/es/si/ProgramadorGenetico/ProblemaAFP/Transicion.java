package es.si.ProgramadorGenetico.ProblemaAFP;
/**
 * Clase que representa una transicion para el algoritmo
 * genetico
 *
 */
public class Transicion {
	/**
	 * Estado origen
	 */
	int estado1;
	/**
	 * Estado destino
	 */
	int estado2;
	/**
	 * Constructora que pide los estados
	 * origen y destino
	 * @param estado1
	 * @param estado2
	 */
	public Transicion(int estado1, int estado2) {
		this.estado1 = estado1;
		
		this.estado2 = estado2;
	}
}
