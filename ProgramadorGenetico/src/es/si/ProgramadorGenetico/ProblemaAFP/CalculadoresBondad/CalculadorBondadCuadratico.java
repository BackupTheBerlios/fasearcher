package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

/**
 * Este calculador de bondad realiza el cuadrado de las probabilidades
 * de aceptar una palabra, de esta forma tiende a diferenciar entre
 * automatas que reconocen todas las palabras de forma media de los que
 * reconocen algunas palabras especialmente bien (favoreciendo a estos
 * ultimos).
 */
public class CalculadorBondadCuadratico extends CalculadorBondad {

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
	 * Constructora que toma un afp, cadenas aceptadas
	 * y cadenas rechazadas y construye el calculador
	 * @param afp
	 * @param cadenasAceptadas
	 * @param cadenasRechazadas
	 */
	public CalculadorBondadCuadratico(AFP afp,
			List<String> cadenasAceptadas,
			List<String> cadenasRechazadas) {
		super(afp, cadenasAceptadas, cadenasRechazadas);
		cont = 0;
		bondadTemp = 0;
	}
	
	/**
	 * Actualiza la bondad de las palabras aceptadas
	 */
	public void actualizarBondadAceptada(double probabilidad) {
		bondadTemp += probabilidad*probabilidad;
		cont++;
		bondad = bondadTemp / cont;
	}

	/**
	 * Actualiza la bondad de las palabras rechazadas 
	 */
	public void actualizarBondadRechazada(double probabilidad) {
		bondadTemp += probabilidad*probabilidad;
		cont++;
		bondad = bondadTemp / cont;
	}

}
