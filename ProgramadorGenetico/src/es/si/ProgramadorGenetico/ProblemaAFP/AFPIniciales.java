package es.si.ProgramadorGenetico.ProblemaAFP;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.PoblacionInicial;

/**
 * Clase que implementa la poblaci�n inicial para el algoritmo gen�tico
 * con individuos del tipo AFP.<p>
 * 
 * Esta clase tiene un m�todo que genera una poblaci�n de tantos
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
