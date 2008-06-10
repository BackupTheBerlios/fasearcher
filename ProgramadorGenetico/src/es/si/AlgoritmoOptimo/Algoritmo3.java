package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Algoritmo3 {

	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private List<Estado> principales;
	
	private Map<Estado, List<Estado>> grupos;
	
	private Map<Integer, List<AF>> pasados;
	
	public Algoritmo3() {
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
		
	while (lista.size() > 0) {
			AF actual = lista.get(lista.size() - 1);
			lista.remove(actual);
			actual.ponerSubCadenas(aceptadas, rechazadas);
			System.out.println("Lista -> " + lista.size() + " Tamaño -> " + actual.getCont() + " Mejor -> " + (mejor != null ? mejor.getCont() : "-"));
			
			AF nuevo = new AF(actual);
			
			principales = new ArrayList<Estado>();
			principales.add(actual.getEstados().get(0));
			grupos = new HashMap<Estado, List<Estado>>();
			grupos.put(actual.getEstados().get(0), new ArrayList<Estado>());
			grupos.get(actual.getEstados().get(0)).add(actual.getEstados().get(0));
			for (int i = 1; i < actual.getEstados().size(); i++) {
				Estado temp1 = actual.getEstados().get(i);
				boolean yaEsta = false;
				for (Estado temp2 : principales) {
					if (compatibles(temp2, temp1)) {
						grupos.get(temp2).add(temp1);
						yaEsta = true;
					}
				}
				if (!yaEsta) {
					principales.add(temp1);
					grupos.put(temp1, new ArrayList<Estado>());
				}
			}
			
			AF otro = new AF();
			int[] siguiente1 = new int[principales.size()];
			int[] siguiente0 = new int[principales.size()];
			for (int j = 0; j < principales.size(); j++) {
				Estado temp2 = principales.get(j);
				Estado temp;
				if (j!=0)
					temp = new Estado();
				else
					temp = otro.getInicial();
				boolean esAlgunoFinal = false;
				siguiente0[j] = -1;
				siguiente1[j] = -1;
				for (Estado temp3 : grupos.get(temp2)) {
					if (temp3.isAcepta()) esAlgunoFinal = true;
					if (siguiente0[j] == -1 && temp3.getSiguiente()[0] != null) {
						siguiente0[j] = encontrarGrupo(temp3.getSiguiente()[0]);	
					}
					if (siguiente1[j] == -1 && temp3.getSiguiente()[1] != null) {
						siguiente1[j] = encontrarGrupo(temp3.getSiguiente()[1]);
					}
				}
				if (esAlgunoFinal)
					temp.setAcepta(true);
				temp.setAcepta(temp2.isAcepta());
				temp.setNum(j);
				if (j != 0) {
					otro.getEstados().add(temp);
					otro.setCont(otro.getCont() + 1);
				}
			}
			
			for (int j = 0; j < otro.getEstados().size(); j++) {
				Estado temp = otro.getEstados().get(j);
				if (siguiente0[j] != -1)
					temp.setSiguiente(0, otro.getEstados().get(siguiente0[j]));
				if (siguiente1[j] != -1)
					temp.setSiguiente(1, otro.getEstados().get(siguiente1[j]));
			}
			lista.add(otro);
			System.out.println(otro);
				/*
				for (int j = i + 1; j < actual.getEstados().size(); j++) {
					Estado temp2 = actual.getEstados().get(j);
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
				*/
			
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
	
	private int encontrarGrupo(Estado estado) {
		for (int i = 0; i < principales.size(); i++) {
			for (Estado e : grupos.get(principales.get(i))) {
				if (e == estado)
					return i;
			}
		}
		return -1;
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
		
		boolean temp = false;//!temp1.getSubRechazadas().contains("");
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
