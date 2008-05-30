package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.List;

public class Algoritmo {

	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	public Algoritmo() {
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
		af.setAceptadas(aceptadas);
		af.setRechazadas(rechazadas);
		af.ponerSubCadenas();
		
		List<AF> lista = new ArrayList<AF>();
		List<AF> pasados = new ArrayList<AF>();
		lista.add(af);
		
		AF mejor = null;
		
		while (lista.size() > 0) {
			AF actual = lista.get(0);//lista.size() - 1);
			lista.remove(actual);
			pasados.add(actual);
			System.out.println("Lista -> " + lista.size() + " Pasados -> " + pasados.size());
			
			AF nuevo = new AF(actual);
			for (Estado temp1 : actual.getEstados()) {
				for (Estado temp2 : actual.getEstados()) {
					if (temp1 != temp2) {
						if (compatibles(temp1, temp2)) {
							nuevo.reemplazar(temp1.getNum(), temp2.getNum());
							if (nuevo.validate()) {
								nuevo.ponerSubCadenas();
								if (mejor == null || nuevo.getCont() < mejor.getCont()) {
									mejor = nuevo;
									System.out.println("Automata");
									System.out.println(nuevo);
								}
								if (!lista.contains(nuevo) && !pasados.contains(nuevo)) {
									lista.add(nuevo);
								}
							}
							
							nuevo = new AF(actual);
						}

					}
				}
			}
			
		}
		
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
		for (String a : temp1.getSubAceptadas()) {
			for (String b : temp2.getSubRechazadas()) {
				if (a.equals(b))
					return false;
			}
		}
		boolean temp = !temp1.getSubRechazadas().contains("");
		for (String a : temp2.getSubAceptadas()) {
			if (!temp2.getSubAceptadas().contains(a) || (temp && a.equals("")))
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
