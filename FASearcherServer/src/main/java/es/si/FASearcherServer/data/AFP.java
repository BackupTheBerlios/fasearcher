package es.si.FASearcherServer.data;

import java.util.List;

public class AFP {
	
	public String getTipo() {
		return "AFP";
	}
	
	private Integer estados;
	
	// separadas por ;
	private String probFinales;
	
	// valor estadoorigen:entrada:valores separados por ; para el estado 0 al n+1
	private List<String> transiciones;

	public Integer getEstados() {
		return estados;
	}

	public void setEstados(Integer estados) {
		this.estados = estados;
	}

	public String getProbFinales() {
		return probFinales;
	}

	public void setProbFinales(String probFinales) {
		this.probFinales = probFinales;
	}

	public List<String> getTransiciones() {
		return transiciones;
	}

	public void setTransiciones(List<String> transiciones) {
		this.transiciones = transiciones;
	}
	

}
