package es.si.ProgramadorGenetico.ProblemaAFP;
/**
 * Interface de Resolutor del algoritmo genetico
 *
 */
public interface ResolverAFP extends Runnable {
	/**
	 * Metodo que actualiza el afp
	 * @param afp
	 */
	public void setAFP(AFP afp);
	/**
	 * Actualiza la cadena
	 * @param cadena
	 */
	public void setCadena(String cadena);
	/**
	 * Devuelve cierto si se reconoce
	 * @return
	 */
	public boolean getReconoce();
	/**
	 * Devuelve la probabilidad de aceptar
	 * @return
	 */
	public double getProbabilidadAceptar();
	/**
	 * Devuelve cierto si se esta procesando
	 * @return
	 */
	public boolean getProcesando();
	/**
	 * Devuelve cierto si se termino el procesamiento
	 * @return
	 */
	public boolean getTermino();
	
}
