package es.si.FASearcherServer.data.service;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetSolucionRequest {

	/**
	 * Identificador de la soluci�n
	 */
	private String id;
		
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
