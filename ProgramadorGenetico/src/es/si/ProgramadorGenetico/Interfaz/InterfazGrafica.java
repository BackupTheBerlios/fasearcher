package es.si.ProgramadorGenetico.Interfaz;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

/**
 * Interface de la interfaz grafica
 */
public interface InterfazGrafica {
	/**
	 * Metodo a implementar que hace las actualizaciones necesarias cuando
	 * se termina la resolucion de un problema
	 * @param correcto
	 * @param mejores
	 */
	public void terminoResolver(boolean correcto, List<AFP> mejores);
	/**
	 * Metodo a implementar que hace las actualizaciones necesarias cuando
	 * se termina la resolucion de muchos problemas
	 * @param correcto
	 * @param mejor
	 */
	public void terminoResolverMuchos(boolean correcto, AFP mejor);
	
}
