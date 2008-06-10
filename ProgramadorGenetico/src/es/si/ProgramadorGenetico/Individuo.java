package es.si.ProgramadorGenetico;

/**
 * Interfaz que define los métodos mínimos que deben poder aplicarse a un idividuo.<p>
 * 
 * Este interfaz define los métodos mínimos que deben poder aplicarse a cada individuo de la
 * población de un problema concreto. Puede almacenar distintas características así como poseer
 * otros métodos en su implementación específica.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public interface Individuo {
	
	/**
	 * Función que determina si el individuo es igual a otro
	 * @param otro
	 * Otro inidviduo con el que comparar
	 * @return
	 * Booleano que inidica si el individuo es igual a otro
	 */
	public boolean equals(Individuo otro);
	
}
