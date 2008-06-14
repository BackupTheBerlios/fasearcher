package es.si.FASearcherServer.data.stats;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetValidValuesRequest {

	private List<String> id_problemas;
	
	private Integer id_config;

	public List<String> getId_problemas() {
		return id_problemas;
	}

	public void setId_problemas(List<String> id_problemas) {
		this.id_problemas = id_problemas;
	}

	public Integer getId_config() {
		return id_config;
	}

	public void setId_config(Integer id_config) {
		this.id_config = id_config;
	}

	
}
