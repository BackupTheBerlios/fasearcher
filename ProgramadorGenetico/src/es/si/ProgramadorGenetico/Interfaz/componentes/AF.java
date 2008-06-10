package es.si.ProgramadorGenetico.Interfaz.componentes;

import java.text.NumberFormat;
import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

import es.si.ProgramadorGenetico.Individuo;

/**
 * Clase que representa a un AF
 * Implementa a la interface Individuo ya que se utilizara en el algoritmo genetico
 */
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
	/**
	 * Array que guarda la probabilidad de ser final de todos los estados
	 */
	private float[] finales;
	/**
	 * Constructora que crea un AF con el numero de estados indicado
	 * en el parametro
	 * @param estados
	 */
	public AF (int estados) {
		this.estados = estados;
		transiciones = new float[estados][2][estados];
		finales = new float[estados];
	}
	/**
	 * Constructora que toma un AFP que puede convertirse en un AF
	 * @param automata
	 */
	public AF (AFP automata) {
		estados = automata.getEstados();
		transiciones = new float[estados][2][estados];
		for (int i = 0; i < estados; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < estados; k++)
					transiciones[i][j][k] = automata.getTransiciones()[i][j][k+1];
		finales = automata.getProbabilidadesFinal();
	}
	/**
	 * Constructora que a partir de una lista de estados y otra de transiciones
	 * construye un AF
	 * @param listaEstados
	 * @param listaTransiciones
	 */
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
		
	/**
	 * Actualiza las caracteristicas de una transicion a las dadas
	 * por los parametros
	 * @param origen
	 * @param valor
	 * @param destino
	 */
	public void setTransicion (int origen, int valor, int destino) {
		transiciones[origen][valor][destino]=1;
	}
	
	/**
	 * Devuelve el numero de estados
	 * @return
	 */
	public int getEstados () {
		return estados;
	}
	/**
	 * Devuelve la probabilidad de la transicion indicada por los parametros 
	 * @param origen
	 * @param valor
	 * @param destino
	 * @return
	 */
	public float getTransicion (int origen, int valor, int destino) {
		return transiciones[origen][valor][destino];
	}
	/**
	 * Devuelve la lista de las transiciones
	 * @return
	 */
	public float [][][] getTransiciones () {
		return transiciones;
	}
	/**
	 * Devuelve la lista de las probabilidades de estados finales
	 * @return
	 */
	public float[] getFinales () {
		return finales;
	}
	/**
	 * Actualiza la probabilidad del estado indicado por el parametro a 1 
	 * @param estado
	 */
	public void setFinal (int estado) {
		finales[estado] = 1;
	}
	/**
	 * Pasa a String los datos del automata
	 */
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

	/**
	 * Devuelve si el estado indicado por parametro es final
	 * @param estado
	 * @return
	 */
	public boolean esFinal (int estado) {
		if (this.finales[estado]==1)
			return true;
		else
			return false;
	}
	/**
	 * Devuelve si la cadena pasada por parametro es aceptada
	 * @param cadena
	 * @return
	 */
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

	/**
	 * Devuelve el destino del valor dado por parametro desde el origen
	 * pasado por parametro
	 * @param estadoOrigen
	 * @param valor
	 * @return
	 */
	public int buscaDestino (int estadoOrigen, int valor) {
		for (int i=0; i<transiciones[estadoOrigen][valor].length; i++) {
			if (transiciones[estadoOrigen][valor][i]==1)
				return i;
		}
		return -1;
	}

	/**
	 * Metodo equals que compara el AF con uno pasado por parametro  
	 */
	public boolean equals(Individuo otro) {
		if (otro == null) return false;
		if (!(otro instanceof AF)) return false;
		if (otro == this) return true;
		// aqui se podría hacer un cast de otro a AF y comprobar las transiciones
		return false;
	}
	/**
	 * Comprueba que el AFD es correcto
	 * @return
	 */
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
