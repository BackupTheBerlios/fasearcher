package es.si.ProgramadorGenetico.Interfaz.componentes;

import java.text.NumberFormat;
import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

import es.si.ProgramadorGenetico.Individuo;

public class AF implements Individuo{
	/**
	 * Numero de estados
	 */
	private int estados;
	
	/**
	 * El primer ídice representa el estado origen, el segundo
	 * la entrada y el tercero el estado destino
	 */
	private float[][][] transiciones;
	
	private float[] finales;
	
	public AF (int estados) {
		this.estados = estados;
		transiciones = new float[estados][2][estados];
		finales = new float[estados];
	}
	
	public AF (AFP automata) {
		estados = automata.getEstados();
		transiciones = new float[estados][2][estados];
		for (int i = 0; i < estados; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < estados; k++)
					transiciones[i][j][k] = automata.getTransiciones()[i][j][k+1];
		finales = automata.getProbabilidadesFinal();
	}
	
	public AF (List<Estado>listaEstados, List<Transicion> listaTransiciones) {		
		estados = listaEstados.size();
		transiciones = new float[estados][2][estados];
		for (int i=0; i<listaTransiciones.size(); i++) {
			Transicion t = listaTransiciones.get(i);
			transiciones[t.getOrigen().getIndice()][0][t.getDestino().getIndice()]=t.getProbabilidad0();
			transiciones[t.getOrigen().getIndice()][1][t.getDestino().getIndice()]=t.getProbabilidad1();			
		}
		finales = new float[estados];
		for (int i=0; i<estados; i++) {			
			finales[i] = listaEstados.get(i).getProbabilidadFinal();
		}
	}
		
	
	public void setTransicion (int origen, int valor, int destino) {
		transiciones[origen][valor][destino]=1;
	}
	
	public int getEstados () {
		return estados;
	}
	
	public float getTransicion (int origen, int valor, int destino) {
		return transiciones[origen][valor][destino];
	}
	
	public float [][][] getTransiciones () {
		return transiciones;
	}
	
	public float[] getFinales () {
		return finales;
	}
	
	public void setFinal (int estado) {
		finales[estado] = 1;
	}
	
	public String toString() {
		String af = new String();
		// form tiene formato "0.0000"
		NumberFormat form = NumberFormat.getInstance();
		form.setMinimumFractionDigits(4);
		form.setMaximumFractionDigits(4);
		for (int i = 0; i < estados;i++) {
			for (int j = 0; j < estados; j++) {
				af += ""+i +":0:"+j+" >> " + form.format(transiciones[i][0][j]);
				af += " || "+i +":1:"+j+" >> " + form.format(transiciones[i][1][j]) + "\n";
			}
		}
		return af;
	}

	public boolean esFinal (int estado) {
		if (this.finales[estado]==1)
			return true;
		else
			return false;
	}
	
	public boolean acepta (String cadena) {
		int estadoActual = 0;//estado inicial
		for (int i=0; i<cadena.length(); i++) {
			int bit = (int)(Integer.valueOf(cadena.substring(i,i+1)));
			estadoActual = buscaDestino(estadoActual,bit);	
			if (estadoActual == -1) return false;
		}
		if (esFinal(estadoActual))
			return true;		
		return false;
	}

	public int buscaDestino (int estadoOrigen, int valor) {
		for (int i=0; i<transiciones[estadoOrigen][valor].length; i++) {
			if (transiciones[estadoOrigen][valor][i]==1)
				return i;
		}
		return -1;
	}

	@Override
	public boolean equals(Individuo otro) {
		if (otro == null) return false;
		if (!(otro instanceof AF)) return false;
		if (otro == this) return true;
		// aqui se podría hacer un cast de otro a AF y comprobar las transiciones
		return false;
	}
	
	public boolean comprobarAFD () {
		for (int i=0; i<estados; i++) {			
			if (buscaDestino(i, 0) == -1)
				return false;
			if (buscaDestino(i, 1) == -1)
				return false;
		}
		return true;
	}
	
}
