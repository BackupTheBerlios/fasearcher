package es.si.ProgramadorGenetico.ProblemaAFP;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.PoblacionInicial;

/**
 * Clase que implementa la población inicial para el algoritmo genético
 * con individuos del tipo AFP.<p>
 * 
 * Esta clase tiene un método que genera una población de tantos
 * individuos como se indique en el algoritmo.
 *
 */
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
