package es.si.ProgramadorGenetico;

/**
 * Interfaz para la generación de la población incial.<p>
 * 
 * Esta interfaz debe ser implementada por el problema para poder generar la población
 * inicial de indviduos del problema.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public interface PoblacionInicial {

	public Poblacion generar();
	
}
