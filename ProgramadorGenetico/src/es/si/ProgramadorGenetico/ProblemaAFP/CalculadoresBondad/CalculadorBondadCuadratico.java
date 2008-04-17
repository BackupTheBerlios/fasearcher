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

	private int cont;
	private double bondadTemp;
	public CalculadorBondadCuadratico(AFP afp,
			List<String> cadenasAceptadas,
			List<String> cadenasRechazadas) {
		super(afp, cadenasAceptadas, cadenasRechazadas);
		cont = 0;
		bondadTemp = 0;
	}
	
	@Override
	public void actualizarBondadAceptada(double probabilidad) {
		bondadTemp += probabilidad*probabilidad;
		cont++;
		bondad = bondadTemp / cont;
	}

	@Override
	public void actualizarBondadRechazada(double probabilidad) {
		bondadTemp += probabilidad*probabilidad;
		cont++;
		bondad = bondadTemp / cont;
	}

}
