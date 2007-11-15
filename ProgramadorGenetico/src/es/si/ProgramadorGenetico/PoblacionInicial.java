package es.si.ProgramadorGenetico;

/**
 * Interfaz para la generaci�n de la poblaci�n incial.<p>
 * 
 * Esta interfaz debe ser implementada por el problema para poder generar la poblaci�n
 * inicial de indviduos del problema.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public interface PoblacionInicial {

	public Poblacion generar();
	
}
