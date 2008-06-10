package es.si.AlgoritmoOptimo;

import java.util.ArrayList;
import java.util.List;

public class Estado {

	private Estado[] siguiente;
	
	private boolean acepta;
	
	private List<String> subAceptadas;
	
	private List<String> subRechazadas;
	
	private List<String> totalSubAceptadas;
	
	private List<String> totalSubRechazadas;
	
	private List<String> tempSubAceptadas;
	
	private List<String> tempSubRechazadas;
	
	private int num;
	
	public Estado() {
		siguiente = new Estado[2];
		acepta = false;
		subAceptadas = new ArrayList<String>();
		subRechazadas = new ArrayList<String>();
		tempSubAceptadas = new ArrayList<String>();
		tempSubRechazadas = new ArrayList<String>();
	}
	
	public void agregar(char[] cadena, int pos, AF af, boolean aceptar) {
		if (cadena.length <= pos) {
			acepta = aceptar;
			return;
		}
		int simbolo = (int) (cadena[pos] - '0');
		Estado siguiente;
		if (this.siguiente[simbolo] != null)
			siguiente = this.siguiente[simbolo];
		else {
			siguiente = new Estado();
			siguiente.setNum(af.getCont());
			af.setCont(af.getCont() + 1);
			af.getEstados().add(siguiente);
			this.siguiente[simbolo] = siguiente;
		}
		siguiente.agregar(cadena, ++pos, af, aceptar);
	}
	
	public boolean acepta(char[] cadena, int pos) {
		if (cadena.length == pos)
			return acepta;
		else {
			int simbolo = (int)(cadena[pos] - '0');
			if (siguiente[simbolo] == null)
				return false;
			return siguiente[simbolo].acepta(cadena, pos+1);
		}
	}

	public Estado[] getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Estado[] siguiente) {
		this.siguiente = siguiente;
	}
	
	public void setSiguiente(int val, Estado siguiente) {
		this.siguiente[val] = siguiente;
	}
	
	public boolean isAcepta() {
		return acepta;
	}

	public void setAcepta(boolean acepta) {
		this.acepta = acepta;
	}

	public List<String> getSubAceptadas() {
		return subAceptadas;
	}

	public void setSubAceptadas(List<String> subAceptadas) {
		this.subAceptadas = subAceptadas;
	}

	public List<String> getSubRechazadas() {
		return subRechazadas;
	}

	public void setSubRechazadas(List<String> subRechazadas) {
		this.subRechazadas = subRechazadas;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public void darPasoSub() {
		for (int i = 0; i < 2; i++) {
			List<String> aceptadas = new ArrayList<String>();
			List<String> rechazadas = new ArrayList<String>();
			List<String> temp1 = new ArrayList<String>();
			List<String> temp2 = new ArrayList<String>();
			for (String cadena : tempSubAceptadas) {
				if (cadena.length() > 0 && cadena.startsWith("" + i)) {
					aceptadas.add(cadena.substring(1));
					temp1.add(cadena);
					if (cadena.length() == 1)
						temp1.add("");
				}
			}
			for (String cadena : tempSubRechazadas) {
				if (cadena.length() > 0 && cadena.startsWith("" + i)) {
					rechazadas.add(cadena.substring(1));
					temp2.add(cadena);
					if (cadena.length() == 1)
						temp2.add("");
				}
			}
			for (String cadena : temp1) {
				tempSubAceptadas.remove(cadena);
				subAceptadas.add(cadena);
			}
			for (String cadena : temp2) {
				tempSubRechazadas.remove(cadena);
				subRechazadas.add(cadena);
			}
			if (siguiente[i] != null && (aceptadas.size() > 0 || rechazadas.size() > 0)) {
				siguiente[i].getTempSubAceptadas().addAll(aceptadas);
				siguiente[i].getTempSubRechazadas().addAll(rechazadas);
				siguiente[i].darPasoSub();
			}
		}
	}

	public void setTempSubAceptadas(List<String> tempSubAceptadas) {
		this.tempSubAceptadas = tempSubAceptadas;
	}

	public void setTempSubRechazadas(List<String> tempSubRechazadas) {
		this.tempSubRechazadas = tempSubRechazadas;
	}

	public List<String> getTempSubAceptadas() {
		return tempSubAceptadas;
	}

	public List<String> getTempSubRechazadas() {
		return tempSubRechazadas;
	}

	public void calcularSubs() {
		totalSubAceptadas = new ArrayList<String>();
		totalSubRechazadas = new ArrayList<String>();
		for (String temp : tempSubAceptadas) {
			if (this.acepta(temp.toCharArray(), 0))
				totalSubAceptadas.add(temp);
		}
		tempSubAceptadas.clear();
		for (String temp : tempSubRechazadas) {
			if (!this.acepta(temp.toCharArray(), 0))
				totalSubRechazadas.add(temp);
		}
		tempSubRechazadas.clear();		
	}

	public List<String> getTotalSubAceptadas() {
		return totalSubAceptadas;
	}

	public void setTotalSubAceptadas(List<String> totalSubAceptadas) {
		this.totalSubAceptadas = totalSubAceptadas;
	}

	public List<String> getTotalSubRechazadas() {
		return totalSubRechazadas;
	}

	public void setTotalSubRechazadas(List<String> totalSubRechazadas) {
		this.totalSubRechazadas = totalSubRechazadas;
	}
}
