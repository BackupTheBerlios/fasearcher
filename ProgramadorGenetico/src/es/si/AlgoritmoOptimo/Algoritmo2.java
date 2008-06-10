package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Algoritmo2 {

	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private Map<Integer, List<AF>> pasados;
	
	public Algoritmo2() {
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
		List<AF> mejores = new ArrayList<AF>();
		pasados = new HashMap<Integer, List<AF>>();
		lista.add(af);
		
		AF mejor = null;
		List<Estado> equiv = new ArrayList<Estado>();

		while (lista.size() > 0) {   
			AF actual = lista.get(lista.size() - 1);
			lista.remove(actual);
			actual.ponerSubCadenas(aceptadas, rechazadas);

			System.out.println("Lista -> " + lista.size() + " Tamaño -> " + actual.getCont());
			
			AF nuevo = new AF(actual);
			for (Estado temp1 : actual.getEstados()) {
			//for (int i = 0; i < actual.getEstados().size() - 1; i++) {
				//Estado temp1 = actual.getEstados().get(i);
				equiv.clear();
				for (Estado temp2 : actual.getEstados()) {
				//for (int j = i + 1; j < actual.getEstados().size(); j++) {
					//Estado temp2 = actual.getEstados().get(j);
					if (temp1 != temp2 && compatibles2(temp1, temp2)) {
						equiv.add(temp2);
					}
				}
				if (equiv.size() > 0) {
					
					nuevo.reemplazar(temp1.getNum(), equiv);
					System.out.println("Equiv -> " + equiv.size() + " Valid-> " + nuevo.validate(aceptadas, rechazadas));
					if (nuevo.validate(aceptadas, rechazadas)) {
						if (mejores.size() == 0 || nuevo.getCont() < mejor.getCont()) {
							mejor = nuevo;
							mejores.clear();
							mejores.add(nuevo);
							System.out.println("Automata");
							System.out.println(nuevo);
						}
						else if (nuevo.getCont() == mejor.getCont())
							mejores.add(nuevo);
						if (!lista.contains(nuevo) && !estaPasados(nuevo)) {
							lista.add(nuevo);
						}
					}
					nuevo = new AF(actual);
				}
			}
			agregarPasados(actual);
		}
		lista.clear();
		for (AF mejor2 : mejores) {
			mejor2.recreateSubs();
		}
		lista.addAll(mejores);
		while (lista.size() > 0) {
			AF actual = lista.get(lista.size() - 1);
			lista.remove(actual);
			actual.ponerSubCadenas(aceptadas, rechazadas);
			System.out.println("Lista -> " + lista.size() + " Tamaño -> " + actual.getCont() + " Mejor -> " + mejor.getCont());
			
			AF nuevo = new AF(actual);
			for (Estado temp1 : actual.getEstados()) {
				for (Estado temp2 : actual.getEstados()) {
					if (temp1 != temp2 && compatibles(temp1, temp2)) {
						nuevo.reemplazar(temp1.getNum(), temp2.getNum());
						if (nuevo.validate(aceptadas, rechazadas)) {
							if (mejor == null || nuevo.getCont() < mejor.getCont()) {
								mejor = nuevo;
								System.out.println("Automata");
								System.out.println(nuevo);
							}
							if (!lista.contains(nuevo) && !estaPasados(nuevo)) {
								lista.add(nuevo);
							}
						}
						nuevo = new AF(actual);
					}
				}
			}
			agregarPasados(actual);
		}
		
		System.out.println("Tiempo: " + (System.currentTimeMillis() - time));
		return af;
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
	
	public boolean compatibles2(Estado temp1, Estado temp2) { 
		boolean temp = !temp1.getSubRechazadas().contains("");
		for (String a : temp2.getSubAceptadas()) {
			if (!(temp1.getTotalSubAceptadas().contains(a) || (temp && a.equals(""))))
				return false;
		}		
		for (String a : temp2.getSubRechazadas()) {
			if (!temp1.getTotalSubRechazadas().contains(a))
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
