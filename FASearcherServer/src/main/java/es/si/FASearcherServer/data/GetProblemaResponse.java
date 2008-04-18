package es.si.FASearcherServer.data;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetProblemaResponse {

	private String id;
	
	private Integer pobMax;
	
	private Integer muestras;
	
	private Integer estados;
	
	// enum?
	private String tipoAutomata;
	
	// separadas por coma
	private String aceptadas;
	
	// también separadas por coma
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
	
	
	
}
