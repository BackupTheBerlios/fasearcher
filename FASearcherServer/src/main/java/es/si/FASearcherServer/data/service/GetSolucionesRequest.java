package es.si.FASearcherServer.data.service;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetSolucionesRequest {

	private String id;
	
	private Integer id_config;
	
	public Integer getId_config() {
		return id_config;
	}

	public void setId_config(Integer id_config) {
		this.id_config = id_config;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
