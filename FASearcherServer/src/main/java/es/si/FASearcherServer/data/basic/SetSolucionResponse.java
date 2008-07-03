package es.si.FASearcherServer.data.basic;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class SetSolucionResponse {

	/**
	 * Resultado de buscar la solución en la BD
	 */
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
		
}
