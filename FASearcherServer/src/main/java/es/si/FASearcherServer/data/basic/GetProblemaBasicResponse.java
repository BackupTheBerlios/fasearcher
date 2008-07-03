package es.si.FASearcherServer.data.basic;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetProblemaBasicResponse {

	/**
	 * Identificador del problema
	 */
	private String id;
	
	/**
	 * Población máxima
	 */
	private Integer pobMax;
	
	/**
	 * Número de muestras
	 */
	private Integer muestras;
	
	/**
	 * Número de estados
	 */
	private Integer estados;
	
	/**
	 * Calculador de bondad que se debe utilizar
	 */
	private Integer calculadorBondad;
	
	/**
	 * Cruzador que se debe utilizar
	 */
	private Integer cruzador;
	
	/**
	 * Mutador que se debe utilizar
	 */
	private Integer mutador;
	
	/**
	 * Resolutor que se debe utilizar
	 */
	private Integer resolver;
	
	/**
	 * Identificador de la configuración que se esta utilizando
	 */
	private Integer id_configuracion;
	
	/**
	 * Tipo de autómata
	 */
	private String tipoAutomata;
	
	/**
	 * Cadenas acpetadas por el problema, separadas por coma
	 */
	private String aceptadas;
	
	/**
	 * Cadenas rechazadas por el problema, separadas por coma
	 */
	private String rechazadas;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPobMax() {
		return pobMax;
	}

	public void setPobMax(Integer pobMax) {
		this.pobMax = pobMax;
	}

	public Integer getEstados() {
		return estados;
	}

	public void setEstados(Integer estados) {
		this.estados = estados;
	}

	public String getTipoAutomata() {
		return tipoAutomata;
	}

	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
	}

	public String getAceptadas() {
		return aceptadas;
	}

	public void setAceptadas(String aceptadas) {
		this.aceptadas = aceptadas;
	}

	public String getRechazadas() {
		return rechazadas;
	}

	public void setRechazadas(String rechazadas) {
		this.rechazadas = rechazadas;
	}

	public Integer getMuestras() {
		return muestras;
	}

	public void setMuestras(Integer muestras) {
		this.muestras = muestras;
	}

	public Integer getCalculadorBondad() {
		return calculadorBondad;
	}

	public void setCalculadorBondad(Integer calculadorBondad) {
		this.calculadorBondad = calculadorBondad;
	}

	public Integer getCruzador() {
		return cruzador;
	}

	public void setCruzador(Integer cruzador) {
		this.cruzador = cruzador;
	}

	public Integer getMutador() {
		return mutador;
	}

	public void setMutador(Integer mutador) {
		this.mutador = mutador;
	}

	public Integer getResolver() {
		return resolver;
	}

	public void setResolver(Integer resolver) {
		this.resolver = resolver;
	}

	public Integer getId_configuracion() {
		return id_configuracion;
	}

	public void setId_configuracion(Integer id_configuracion) {
		this.id_configuracion = id_configuracion;
	}
}
