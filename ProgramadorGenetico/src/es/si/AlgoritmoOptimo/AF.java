package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AF {

	private List<Estado> estados;
	
	private Estado inicial;
	
	private int cont = 0;

	public AF() {
		estados = new ArrayList<Estado>();
		inicial = new Estado();
		inicial.setNum(cont);
		cont++;
		estados.add(inicial);
	}
	
	public AF(AF af) {
		this();
		Map<Estado, Estado> mapa = new HashMap<Estado, Estado>();
		mapa.put(af.getInicial(), inicial);
		if (af.getInicial().isAcepta())
			inicial.setAcepta(true);
		for (Estado otro : af.getEstados()) {
			if (!mapa.containsKey(otro))
				ponerYCopiar(mapa, otro);
			Estado temp = mapa.get(otro);
			for (int i = 0; i < 2; i++) {
				if (otro.getSiguiente()[i] != null) {
					if (!mapa.containsKey(otro.getSiguiente()[i]))
						ponerYCopiar(mapa, otro.getSiguiente()[i]);
					temp.setSiguiente(i, mapa.get(otro.getSiguiente()[i]));
				}
			}
		}
	}
	
	public boolean validate(List<String> aceptadas, List<String> rechazadas) {
		if (inicial == null || !estados.contains(inicial))
			return false;
		for (String temp : aceptadas) {
			if (!inicial.acepta(temp.toCharArray(), 0))
				return false;
		}
		for (String temp : rechazadas) {
			if (inicial.acepta(temp.toCharArray(), 0))
				return false;
		}
		return true;
	}
	
	public void ponerYCopiar(Map<Estado, Estado> mapa, Estado otro) {
		Estado temp = new Estado();
		temp.setNum(otro.getNum());
		cont++;
		if (otro.isAcepta())
			temp.setAcepta(true);
		estados.add(temp);
		mapa.put(otro, temp);
	}
	
	public void ponerSubCadenas(List<String> aceptadas, List<String> rechazadas) {
		inicial.setTempSubAceptadas(new ArrayList<String>(aceptadas));
		inicial.setTempSubRechazadas(new ArrayList<String>(rechazadas));
		inicial.darPasoSub();
		List<String> subAceptadas = new ArrayList<String>();
		List<String> subRechazadas = new ArrayList<String>();
		for (String temp : aceptadas) {
			for (int i = 0; i < temp.length(); i++) {
				if (!subAceptadas.contains(temp.substring(i)))
					subAceptadas.add(temp.substring(i));
			}
			if (!subAceptadas.contains(""))
				subAceptadas.add("");
		}
		for (String temp : rechazadas) {
			for (int i = 0; i < temp.length(); i++) {
				if (!subRechazadas.contains(temp.substring(i)))
					subRechazadas.add(temp.substring(i));
			}
			if (!subRechazadas.contains(""))
				subRechazadas.add("");
		}
		for (Estado temp: estados) {
			temp.setTempSubAceptadas(new ArrayList<String>(subAceptadas));
			temp.setTempSubRechazadas(new ArrayList<String>(subRechazadas));
			temp.calcularSubs();
		}
	}
	
	public void agregar(char[] cadena, boolean aceptar) {
		inicial.agregar(cadena, 0, this, aceptar);
	}
	
	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getInicial() {
		return inicial;
	}

	public void setInicial(Estado inicial) {
		this.inicial = inicial;
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}

	public void reemplazar(int num1, int num2) {
		Estado temp1 = null;
		Estado temp2 = null;
		for (Estado a : estados) {
			if (a.getNum() == num1)
				temp1 = a;
			if (a.getNum() == num2)
				temp2 = a;
		}
		
		if (temp2.isAcepta())
			temp1.setAcepta(true);
		/*
		if (temp1.getSiguiente()[0] == null && temp2.getSiguiente()[0] != null) {
			temp1.setSiguiente(0, temp2.getSiguiente()[0]);
		}
		if (temp1.getSiguiente()[1] == null && temp2.getSiguiente()[1] != null) {
			temp1.setSiguiente(1, temp2.getSiguiente()[1]);
		}
		*/
		for (Estado a : estados) {
			if (a.getSiguiente()[0] == temp2)
				a.setSiguiente(0, temp1);
			if (a.getSiguiente()[1] == temp2)
				a.setSiguiente(1, temp1);
		}

		
		cont--;
		estados.remove(temp2);
		
		while (eliminarSinReferencias()) {};
	}
	
	private boolean eliminarSinReferencias() {
		boolean result = false;
		
		boolean[] ests = new boolean[estados.size()];
		ests[inicial.getNum()] = true;
		for (int i = 0; i < estados.size(); i++) {
			if (estados.get(i).getSiguiente()[0] != null)
				ests[estados.indexOf(estados.get(i).getSiguiente()[0])] = true;
			if (estados.get(i).getSiguiente()[1] != null)
				ests[estados.indexOf(estados.get(i).getSiguiente()[1])] = true;
		}
		
		for (int i = 0; i < estados.size(); i++) {
			if (ests[i] == false) {
				estados.remove(i);
				cont--;
				return true;
			}
		}
		
		return result;
	}
	
	public String toString() {
		String temp = "inicial : " + inicial.getNum() + "\n";
		for (Estado a : estados) {
			temp += "" + a.getNum() + (a.isAcepta() ? "+" : "-") + " : " + " 0->" + (a.getSiguiente()[0] != null ? a.getSiguiente()[0].getNum() : "-");
			temp += " 1->" + (a.getSiguiente()[1] != null ? a.getSiguiente()[1].getNum() : "-") + " ";
			temp += "(" + a.getSubAceptadas() + "," + a.getSubRechazadas() + ") || ";
			temp += "(" + a.getTotalSubAceptadas() + "," + a.getTotalSubRechazadas() + ")\n";
		}
		return temp;
	}
	
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!(object instanceof AF))
			return false;
		AF af = (AF) object;
		if (cont != af.getCont())
			return false;
		iguales.clear();
		
		return iguales(af.getInicial(), this.getInicial());
	}
	
	private boolean iguales(Estado est1, Estado est2) {
		if (est1 == null && est2 == null)
			return true;
		else if (est1 == null || est2 == null)
			return false; 
		if (iguales.get(est1) != null) {
			if (est2 == iguales.get(est1))
				return true;
			else
				return false;
		}
		
		if (est1.isAcepta() != est2.isAcepta())
			return false;
		
		iguales.put(est1, est2);
		if (!iguales(est1.getSiguiente()[0], est2.getSiguiente()[0]))
			return false;
		if (!iguales(est1.getSiguiente()[1], est2.getSiguiente()[1]))
			return false;
		
		return true;
	}
	
	private Map<Estado, Estado> iguales = new HashMap<Estado, Estado>();

	public void reemplazar(int num, List<Estado> equiv) {
		for (Estado est : equiv) {
			reemplazar(num, est.getNum());
		}
	}

	public void clearSubs() {
		for (Estado est : estados) {
			est.setSubAceptadas(null);
			est.setSubRechazadas(null);
			est.setTotalSubAceptadas(null);
			est.setTotalSubRechazadas(null);
			est.setTempSubAceptadas(null);
			est.setTempSubRechazadas(null);
		}
	}
	
	public void recreateSubs() {
		for (Estado est : estados) {
			est.setSubAceptadas(new ArrayList<String>());
			est.setSubRechazadas(new ArrayList<String>());
			est.setTotalSubAceptadas(new ArrayList<String>());
			est.setTotalSubRechazadas(new ArrayList<String>());
			est.setTempSubAceptadas(new ArrayList<String>());
			est.setTempSubRechazadas(new ArrayList<String>());
		}
	}
}
