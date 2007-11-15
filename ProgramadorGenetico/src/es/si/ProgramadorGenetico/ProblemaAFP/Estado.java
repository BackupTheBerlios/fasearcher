package es.si.ProgramadorGenetico.ProblemaAFP;

public class Estado {
	private int estadoAF;
	
	private String cadena;
	
	private double prob;
	
	public Estado(int estadoAF, String cadena, double prob) {
		this.estadoAF = estadoAF;
		this.cadena = cadena;
		this.prob = prob;
	}
	
	public boolean equals(Object estado) {
		if (estado == null) return false;
		if (estado.getClass() != this.getClass()) return false;
		if (((Estado) estado).estadoAF != estadoAF) return false;
		if ((((Estado) estado).cadena == null && cadena != null) ||
				(((Estado) estado).cadena != null && cadena == null)) 
			return false;
		else 
			if ((((Estado) estado).cadena != null) && (((Estado) estado).cadena.equals(cadena))) return true;
		return false;
	}
	
	public double getProb() {
		return prob;
	}
	
	public void setProb(double prob) {
		this.prob = prob;
	}
	
	public String getCadena() {
		return cadena;
	}
	
	public int getEstadoAF() {
		return estadoAF;
	}
}
