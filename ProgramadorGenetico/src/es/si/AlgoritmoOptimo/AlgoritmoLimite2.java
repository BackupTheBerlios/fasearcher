package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoLimite2 {

	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private Map<Integer, List<AF>> pasados;
	
	private int k;
	
	public void setK(int k) {
		this.k = k;
	}

	public AlgoritmoLimite2() {
		aceptadas = new ArrayList<String>();
		rechazadas = new ArrayList<String>();
	}
	
	public AF ejecutar() {
		long time = System.currentTimeMillis();
		AF af = new AF();
		for (String cadena : aceptadas) {
			af.agregar(cadena.toCharArray(), true);
		}
		//for (String cadena : rechazadas) {
		//	af.agregar(cadena.toCharArray(), false);
		//}

	
		af.ponerSubCadenas(aceptadas, rechazadas);
		
		List<AF> lista = new ArrayList<AF>();
		pasados = new HashMap<Integer, List<AF>>();
		
		af = recursive(af);

		//mejor.recreateSubs();
		//mejor.ponerSubCadenas(aceptadas, rechazadas);
		System.out.println("Mejor: " + af.getCont());
		System.out.println("Automta");
		System.out.println(af);
		System.out.println("Tiempo: " + (System.currentTimeMillis() - time));
		return af;
	}
	
	
	private AF recursive(AF actual) {
		AF nuevo = new AF(actual);
		for (Estado temp1 : actual.getEstados()) {
			for (Estado temp2 : actual.getEstados()) {
				if (temp1 != temp2) {
					nuevo.reemplazar(temp1.getNum(), temp2.getNum());
					if (nuevo.validate(aceptadas, rechazadas)) {
						System.out.println("estados: " + nuevo.getCont());
						if (nuevo.getCont() <= k)
							return nuevo;
						if (!estaPasados(nuevo)) {
							AF vuelta = recursive(nuevo);
							if (vuelta != null)
								return vuelta;
						}
					}
					nuevo = new AF(actual);
				}
			}
		}
		agregarPasados(actual);
		return null;
	}
	
	
	private void agregarPasados(AF actual) {
		actual.clearSubs();
		if (pasados.get(actual.getCont()) == null)
			pasados.put(new Integer(actual.getCont()), new ArrayList<AF>());
		pasados.get(actual.getCont()).add(actual);
	}

	private boolean estaPasados(AF nuevo) {
		if (pasados.get(nuevo.getCont()) == null)
			return false;
		return pasados.get(nuevo.getCont()).contains(nuevo);
	}

	/**
	 * Comprueba que las aceptadas por el primer estado no tengan ninguna
	 * cadena en comun con las rechazadas por el segundo estado
	 * Comprueba que las aceptadas por el primer estado incluya todas aquellas
	 * aceptadas por el segundo estado
	 * @param temp1
	 * @param temp2
	 * @return
	 */
	public boolean compatibles(Estado temp1, Estado temp2) { 
		for (String a : temp1.getTotalSubAceptadas()) {
			if (temp2.getSubRechazadas().contains(a))
					return false;
		}
		
		boolean temp = !temp1.getSubRechazadas().contains("");
		for (String a : temp2.getSubAceptadas()) {
			if (!(temp1.getTotalSubAceptadas().contains(a) || (temp && a.equals(""))))
				return false;
		}
		return true;
	}
	
	public void addAceptada(String cadena) {
		aceptadas.add(cadena);
	}
	
	public void addRechazada(String cadena) {
		rechazadas.add(cadena);
	}
	
}
