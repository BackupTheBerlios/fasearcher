package es.si.FASearcherServer.data.basic;

import javax.xml.bind.annotation.XmlType;

import es.si.FASearcherServer.data.AFP;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class SetSolucionRequest {

	/**
	 * Id del problema
	 */
	private String id;
	
	/**
	 * Valor del reconocimiento de la solución
	 */
	private String reconocimiento;
	
	/**
	 * Valor del parecido con un AF de la solución
	 */
	private String parecidoAF;
	
	/**
	 * AFP encontrado como solución al problema
	 */
	private AFP afp;
	
	/**
	 * Número de pasos utilizado para encontrar la solución
	 */
	private Integer pasos;
	
	/**
	 * Mutador utilizado
	 */
	private String mutador;
	
	/**
	 * Resolutor utilizado
	 */
	private String metodoRes;
	
	/**
	 * Función de bondad utilizada
	 */
	private String funcbondad;
	
	/**
	 * Cruzador utilizado
	 */
	private String cruzador;
	
	/**
	 * Población máxima utilizada
	 */
	private Integer pobmax;
	
	/**
	 * Muestras utilizada para encontrar la solución
	 */
	private Integer muestras;
	
	/**
	 * Particiones para generar los automatas iniciales
	 */
	private Integer particiones;
	
	/**
	 * Identificador de la configuración utilizada para encontrar la solución
	 */
	private Integer id_configuracion;

	public Integer getId_configuracion() {
		return id_configuracion;
	}

	public void setId_configuracion(Integer id_configuracion) {
		this.id_configuracion = id_configuracion;
	}

	public Integer getParticiones() {
		return particiones;
	}

	public void setParticiones(Integer particiones) {
		this.particiones = particiones;
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

	public String getMetodoRes() {
		return metodoRes;
	}

	public void setMetodoRes(String metodoRes) {
		this.metodoRes = metodoRes;
	}

	public String getFuncbondad() {
		return funcbondad;
	}

	public void setFuncbondad(String funcbondad) {
		this.funcbondad = funcbondad;
	}

	public String getCruzador() {
		return cruzador;
	}

	public void setCruzador(String cruzador) {
		this.cruzador = cruzador;
	}

	public Integer getPobmax() {
		return pobmax;
	}

	public void setPobmax(Integer pobmax) {
		this.pobmax = pobmax;
	}

	public Integer getMuestras() {
		return muestras;
	}

	public void setMuestras(Integer muestras) {
		this.muestras = muestras;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AFP getAfp() {
		return afp;
	}

	public void setAfp(AFP afp) {
		this.afp = afp;
	}

	public String getReconocimiento() {
		return reconocimiento;
	}

	public void setReconocimiento(String reconocimiento) {
		this.reconocimiento = reconocimiento;
	}

	public String getParecidoAF() {
		return parecidoAF;
	}

	public void setParecidoAF(String parecidoAF) {
		this.parecidoAF = parecidoAF;
	}
}
