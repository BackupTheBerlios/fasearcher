package es.si.ProgramadorGenetico.ProblemaAFP.Mutadores;

import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.Poblacion;

public class MutadorAFPNulo implements Mutador {

	@Override
	public Poblacion mutar(Poblacion poblacion) {
		return poblacion;
	}

}
