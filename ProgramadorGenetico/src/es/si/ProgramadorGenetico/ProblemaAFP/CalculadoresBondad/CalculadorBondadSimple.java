package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

/**
 * Este calculador de bondad simple solo realiza el promedio entre
 * las probabilidades de ser aceptadas o rechazadas de cada palabra.
 */
public class CalculadorBondadSimple extends CalculadorBondad {

	public static final double VERSION = 1.0f;
	/**
	 * Contador
	 */
	private int cont;
	/**
	 * Bondad temporal
	 */
	private double bondadTemp;
	/**
	 * Constructora del calculador
	 * @param afp
	 * @param cadenasAceptadas
	 * @param cadenasRechazadas
	 */
	public CalculadorBondadSimple(AFP afp,
			List<String> cadenasAceptadas,
			List<String> cadenasRechazadas) {
		super(afp, cadenasAceptadas, cadenasRechazadas);
		cont++;
		bondadTemp=0;
	}
	
	/**
	 * Actualiza la bondad de las palabras aceptadas
	 */
	public void actualizarBondadAceptada(double probabilidad) {
		bondadTemp += probabilidad;
		cont ++;
		bondad = bondadTemp / cont;
	}

	/**
	 * Actualiza la bondad de las palabras rechazadas
	 */
	public void actualizarBondadRechazada(double probabilidad) {
		bondadTemp += probabilidad;
		cont++;
		bondad = bondadTemp / cont;
	}

}
