package es.si.ProgramadorGenetico;

/**
 * Interfaz del reproductor que debe ser implementado para cada problema
 * concreto teniendo en cuenta el tipo de individuos.<p>
 * 
 * La interfaz del reproductor consta de dos m�todos que deben ser implementados:<br>
 * <t>entrecruzar: coger caracter�sticas de una poblaci�n para generar una nueva con ellas.
 * <t>mutar: variar las caracter�sticas de miebros de la poblaci�n al azar.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public interface Reproductor {
	
	public Poblacion entrecruzar(int cant_obtener, Poblacion mejores);
	
	public Poblacion mutar(Poblacion poblacion);

}
