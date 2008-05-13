package es.si.ProgramadorGenetico.Interfaz.data;

import java.util.ArrayList;
import java.util.List;

public class Problema {

	private List<Configuracion> configuraciones;
	
	private List<String> cadenas;
	
	private String id = null;
	
	private String descripcion = null;
	
	public Problema() {
		configuraciones = new ArrayList<Configuracion>();
		
		cadenas = new ArrayList<String>();
	}

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}

	public List<String> getCadenas() {
		return cadenas;
	}

	public void setCadenas(List<String> cadenas) {
		this.cadenas = cadenas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	
}
