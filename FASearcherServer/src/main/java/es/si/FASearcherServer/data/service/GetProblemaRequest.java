package es.si.FASearcherServer.data.service;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetProblemaRequest {

	/**
	 * Identificador del problema 
	 */
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
