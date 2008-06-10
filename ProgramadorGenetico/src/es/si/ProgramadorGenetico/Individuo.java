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
	
	/**
	 * Funci�n que determina si el individuo es igual a otro
	 * @param otro
	 * Otro inidviduo con el que comparar
	 * @return
	 * Booleano que inidica si el individuo es igual a otro
	 */
	public boolean equals(Individuo otro);
	
}
