package es.si.FASearcherServer.data;

public class Solucion {

	private String id;
	
	private Integer estados;
	
	private Double reconocimiento;
	
	private Double parecidoAF;
	
	private String tipoAutomata;
	
	private Integer pasos;
	
	private String mutador;
	
	private String funcBondad;
	
	private String cruzador;
	
	private Integer pobMax;
	
	private String muestras;
	
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
