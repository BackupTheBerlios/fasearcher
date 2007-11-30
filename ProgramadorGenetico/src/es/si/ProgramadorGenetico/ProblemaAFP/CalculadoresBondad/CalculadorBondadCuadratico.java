package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

public class CalculadorBondadCuadratico extends CalculadorBondad {

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
