package es.si.ProgramadorGenetico.Interfaz.data;

import java.util.ArrayList;
import java.util.List;

public class Problema {

	private List<Configuracion> configuraciones;
	
	private List<String> cadenas;
	
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

	
	
}
