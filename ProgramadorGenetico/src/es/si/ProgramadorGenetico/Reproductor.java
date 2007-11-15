package es.si.ProgramadorGenetico;

/**
 * Interfaz del reproductor que debe ser implementado para cada problema
 * concreto teniendo en cuenta el tipo de individuos.<p>
 * 
 * La interfaz del reproductor consta de dos métodos que deben ser implementados:<br>
 * <t>entrecruzar: coger características de una población para generar una nueva con ellas.
 * <t>mutar: variar las características de miebros de la población al azar.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public interface Reproductor {
	
	public Poblacion entrecruzar(int cant_obtener, Poblacion mejores);
	
	public Poblacion mutar(Poblacion poblacion);

}
