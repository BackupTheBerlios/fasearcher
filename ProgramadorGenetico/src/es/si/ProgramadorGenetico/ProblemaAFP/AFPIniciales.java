package es.si.ProgramadorGenetico.ProblemaAFP;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.PoblacionInicial;

public class AFPIniciales implements PoblacionInicial {

	
	
	@Override
	public Poblacion generar() {
		Poblacion pob = new Poblacion();
		int estados = ParametrosAFP.getInstance().getEstados();
		for (int i = 0; i < Algoritmo.POB_MAX; i++) {
			pob.agregarMiembro(GeneradorAleatorioAFP.nuevo(estados));
		}
		//ParametrosAFP.getInstance();
		
		return pob;
	}

}
