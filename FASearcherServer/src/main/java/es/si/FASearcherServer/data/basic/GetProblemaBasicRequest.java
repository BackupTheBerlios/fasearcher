package es.si.FASearcherServer.data.basic;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetProblemaBasicRequest {

	/**
	 * Tipo del autómata
	 */
	private String tipoAutomata;
	
	/**
	 * Tamaño del autómata (número de estados)
	 */
	private Integer tamano;

	public String getTipoAutomata() {
		return tipoAutomata;
	}

	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
	}

	public Integer getTamano() {
		return tamano;
	}

	public void setTamano(Integer tamano) {
		this.tamano = tamano;
	}
}
