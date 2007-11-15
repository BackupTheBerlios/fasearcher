package es.si.ProgramadorGenetico;

/**
 * Interfaz que define los m�todos m�nimos que deben poder aplicarse a un idividuo.<p>
 * 
 * Este interfaz define los m�todos m�nimos que deben poder aplicarse a cada individuo de la
 * poblaci�n de un problema concreto. Puede almacenar distintas caracter�sticas as� como poseer
 * otros m�todos en su implementaci�n espec�fica.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public interface Individuo {
	
	public boolean equals(Individuo otro);
	
}
