package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Algoritmo con etiquetados de los sub-arboles.
 *
 */
public class Algoritmo1 extends Algoritmo {

	public Algoritmo1() {
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
		lista.add(af);
		
		AF mejor = null;
		
		while (lista.size() > 0 && (mejor == null || mejor.getCont() > k)) {
			AF actual = lista.get(lista.size() - 1);
			lista.remove(actual);
			actual.ponerSubCadenas(aceptadas, rechazadas);
			System.out.println("Lista -> " + lista.size() + " Tamaño -> " + actual.getCont() + " Mejor -> " + (mejor != null ? mejor.getCont() : "-"));
			
			AF nuevo = new AF(actual);
			for (Estado temp1 : actual.getEstados()) {
			//for (int i = actual.getEstados().size() - 1; i >= 0; i--) {
				//Estado temp1 = actual.getEstados().get(i);
				for (Estado temp2 : actual.getEstados()) {
				//for (int j = actual.getEstados().size() - 1; j >= 0; j--) {
					//Estado temp2 = actual.getEstados().get(j);
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
		mejor.recreateSubs();
		mejor.ponerSubCadenas(aceptadas, rechazadas);
		System.out.println("Mejor: " + mejor.getCont());
		System.out.println("Automta");
		System.out.println(mejor);
		System.out.println("Tiempo: " + (System.currentTimeMillis() - time));
		return af;
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
}
