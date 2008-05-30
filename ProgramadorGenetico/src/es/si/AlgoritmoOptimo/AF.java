package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AF {

	private List<Estado> estados;
	
	private Estado inicial;
	
	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private int cont = 0;

	public AF() {
		estados = new ArrayList<Estado>();
		inicial = new Estado();
		inicial.setNum(cont);
		cont++;
		estados.add(inicial);
		aceptadas = new ArrayList<String>();
		rechazadas = new ArrayList<String>();
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
		aceptadas.addAll(af.getAceptadas());
		rechazadas.addAll(af.getRechazadas());
	}
	
	public boolean validate() {
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
	
	public void ponerSubCadenas() {
		inicial.setTempSubAceptadas(new ArrayList<String>(aceptadas));
		inicial.setTempSubRechazadas(new ArrayList<String>(rechazadas));
		inicial.darPasoSub();
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

	public List<String> getAceptadas() {
		return aceptadas;
	}

	public void setAceptadas(List<String> aceptadas) {
		this.aceptadas = aceptadas;
	}

	public List<String> getRechazadas() {
		return rechazadas;
	}

	public void setRechazadas(List<String> rechazadas) {
		this.rechazadas = rechazadas;
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}

	public void reemplazar(int num1, int num2) {
		Estado temp1 = new Estado();
		Estado temp2 = new Estado();
		for (Estado a : estados) {
			if (a.getNum() == num1)
				temp1 = a;
			if (a.getNum() == num2)
				temp2 = a;
		}
		
		if (temp2.isAcepta())
			temp1.setAcepta(true);
		
		for (Estado a : estados) {
			if (a.getSiguiente()[0] == temp2)
				a.setSiguiente(0, temp1);
			if (a.getSiguiente()[1] == temp2)
				a.setSiguiente(1, temp1);
		}
		cont--;
		estados.remove(temp2);
	}
	
	public String toString() {
		String temp = "inicial : " + inicial.getNum() + "\n";
		for (Estado a : estados) {
			temp += "" + a.getNum() + (a.isAcepta() ? "+" : "-") + " : " + " 0->" + (a.getSiguiente()[0] != null ? a.getSiguiente()[0].getNum() : "-");
			temp += " 1->" + (a.getSiguiente()[1] != null ? a.getSiguiente()[1].getNum() : "-") + "\n";
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
		if (iguales.get(est1) != null)
			if (est2 == iguales.get(est1))
				return true;
			else
				return false;
		if (est1 == null || est2 == null)
			return false;
		if (est1.isAcepta() != est2.isAcepta())
			return false;
		if ((est1.getSiguiente()[0] == null && est2.getSiguiente()[0] != null)
			|| (est1.getSiguiente()[0] != null && est2.getSiguiente()[0] == null))
				return false;
		if ((est1.getSiguiente()[1] == null && est2.getSiguiente()[1] != null)
			|| (est1.getSiguiente()[1] != null && est2.getSiguiente()[1] == null))
				return false;
		
		iguales.put(est1, est2);
		if (!iguales(est1.getSiguiente()[0], est2.getSiguiente()[0]))
			return false;
		if (!iguales(est1.getSiguiente()[1], est2.getSiguiente()[1]))
			return false;
		
		return true;
	}
	
	private Map<Estado, Estado> iguales = new HashMap<Estado, Estado>();
}
