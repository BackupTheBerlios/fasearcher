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
	
	public boolean equals(Individuo otro);
	
}
