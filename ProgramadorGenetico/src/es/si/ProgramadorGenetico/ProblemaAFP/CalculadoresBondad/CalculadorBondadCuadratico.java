package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

public class CalculadorBondadCuadratico extends CalculadorBondad {

	public CalculadorBondadCuadratico(AFP afp,
			List<String> cadenasAceptadas,
			List<String> cadenasRechazadas) {
		super(afp, cadenasAceptadas, cadenasRechazadas);
	}
	
	@Override
	public void actualizarBondad(double probabilidad) {
		bondad += probabilidad*probabilidad;

	}

}
