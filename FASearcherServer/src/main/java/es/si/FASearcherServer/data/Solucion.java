package es.si.FASearcherServer.data;

/**
 * Clase que guarda la informaci�n de la soluci�n
 *
 */
public class Solucion {

	/**
	 * Identificador de la soluci�n
	 */
	private String id;
	
	/**
	 * N�mero de estados utilizado para encontrar la soluci�n
	 */
	private Integer estados;
	
	/**
	 * Valor del reconocimiento del problema para la soluci�n
	 */
	private Double reconocimiento;
	
	/**
	 * Valor del parecido a un AF de la soluci�n
	 */
	private Double parecidoAF;
	
	/**
	 * Tipo del automata soluci�n
	 */
	private String tipoAutomata;
	
	/**
	 * N�mero de pasos para encontrar la soluci�n
	 */
	private Integer pasos;
	
	/**
	 * Mutador utilizado para encontrar la soluci�n
	 */
	private String mutador;
	
	/**
	 * Funcion de bondad utilizada para encontrar la soluci�n
	 */
	private String funcBondad;
	
	/**
	 * Cruzador utilizado para encontrar la soluci�n
	 */
	private String cruzador;
	
	/**
	 * Poblaci�n m�xima utilizada para encontrar la soluci�n
	 */
	private Integer pobMax;
	
	/**
	 * N�mero de muestras utilizada para encontrar la soluci�n
	 */
	private String muestras;
	
	/**
	 * Identificador de la configuraci�n utilizada para encontrar la soluci�n
	 */
	private Integer id_configuracion;

	public Integer getId_configuracion() {
		return id_configuracion;
	}

	public void setId_configuracion(Integer id_configuracion) {
		this.id_configuracion = id_configuracion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getEstados() {
		return estados;
	}

	public void setEstados(Integer esatdos) {
		this.estados = esatdos;
	}

	public Double getReconocimiento() {
		return reconocimiento;
	}

	public void setReconocimiento(Double reconocimiento) {
		this.reconocimiento = reconocimiento;
	}

	public Double getParecidoAF() {
		return parecidoAF;
	}

	public void setParecidoAF(Double parecidoAF) {
		this.parecidoAF = parecidoAF;
	}

	public String getTipoAutomata() {
		return tipoAutomata;
	}

	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
	}

	public Integer getPasos() {
		return pasos;
	}

	public void setPasos(Integer pasos) {
		this.pasos = pasos;
	}

	public String getMutador() {
		return mutador;
	}

	public void setMutador(String mutador) {
		this.mutador = mutador;
	}

	public String getFuncBondad() {
		return funcBondad;
	}

	public void setFuncBondad(String funcBondad) {
		this.funcBondad = funcBondad;
	}

	public String getCruzador() {
		return cruzador;
	}

	public void setCruzador(String cruzador) {
		this.cruzador = cruzador;
	}

	public Integer getPobMax() {
		return pobMax;
	}

	public void setPobMax(Integer pobMax) {
		this.pobMax = pobMax;
	}

	public String getMuestras() {
		return muestras;
	}

	public void setMuestras(String muestras) {
		this.muestras = muestras;
	}
}
