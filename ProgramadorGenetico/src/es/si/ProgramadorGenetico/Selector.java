package es.si.ProgramadorGenetico;

/**
 * Interfaz del selector, que elige miembros de una población.<p>
 * 
 * Este interfaz debe ser implementado para seleccionar entre los miembros de una
 * población un grupo de mejores o al mejor de todos. Consta de un método distinto
 * para cumplir cada uno de estos cometidos.<br>
 * 
 * Se recomienda la implementación con threads de esta interfaz, dado que sus
 * características lo permiten hacer con facilidad y que es posiblemente la que representa
 * el mayor cuello de botella.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public interface Selector {

	public Poblacion seleccionar(int cantidad, Poblacion poblacion);
	
	public Individuo mejor(Poblacion poblacion);
	
}
