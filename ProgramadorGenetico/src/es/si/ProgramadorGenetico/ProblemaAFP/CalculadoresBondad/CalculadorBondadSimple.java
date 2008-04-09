package es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;

public class CalculadorBondadSimple extends CalculadorBondad {

	public static final double VERSION = 1.0f;

	private int cont;
	private double bondadTemp;
	public CalculadorBondadSimple(AFP afp,
			List<String> cadenasAceptadas,
			List<String> cadenasRechazadas) {
		super(afp, cadenasAceptadas, cadenasRechazadas);
		cont++;
		bondadTemp=0;
	}
	
	@Override
	public void actualizarBondadAceptada(double probabilidad) {
		bondadTemp += probabilidad;
		cont ++;
		bondad = bondadTemp / cont;
	}

	@Override
	public void actualizarBondadRechazada(double probabilidad) {
		bondadTemp += probabilidad;
		cont++;
		bondad = bondadTemp / cont;
	}

}
