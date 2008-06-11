package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Algorimto "voraz" recursivo con límite (busca un automata de mínimo k estados)
 *
 */
public class AlgoritmoLimite2 extends Algoritmo {
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
}
