package es.si.FASearcherServer.data.service;

import javax.xml.bind.annotation.XmlType;

import es.si.FASearcherServer.data.AFP;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetSolucionResponse {

	/**
	 * AFP solución del problema
	 */
	private AFP afp;

	/**
	 * Tipo del automata de la solución
	 */
	private String tipoAutomata;
	
	public String getTipoAutomata() {
		return tipoAutomata;
	}

	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
	}

	public AFP getAfp() {
		return afp;
	}

	public void setAfp(AFP afp) {
		this.afp = afp;
	}
}
