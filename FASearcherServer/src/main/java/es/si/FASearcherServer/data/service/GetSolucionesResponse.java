package es.si.FASearcherServer.data.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import es.si.FASearcherServer.data.Configuracion;
import es.si.FASearcherServer.data.Solucion;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetSolucionesResponse {

	/**
	 * Lista de las soluciones
	 */
	private List<Solucion> soluciones;

	/**
	 * Lista de las configuraciones
	 */
	private List<Configuracion> configuraciones;
	
	public GetSolucionesResponse() {
		soluciones = new ArrayList<Solucion>();
	}

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}

	public List<Solucion> getSoluciones() {
		return soluciones;
	}

	public void setSoluciones(List<Solucion> soluciones) {
		this.soluciones = soluciones;
	}
	
}
