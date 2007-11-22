package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.List;

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
