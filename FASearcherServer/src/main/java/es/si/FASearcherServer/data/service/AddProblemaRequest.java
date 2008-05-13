package es.si.FASearcherServer.data.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import es.si.FASearcherServer.data.AFP;
import es.si.FASearcherServer.data.Configuracion;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class AddProblemaRequest {

	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private List<Configuracion> configuraciones;
	
	private String tipoAutomata;
	
	private AFP afp;
	
	private Integer estados;
	
	private Integer pobMax;
	
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

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

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}
	
	
}
