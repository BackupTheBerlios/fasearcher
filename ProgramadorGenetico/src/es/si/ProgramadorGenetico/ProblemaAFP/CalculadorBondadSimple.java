package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.List;

public class CalculadorBondadSimple extends CalculadorBondad {

	public CalculadorBondadSimple(AFP afp,
			List<String> cadenasAceptadas,
			List<String> cadenasRechazadas) {
		super(afp, cadenasAceptadas, cadenasRechazadas);
	}

	@Override
	public void actualizarBondad(double probabilidad) {
		bondad += probabilidad;
	}

}
