package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

/**
 * Este calculador de bondad da igual importancia a las palabras aceptadas
 * que a las rechazadas, independientemente del n�mero que haya de cada una de
 * ellas.
 */
public class CalculadorBondadBalanceado extends CalculadorBondad {
	
	public static final double VERSION = 1.0f;
	/**
	 * Bondad de las palabras aceptadas
	 */
	private double bondadAceptadas;
	/**
	 * Bondad de las palabras rechazadas
	 */
	private double bondadRechazadas;
	/**
	 * Cantidad de palabras aceptadas
	 */
	private int cantAceptadas;
	/**
	 * Cantidad de palabras rechazadas
	 */
	private int cantRechazadas;
	/**
	 * Constructora que a partir de un afp, cadenas aceptadas
	 * y cadenas rechazadas, construye el calculador de bondad balanceado
	 * @param afp
	 * @param cadenasAceptadas
	 * @param cadenasRechazadas
	 */
	public CalculadorBondadBalanceado(AFP afp,
			List<String> cadenasAceptadas,
			List<String> cadenasRechazadas) {
		super(afp, cadenasAceptadas, cadenasRechazadas);
		bondadAceptadas = 0;
		bondadRechazadas = 0;
		cantAceptadas = cadenasAceptadas.size();
		cantRechazadas = cadenasRechazadas.size();
	}


	/**
	 * Actualiza la bondad de palabra aceptada
	 */
	public void actualizarBondadAceptada(double probabilidad) {
		bondadAceptadas+=probabilidad;
		bondad = bondadAceptadas / cantAceptadas;
		if (cantRechazadas != 0)
			bondad = (bondad/2) + (bondadRechazadas / (cantRechazadas*2));
	}


	/**
	 * Actualiza la bondad de palabra rechazada
	 */
	public void actualizarBondadRechazada(double probabilidad) {
		bondadRechazadas+=probabilidad;
		bondad = bondadRechazadas / cantRechazadas;
		if (cantAceptadas != 0)
			bondad = (bondad/2) + (bondadAceptadas / (cantAceptadas*2));
	}

}
