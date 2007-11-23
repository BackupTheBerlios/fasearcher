package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

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
