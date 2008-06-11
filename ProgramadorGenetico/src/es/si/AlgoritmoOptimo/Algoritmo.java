package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Algoritmo {

	protected List<String> aceptadas;
	
	protected List<String> rechazadas;

	protected Map<Integer, List<AF>> pasados;
	
	protected int k;

	public abstract AF ejecutar();
	
	protected void agregarPasados(AF actual) {
		actual.clearSubs();
		if (pasados.get(actual.getCont()) == null)
			pasados.put(new Integer(actual.getCont()), new ArrayList<AF>());
		pasados.get(actual.getCont()).add(actual);
	}

	protected boolean estaPasados(AF nuevo) {
		if (pasados.get(nuevo.getCont()) == null)
			return false;
		return pasados.get(nuevo.getCont()).contains(nuevo);
	}
	
	public void setK(int k) {
		this.k = k;
	}
	
	public void addAceptada(String cadena) {
		aceptadas.add(cadena);
	}
	
	public void addRechazada(String cadena) {
		rechazadas.add(cadena);
	}

}
