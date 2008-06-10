package es.si.ProgramadorGenetico;

/**
 * Interface común para los mutadores.<p>
 * 
 * Los mutadores son encargados de modificar los elementos de una poblacion
 * con mutaciones para obtener una nueva población del mismo tamaño.
 *
 */
public interface Mutador {

	/**
	 * Función que devuelve una nueva población de igual tamaño
	 * a la dada pero con los individuos mutados
	 * 
	 * @param poblacion
	 * Conjunto inicial de individuos sin mutar
	 * @return
	 * Conjunto de individuos mutados
	 */
	public Poblacion mutar(Poblacion poblacion);

}
