package es.si.ProgramadorGenetico.Interfaz.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un problema
 * Un problema viene dado por sus configuraciones, 
 * sus cadenas de entrada, un id y una descripcion
 */
public class Problema {
	
	/**
	 * Lista de configuraciones asignadas a ese problema
	 */
	private List<Configuracion> configuraciones;
	/**
	 * Lista de cadenas asignadas a ese problema
	 */
	private List<String> cadenas;
	/**
	 * Identificador del problema
	 */
	private String id = null;
	/**
	 * Descripcion del problema
	 */
	private String descripcion = null;
	/**
	 * Constructora principal que inicializa las listas
	 */
	public Problema() {
		configuraciones = new ArrayList<Configuracion>();
		
		cadenas = new ArrayList<String>();
	}
	/**
	 * Devuelve la lista de configuraciones
	 * @return
	 */
	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}
	/**
	 * Actualiza la lista de configuraciones
	 * @param configuraciones
	 */
	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}
	/**
	 * Devuelve las cadenas de entrada
	 * @return
	 */
	public List<String> getCadenas() {
		return cadenas;
	}
	/**
	 * Actualiza las cadenas de entrada
	 * @param cadenas
	 */
	public void setCadenas(List<String> cadenas) {
		this.cadenas = cadenas;
	}
	/**
	 * Devuelve el id del problema
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * Actualiza el valor del id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Devuelve la descripcion del problema
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Actualiza la descripcion del problema
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	
}
