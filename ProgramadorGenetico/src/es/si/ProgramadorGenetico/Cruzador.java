package es.si.ProgramadorGenetico;

/**
 * Inteface para los cruzadores.<p>
 * 
 * Esta interface es común a todos los cruzadores, encargados de crear un
 * numero determinado de individuos a partir de una población inicial.
 */
public interface Cruzador {

	/**
	 * Método que crea una cantidad determinada de automatas a partir
	 * de una población inicial.
	 * 
	 * @param cant_obtener
	 * Numero de automatas que se desean obtener
	 * @param mejores
	 * Poblacion inicial de los mejores de la iteracion anterior
	 * @return
	 * Poblacion de "cant_obtener" automatas
	 */
	public Poblacion entrecruzar(int cant_obtener, Poblacion mejores);

}
