package es.si.ProgramadorGenetico.Interfaz;

public class Transicion {

	public Estado origen;
	public Estado destino;
	public double probabilidad;
	public int valor; //0 o 1
	
	
	public Transicion () {
		
	}
	
	public Transicion (Estado origen, Estado destino, double probabilidad, int valor) {
		this.origen = origen;
		this.destino = destino;
		this.probabilidad = probabilidad;
		this.valor = valor;
	}
	
	public Estado getOrigen () {
		return origen;
	}
	
	public Estado getDestino () {
		return destino;
	}
	
	public double getProbabilidad () {
		return probabilidad;
	}
	
	public int getValor () {
		return valor;
	}
	
	

}
