package es.si.FASearcherServer.data;

/**
 * Clase que guarda la informaci�n del problema.<p>
 *
 */
public class Problema {

	/**
	 * Identificador del problema
	 */
	private String id;
	
	/**
	 * Descripci�n del problema
	 */
	private String descripcion;
	
	/**
	 * N�mero de soluciones que tiene el problema en la base de datos
	 */
	private Integer soluciones;

	public String getId() {
		return id;
	}

	public Integer getSoluciones() {
		return soluciones;
	}

	public void setSoluciones(Integer soluciones) {
		this.soluciones = soluciones;
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
