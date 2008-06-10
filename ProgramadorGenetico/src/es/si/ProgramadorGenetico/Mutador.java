package es.si.ProgramadorGenetico;

/**
 * Interface com�n para los mutadores.<p>
 * 
 * Los mutadores son encargados de modificar los elementos de una poblacion
 * con mutaciones para obtener una nueva poblaci�n del mismo tama�o.
 *
 */
public interface Mutador {

	/**
	 * Funci�n que devuelve una nueva poblaci�n de igual tama�o
	 * a la dada pero con los individuos mutados
	 * 
	 * @param poblacion
	 * Conjunto inicial de individuos sin mutar
	 * @return
	 * Conjunto de individuos mutados
	 */
	public Poblacion mutar(Poblacion poblacion);

}
