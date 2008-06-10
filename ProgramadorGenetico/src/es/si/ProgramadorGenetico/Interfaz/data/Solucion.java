package es.si.ProgramadorGenetico.Interfaz.data;

/**
 * Clase que representa una solucion
 * Tiene todos los datos de un problema, y los parametros
 * del algoritmo genetico con los que fue resuelto
 *
 */
public class Solucion {
	/**
	 * Identificador que identifica a la solucion en la base de datos
	 */
	private Integer id;
	/**
	 * Numero de estados de esa solucion
	 */
	private int estados;
	/**
	 * Porcentaje de acierto en el reconocimiento de palabras
	 * de esta solucion
	 */
	private double reconocmiento;
	/**
	 * Porcentaje de parecido de la solucion con un AF
	 */
	private double parecidoAF;
	/**
	 * Tipo de automata obtenido
	 */
	private String tipoAutomata;
	/**
	 * Numero de pasos que fueron necesarios en el algoritmo
	 * para obtener esta solucion
	 */
	private int pasos;
	/**
	 * Mutador utilizado
	 */
	private String mutador;
	/**
	 * Calculador de bondad utilizado
	 */
	private String calculadorBondad;
	/**
	 * Cruzador utilizado
	 */
	private String cruzador;
	/**
	 * Resolutor utilizado
	 */
	private String metodoRes;
	/**
	 * Poblacion maxima
	 */
	private String pobMax;
	/**
	 * Muestras del problema
	 */
	private String muestras;
	/**
	 * Numero de iteraciones o particiones 
	 * en las que ha sido resuelto
	 */
	private String particiones;
	/**
	 * Devuelve el id del problema
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Actualiza el id
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
	 * Devuelve el porcentaje de reconocimiento
	 * @return
	 */
	public double getReconocmiento() {
		return reconocmiento;
	}
	/**
	 * Actualiza el reconocimiento
	 * @param reconocmiento
	 */
	public void setReconocmiento(double reconocmiento) {
		this.reconocmiento = reconocmiento;
	}
	/**
	 * Devuelve el porcentaje de parecido con un AF
	 * @return
	 */
	public double getParecidoAF() {
		return parecidoAF;
	}
	/**
	 * Actualiza el porcentaje de parecido con un AF
	 * @param parecidoAF
	 */
	public void setParecidoAF(double parecidoAF) {
		this.parecidoAF = parecidoAF;
	}
	/**
	 * Devuelve el tipo de automata
	 * @return
	 */
	public String getTipoAutomata() {
		return tipoAutomata;
	}
	/**
	 * Actualiza el tipo de automata
	 * @param tipoAutomata
	 */
	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
	}
	/**
	 * Devuelve el numero de pasos en los que
	 * ha sido resuelto el problema
	 * @return
	 */
	public int getPasos() {
		return pasos;
	}
	/**
	 * Actualiza el numero de pasos
	 * @param pasos
	 */
	public void setPasos(int pasos) {
		this.pasos = pasos;
	}
	/**
	 * Devuelve el mutador
	 * @return
	 */
	public String getMutador() {
		return mutador;
	}
	/**
	 * Actualiza el mutador
	 * @param mutador
	 */
	public void setMutador(String mutador) {
		this.mutador = mutador;
	}
	/**
	 * Devuelve el calculador de bondad
	 * @return
	 */
	public String getCalculadorBondad() {
		return calculadorBondad;
	}
	/**
	 * Actualiza el calculador de bondad
	 * @param calculadorBondad
	 */
	public void setCalculadorBondad(String calculadorBondad) {
		this.calculadorBondad = calculadorBondad;
	}
	/**
	 * Devuelve el cruzador
	 * @return
	 */
	public String getCruzador() {
		return cruzador;
	}
	/**
	 * Actualiza el cruzador
	 * @param cruzador
	 */
	public void setCruzador(String cruzador) {
		this.cruzador = cruzador;
	}
	/**
	 * Devuelve el metodo de resolucion
	 * @return
	 */
	public String getMetodoRes() {
		return metodoRes;
	}
	/**
	 * Actualiza el metodo de resolucion
	 * @param metodoRes
	 */
	public void setMetodoRes(String metodoRes) {
		this.metodoRes = metodoRes;
	}
	/**
	 * Devuelve la poblacion maxima
	 * @return
	 */
	public String getPobMax() {
		return pobMax;
	}
	/**
	 * Actualiza la poblacion maxima
	 * @param pobMax
	 */
	public void setPobMax(String pobMax) {
		this.pobMax = pobMax;
	}
	/**
	 * Devuelve el numero de muestras
	 * @return
	 */
	public String getMuestras() {
		return muestras;
	}
	/**
	 * Actualiza el numero de muestras
	 * @param muestras
	 */
	public void setMuestras(String muestras) {
		this.muestras = muestras;
	}
	/**
	 * Devuelve el numero de particiones
	 * @return
	 */
	public String getParticiones() {
		return particiones;
	}
	/**
	 * Actualiza el numero de particiones
	 * @param particiones
	 */
	public void setParticiones(String particiones) {
		this.particiones = particiones;
	}
}
