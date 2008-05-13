package es.si.FASearcherServer.data;

public class Problema {

	private String id;
	
	private String descripcion;
	
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
