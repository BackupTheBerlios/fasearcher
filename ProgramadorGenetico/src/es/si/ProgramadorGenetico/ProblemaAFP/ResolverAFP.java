package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;

/**
 * Clase que dado un automata y una cadena determina la probabilidad de que
 * esta sea reconocida.<p>
 * 
 * Esta clase toma como par�metros en su constructor el automata finito
 * probabilista y la cadena a reconocer. Tiene un m�todo run que se puede
 * ejecutar en un thread independiente si se quiere y que guarda en la
 * variable <b>probabilidadAceptar</b> la probabilidad de que la cadena
 * sea aceptada una vez que termina con el procesamiento.<br>
 * Adem�s tiene un flag <b>procesando</b> para indicar si esta trabajando y un
 * flag <b>termino</b> para indicar cuando se termin� el procesamiento.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public class ResolverAFP implements Runnable {

	private AFP af;
	
	private String cadenaInicial;
	
	private boolean reconoce;
	
	private ArrayList<Estado> pila;
	
	private double probabilidadAceptar;
	
	private boolean procesando;
	
	private boolean termino;
	
	public ResolverAFP(AFP af, String cadena) {
		this.af = af;
		this.cadenaInicial = new String(cadena);
		this.reconoce = false;
		pila = new ArrayList<Estado>();
		procesando = false;
		termino = false;
	}
	
	@Override
	public void run() {
		if (this.termino) return;
		this.procesando = true;
		pila.add(new Estado(1, cadenaInicial, 1.0f));
		Estado procesando;
		probabilidadAceptar = 0;
		while (!pila.isEmpty()) {
			procesando = pila.get(pila.size() - 1);
			pila.remove(pila.size() - 1);

			String cadena = procesando.getCadena();

			int simbolo = obtenerSimbolo(cadena);
			cadena = actualizarCadena(cadena);

			for (int i = 1; i <= af.getEstados(); i++) {
				double prob = procesando.getProb()*af.getProbabilidad(procesando.getEstadoAF(), simbolo, i);
				if (cadena == null) {
					probabilidadAceptar += af.getProbabilidadFinal(i)*prob;
				} else
					pila.add(new Estado(i, cadena, prob));
			}

		}
		this.procesando = false;
		termino = true;
	}
	
	private int obtenerSimbolo(String cadena) {
		if (cadena == null) return -1;
		int valor = (int) (cadena.charAt(0) - '0');
		if (valor == 0 || valor == 1)
			return valor;
		return -1;
	}
	
	private String actualizarCadena(String cadena) {
		if (cadena.length() == 1) return null;
		return cadena.substring(1);
	}
	
	public boolean getReconoce() {
		return reconoce;
	}
	
	public double getProbabilidadAceptar() {
		return probabilidadAceptar;
	}
	
	public boolean getProcesando() {
		return procesando;
	}
	
	public boolean getTermino() {
		return termino;
	}

}
