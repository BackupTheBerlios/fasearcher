package es.si.ProgramadorGenetico.Interfaz.data;
/**
 * Clase que representa la configuracion de un problema
 * Estara formada por un conjunto de parametros (atributos)
 * que conforman la configuracion del algoritmo genetico
 * para resolver el problema
 */
public class Configuracion {
	/**
	 * Numero de estados pedidos al problema
	 */
	private int estados;
	/**
	 * Numero de muestras maximas para el algoritmo genetico
	 */
	private int muestras;
	/**
	 * Poblacion maxima para el algoritmo genetico
	 */
	private int pobMax;
	/**
	 * Calculador de bondad seleccionado para el algoritmo genetico
	 */
	private int calculadorBondad;
	/**
	 * Cruzador que se utilizara
	 */
	private int cruzador;
	/**
	 * Mutador que se utilizara
	 */
	private int mutador;
	/**
	 * Resolutor que se utilizara
	 */
	private int resolver;
	/**
	 * Identificador del problema
	 */
	private Integer id;
	/**
	 * Devuelve el identificador
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Actualiza el identificador del problema
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Devuelve el numero de estados
	 * @return
	 */
	public int getEstados() {
		return estados;
	}
	/**
	 * Actualiza el numero de estados
	 * @param estados
	 */
	public void setEstados(int estados) {
		this.estados = estados;
	}
	/**
	 * Devuelve el numero de muestras
	 * @return
	 */
	public int getMuestras() {
		return muestras;
	}
	/**
	 * Actualiza el numero de muestras
	 * @param muestras
	 */
	public void setMuestras(int muestras) {
		this.muestras = muestras;
	}
	/**
	 * Devuelve la poblacion maxima
	 * @return
	 */
	public int getPobMax() {
		return pobMax;
	}
	/**
	 * Actualiza la poblacion maxima
	 * @param pobMax
	 */
	public void setPobMax(int pobMax) {
		this.pobMax = pobMax;
	}
	/**
	 * Devuelve el calculador de bondad utilizado
	 * @return
	 */
	public int getCalculadorBondad() {
		return calculadorBondad;
	}
	/**
	 * Actualiza el valor del calculador de bondad
	 * @param calculadorBondad
	 */
	public void setCalculadorBondad(int calculadorBondad) {
		this.calculadorBondad = calculadorBondad;
	}
	/**
	 * Devuelve el cruzador
	 * @return
	 */
	public int getCruzador() {
		return cruzador;
	}
	/**
	 * Actualiza el cruzador al dado por parametro
	 * @param cruzador
	 */
	public void setCruzador(int cruzador) {
		this.cruzador = cruzador;
	}
	/**
	 * Devuelve el mutador
	 * @return
	 */
	public int getMutador() {
		return mutador;
	}
	/**
	 * Actualiza el valor del mutador
	 * @param mutador
	 */
	public void setMutador(int mutador) {
		this.mutador = mutador;
	}
	/**
	 * Devuelve el resolutor utilizado
	 * @return
	 */
	public int getResolver() {
		return resolver;
	}
	/**
	 * Actualiza el valor del resolutor
	 * @param resolver
	 */
	public void setResolver(int resolver) {
		this.resolver = resolver;
	}


}
