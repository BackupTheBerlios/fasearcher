package es.si.FASearcherServer.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class AddProblemaRequest {

	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private String tipoAutomata;
	
	private AFP afp;
	
	private Integer estados;
	
	private Integer pobMax;

	public AddProblemaRequest() {
		aceptadas = new ArrayList<String>();
		rechazadas = new ArrayList<String>();
	}
	
	public List<String> getAceptadas() {
		return aceptadas;
	}

	public void setAceptadas(List<String> aceptadas) {
		this.aceptadas = aceptadas;
	}

	public List<String> getRechazadas() {
		return rechazadas;
	}

	public void setRechazadas(List<String> rechazadas) {
		this.rechazadas = rechazadas;
	}

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

	public Integer getEstados() {
		return estados;
	}

	public void setEstados(Integer estados) {
		this.estados = estados;
	}

	public Integer getPobMax() {
		return pobMax;
	}

	public void setPobMax(Integer pobMax) {
		this.pobMax = pobMax;
	}
	
	
}
