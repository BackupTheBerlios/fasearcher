package es.si.ProgramadorGenetico.GeneradorAutomatico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;

public class GeneradorCadenas {

	private AF af;
	private ArrayList<String> cadenasAceptadas;
	private ArrayList<String> cadenasRechazadas;
	private int estados;
	private double[][][] transiciones;
	
	public GeneradorCadenas (AF af) {
		this.af = af;
		cadenasAceptadas = new ArrayList<String>();
		cadenasRechazadas = new ArrayList<String>();
		generaCadenas();
	}
	
	public void generaCadenas () {
		estados = af.getEstados();
		transiciones = af.getTransiciones();
		generaCadenasIniciales();
		
	}
	
	public void generaCadenasIniciales () {
		int destinoCadena0 = buscaDestino(transiciones,0,0);
		int destinoCadena1 = buscaDestino(transiciones,1,0);
		if (af.esFinal(destinoCadena0))
			cadenasAceptadas.add(new String("0"));
		else
			cadenasRechazadas.add(new String("0"));
		if (af.esFinal(destinoCadena1))
			cadenasAceptadas.add(new String("1"));
		else
			cadenasRechazadas.add(new String("1"));
		
		String cadenaActual="";
		for (int i=0; i<estados-1; i++) {
			if (transiciones[i][0][i+1]==1) {
				cadenaActual+='0';				
			}
			else if (transiciones[i][1][i+1]==1) {
				cadenaActual+='1';
				
			}
			String clonCadena = new String(cadenaActual);
			if (af.esFinal(i+1)) {
				if (!cadenasAceptadas.contains(clonCadena))
					cadenasAceptadas.add(clonCadena); 
			}				
			else if (!cadenasRechazadas.contains(clonCadena)) 
				cadenasRechazadas.add(clonCadena);			
		}
	}
	public int buscaDestino(double [][][] trans,int origen, int valor) {
		for (int i=0; i<trans[origen][valor].length; i++) {
			if (trans[origen][valor][i]==1)
				return i;
		}
		return -1;
	}
	
	public String toString() {
		String cadenas= new String();
		cadenas="Cadenas aceptadas:"+"\n";
		Iterator<String> it = cadenasAceptadas.iterator();
		while (it.hasNext()) {
			String cad = it.next();
			cadenas+=cad+"\n";
		}
		cadenas+="Cadenas rechazadas:"+"\n";
		it = cadenasRechazadas.iterator();
		while (it.hasNext()) {
			String cad = it.next();
			cadenas+=cad+"\n";
		}
		return cadenas;
	}
}
