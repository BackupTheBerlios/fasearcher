package es.si.FASearcherServer.data.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import es.si.FASearcherServer.data.Problema;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetProblemasResponse {

	private List<Problema> problemas;
	
	public GetProblemasResponse() {
		problemas = new ArrayList<Problema>();
	}
	

	public List<Problema> getProblemas() {
		return problemas;
	}



	public void setProblemas(List<Problema> problemas) {
		this.problemas = problemas;
	}

}
