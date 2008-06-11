package es.si.ProgramadorGenetico.GeneradorAutomatico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
/**
 * Clase que genera cadenas aleatorias para un automata dado
 * Genera tanto cadenas que se aceptan como cadenas que se
 * rechazan
 *
 */
public class GeneradorCadenas {
	/**
	 * Automata
	 */
	private AF af;
	/**
	 * Lista de cadenas aceptadas
	 */
	private List<String> cadenasAceptadas;
	/**
	 * Lista de cadenas rechazadas
	 */
	private List<String> cadenasRechazadas;
	/**
	 * Numero de estados
	 */
	private int estados;
	/**
	 * Array de transiciones
	 */
	private float[][][] transiciones;
	/**
	 * Generador de cadenas que pide el automata para
	 * el que tiene que generar las cadenas
	 * Se encarga de generar las cadenas
	 * @param af
	 */
	public GeneradorCadenas(AF af) {
		this.af = af;
		cadenasAceptadas = new ArrayList<String>();
		cadenasRechazadas = new ArrayList<String>();
		generaCadenas();
	}
	/**
	 * Inicializa valores y llama a otros metodos para
	 * generar las cadenas
	 */
	public void generaCadenas() {
		estados = af.getEstados();
		transiciones = af.getTransiciones();
		generaCadenasIniciales();
		generaCadenasAleatorias();

	}
	/**
	 * Se generan unas cadenas iniciales que solo son 0 o 1
	 */
	public void generaCadenasIniciales() {
		int destinoCadena0 = buscaDestino(transiciones, 0, 0);
		int destinoCadena1 = buscaDestino(transiciones, 1, 0);
		if (af.esFinal(destinoCadena0))
			cadenasAceptadas.add(new String("0"));
		else
			cadenasRechazadas.add(new String("0"));
		if (af.esFinal(destinoCadena1))
			cadenasAceptadas.add(new String("1"));
		else
			cadenasRechazadas.add(new String("1"));
	}
	/**
	 * Genera mas cadenas aleatorias
	 * Genera todas las palabras de longitud 2, y genera
	 * cadenas de longitudes aleatorias en varios bucles 
	 */
	public void generaCadenasAleatorias() {
		String cadena = new String();
		nuevaCadena(af.acepta("00"), "00");
		nuevaCadena(af.acepta("01"), "01");
		nuevaCadena(af.acepta("10"), "10");
		nuevaCadena(af.acepta("11"), "11");
		for (int i = 0; i < 6; i++) {
			cadena = "";
			int longitudCadena = 3 + (int) (Math.random() * 2);
			// 24 posibles cadenas
			for (int j = 0; j < longitudCadena; j++) {
				int bitActual = (int) (Math.random() * 2);
				cadena += bitActual;
			}
			nuevaCadena(af.acepta(cadena), cadena);
		}
		for (int i = 0; i < estados; i++) {
			cadena = "";
			int longitudCadena = 5 + (int) (Math.random() * estados - 1);
			for (int j = 0; j < longitudCadena; j++) {
				int bitActual = (int) (Math.random() * 2);
				cadena += bitActual;
			}
			nuevaCadena(af.acepta(cadena), cadena);
		}
	}
	/**
	 * Busca el destino de una transicion en un automata
	 * Parte del estado origen, y tomando el valor, busca
	 * el estado destino
	 * @param trans
	 * @param valor
	 * @param origen
	 * @return
	 */
	public int buscaDestino(float[][][] trans, int valor, int origen) {
		for (int i = 0; i < trans[origen][valor].length; i++) {
			if (trans[origen][valor][i] == 1)
				return i;
		}
		return -1;
	}
	/**
	 * Pasa a String las cadenas
	 */
	public String toString() {
		String cadenas = new String();
		cadenas = "Cadenas aceptadas:" + "\n";
		Iterator<String> it = cadenasAceptadas.iterator();
		while (it.hasNext()) {
			String cad = it.next();
			cadenas += cad + "\n";
		}
		cadenas += "Cadenas rechazadas:" + "\n";
		it = cadenasRechazadas.iterator();
		while (it.hasNext()) {
			String cad = it.next();
			cadenas += cad + "\n";
		}
		return cadenas;
	}
	/**
	 * Añade una cadena a la lista de aceptadas si el valor aceptada
	 * es true y la añade a la lista de rechazadas si el valor es false
	 * @param aceptada
	 * @param cadena
	 */
	public void nuevaCadena(boolean aceptada, String cadena) {
		if (aceptada) {
			if (!cadenasAceptadas.contains(cadena))
				cadenasAceptadas.add(cadena);
		} else if (!cadenasRechazadas.contains(cadena))
			cadenasRechazadas.add(cadena);
	}
	/**
	 * Devuelve las cadenas aceptadas
	 * @return
	 */
	public List<String> getCadenasAceptadas() {
		return cadenasAceptadas;
	}
	/**
	 * Devuelve las cadenas rechazadas
	 * @return
	 */
	public List<String> getCadenasRechazadas() {
		return cadenasRechazadas;
	}
}
