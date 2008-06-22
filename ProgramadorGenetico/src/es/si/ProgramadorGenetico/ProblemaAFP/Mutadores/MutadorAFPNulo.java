package es.si.ProgramadorGenetico.ProblemaAFP.Mutadores;

import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.Poblacion;
/**
 * Implementa el mutador de tipo nulo 
 *
 */
public class MutadorAFPNulo implements Mutador {
	/**
	 * Atributo que indica el numero de version
	 */
	public static final double VERSION = 1.0f;

	/**
	 * La mutacion devuelve la poblacion en el mismo estado
	 */
	public Poblacion mutar(Poblacion poblacion) {
		return poblacion;
	}

}
