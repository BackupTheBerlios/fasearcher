package es.si.ProgramadorGenetico.GeneradorAutomatico;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import es.si.ProgramadorGenetico.Interfaz.*;

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
	private double[][][] transiciones;
	
	private double[] finales;
	
	public AF (int estados) {
		this.estados = estados;
		transiciones = new double[estados][2][estados];
		finales = new double[estados];
	}
	
	public AF (List<Estado> listaEstados, List<Transicion> listaTransiciones) {		
		estados = listaEstados.size();
		transiciones = new double[estados][2][estados];
		for (int i=0; i<listaTransiciones.size(); i++) {
			Transicion t = listaTransiciones.get(i);
			transiciones[t.getOrigen().getIndice()][0][t.getDestino().getIndice()]=t.getProbabilidad0();
			transiciones[t.getOrigen().getIndice()][1][t.getDestino().getIndice()]=t.getProbabilidad1();			
		}
		finales = new double[estados];
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
	
	public double getTransicion (int origen, int valor, int destino) {
		return transiciones[origen][valor][destino];
	}
	
	public double [][][] getTransiciones () {
		return transiciones;
	}
	
	public double[] getFinales () {
		return finales;
	}
	
	public void setFinal (int estado) {
		finales[estado] = 1;
	}
	/*
	public void setFinalBool (boolean estadoFinal, int estado) {
		if (estadoFinal)
			finales[estado] = 1;
		else
			finales[estado] = 0;
	}
	*/
	
	public String toString() {
		String af = new String();
		NumberFormat form = new DecimalFormat("0.0000");
		
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
		}
		if (esFinal(estadoActual))
			return true;		
		return false;
	}

	public int buscaDestino (int estadoOrigen, int valor) {
		int longit = transiciones[estadoOrigen][valor].length;
		for (int i=0; i<transiciones[estadoOrigen][valor].length; i++) {
			if (transiciones[estadoOrigen][valor][i]==1)
				return i;
		}
		return -1;
	}

	@Override
	public boolean equals(Individuo otro) {
		// TODO Auto-generated method stub
		return false;
	}
}
