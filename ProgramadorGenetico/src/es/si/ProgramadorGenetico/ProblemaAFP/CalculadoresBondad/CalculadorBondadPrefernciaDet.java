package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

/**
 * Este calculador de bondad tiene en cuenta no solo las probabilidades de 
 * aceptar y rechazar las cadenas dadas, sino tambien cuanto se parece el
 * Automata Finito Probabilista a un Automata Finito.
 */
public class CalculadorBondadPrefernciaDet extends CalculadorBondad {

	public static final double VERSION = 2.1f;
	/**
	 * Porcentaje de importancia que se da al reconocimiento de las cadenas
	 */
	private float pesoCadenas = 0.8f;
	/**
	 * Bondad de las palabras aceptadas
	 */
	private double bondadAceptadas;
	/**
	 * Bondad de las palabras rechazadas
	 */
	private double bondadRechazadas;
	/**
	 * Bondad de la cantidad de unos que hay entre
	 * las transiciones
	 * Cuantos mas unos haya, mas se parecera a un AF
	 */
	private double bondadUnos;
	/**
	 * Cantidad de palabras aceptadas
	 */
	private int cantAceptadas;
	/**
	 * Cantidad de palabras rechazadas
	 */
	private int cantRechazadas;
	/**
	 * Constructora que a partir de los parametros construye
	 * el calculador
	 * @param afp
	 * @param cadenasAceptadas
	 * @param cadenasRechazadas
	 */
	public CalculadorBondadPrefernciaDet(AFP afp,
			List<String> cadenasAceptadas,
			List<String> cadenasRechazadas) {
		super(afp, cadenasAceptadas, cadenasRechazadas);
		bondadUnos = calcularPorcentajeUnos(afp.getEstados(), afp.getTransiciones());
		bondadAceptadas = 0;
		bondadRechazadas = 0;
		cantAceptadas = cadenasAceptadas.size();
		cantRechazadas = cadenasRechazadas.size();
	}
	
	/**
	 * Actualiza la bondad de las palabras aceptadas
	 */
	public void actualizarBondadAceptada(double probabilidad) {
		bondadAceptadas+=probabilidad;
		bondad = bondadAceptadas / cantAceptadas;
		if (cantRechazadas != 0)
			bondad = (bondad/2) + (bondadRechazadas / (cantRechazadas*2));
		bondad = bondad * pesoCadenas + bondadUnos;
	}

	/**
	 * Actualiza la bondad de las palabras rechazadas
	 */
	public void actualizarBondadRechazada(double probabilidad) {
		bondadRechazadas+=probabilidad;
		bondad = bondadRechazadas / cantRechazadas;
		if (cantAceptadas != 0)
			bondad = (bondad/2) + (bondadAceptadas / (cantAceptadas*2));
		bondad = bondad * pesoCadenas + bondadUnos;
	}
	/**
	 * Calcula el porcentaje de unos
	 * Cuando se actualiza la bondad general, se le suma el valor devuelto por
	 * este metodo. Este valor cuenta como una parte de la bondad total. El resto
	 * de la bondad se obtiene a partir de la bondad de las cadenas
	 * @param estados
	 * @param trans
	 * @return
	 */
	private float calcularPorcentajeUnos(int estados, float[][][] trans) {
		float valor =0;
		float suma = 0;
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j < estados + 1; j++) {
				
				if (trans[i][0][j] > 0.5)
					suma += trans[i][0][j]*trans[i][0][j];
				if (trans[i][1][j] > 0.5)
					suma += trans[i][1][j]*trans[i][1][j];
				/*
				float valor0 = (float) Math.abs(0.5-trans[i][0][j]);
				float valor1 = (float) Math.abs(0.5-trans[i][1][j]);
				//Esa resta se acerca a 0 si la probabilidad es proxima a 0.5, y se acerca a 0.5 si la probabilidad es próxima a 1 o a 0.
				//Si el resultado es mayor a 0.25, es que la probabilidad es < 0.25 o > 0.75, más parecida a un AFD
				if (valor0 > 0.25)
				  suma+=trans[i][0][j];
				if (valor1 > 0.25)
				  suma+=trans[i][1][j];
				*/
			}
		}
		valor = suma / (estados*2);
		return valor * (1.0f - pesoCadenas);
	}
}
