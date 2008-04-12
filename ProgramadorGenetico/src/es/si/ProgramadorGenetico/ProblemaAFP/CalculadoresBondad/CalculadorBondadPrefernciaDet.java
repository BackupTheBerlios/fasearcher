package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

public class CalculadorBondadPrefernciaDet extends CalculadorBondad {

	public static final double VERSION = 1.3f;

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

	private double calcularPorcentajeUnos(int estados, double[][][] trans) {
		double valor =0;
		int cont= 0;
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j < estados + 1; j++) {
				if (trans[i][0][j] > 0.95)
					cont++;
				if (trans[i][1][j] > 0.95)
					cont++;
			}
		}
		valor = (double) cont / (estados*2);
		return valor/2;
	}
}
