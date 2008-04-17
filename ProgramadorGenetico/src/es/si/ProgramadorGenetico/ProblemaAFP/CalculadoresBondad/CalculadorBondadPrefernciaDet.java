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

	public static final double VERSION = 2.0f;

	private double bondadAceptadas;
	
	private double bondadRechazadas;
	private double bondadUnos;
	private int cantAceptadas;
	
	private int cantRechazadas;

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
	
	@Override
	public void actualizarBondadAceptada(double probabilidad) {
		bondadAceptadas+=probabilidad;
		bondad = bondadAceptadas / cantAceptadas;
		if (cantRechazadas != 0)
			bondad = (bondad/2) + (bondadRechazadas / (cantRechazadas*2));
		bondad = bondad/2 + bondadUnos;
	}

	@Override
	public void actualizarBondadRechazada(double probabilidad) {
		bondadRechazadas+=probabilidad;
		bondad = bondadRechazadas / cantRechazadas;
		if (cantAceptadas != 0)
			bondad = (bondad/2) + (bondadAceptadas / (cantAceptadas*2));
		bondad = bondad/2 + bondadUnos;
	}

	private float calcularPorcentajeUnos(int estados, float[][][] trans) {
		float valor =0;
		float suma = 0;
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j < estados + 1; j++) {
				if (trans[i][0][j] > 0.5)
					suma += trans[i][0][j];
				if (trans[i][1][j] > 0.5)
					suma += trans[i][0][j];
			}
		}
		valor = suma / (estados*2);
		return valor/2;
	}
}
