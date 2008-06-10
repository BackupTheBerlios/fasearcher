package es.si.ProgramadorGenetico.Interfaz.data;

import java.util.List;
/**
 * Clase que guarda las estadisticas de las
 * soluciones de la base de datos
 *
 */
public class Stats {
	/**
	 * Numero de problemas de la base de datos
	 */
	private Integer numProblemas;
	/**
	 * Media de soluciones por problema
	 */
	private Float mediaSoluciones;
	/**
	 * Lista donde se almacenan todos los porcentajes de acierto
	 * al reconocer las cadenas de entrada
	 */
	private List<Double> histReconocimiento;
	/**
	 * Lista donde se almacenan todos los porcentajes de parecido
	 * con un AF
	 */
	private List<Double> histParecido;
	/**
	 * Devuelve el numero de problemas
	 * @return
	 */
	public Integer getNumProblemas() {
		return numProblemas;
	}
	/**
	 * Actualiza el numero de problemas
	 * @param numProblemas
	 */
	public void setNumProblemas(Integer numProblemas) {
		this.numProblemas = numProblemas;
	}
	/**
	 * Devuelve la media de soluciones
	 * @return
	 */
	public Float getMediaSoluciones() {
		return mediaSoluciones;
	}
	/**
	 * Actualiza la media de soluciones
	 * @param mediaSoluciones
	 */
	public void setMediaSoluciones(Float mediaSoluciones) {
		this.mediaSoluciones = mediaSoluciones;
	}
	/**
	 * Devuelve la lista de porcentajes de acierto
	 * en reconocimiento de cadenas
	 * @return
	 */
	public List<Double> getHistReconocimiento() {
		return histReconocimiento;
	}
	/**
	 * Actualiza la lista de porcentajes de acierto
	 * en reconocimiento de cadenas
	 * @param histReconocimiento
	 */
	public void setHistReconocimiento(List<Double> histReconocimiento) {
		this.histReconocimiento = histReconocimiento;
	}
	/**
	 * Devuelve la lista de porcentajes de parecido
	 * con un AF
	 * @return
	 */
	public List<Double> getHistParecido() {
		return histParecido;
	}
	/**
	 * Actualiza la lista de porcentajes de parecido
	 * con un AF
	 * @param histParecido
	 */
	public void setHistParecido(List<Double> histParecido) {
		this.histParecido = histParecido;
	}
}
