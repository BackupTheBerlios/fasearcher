package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;

import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondadSimple;
import es.si.ProgramadorGenetico.ProblemaAFP.SelectorAutomatas;

public class PruebaSeleccionador {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Poblacion pob = new Poblacion();
		for (int i = 0; i < 10000; i++) {
			pob.agregarMiembro(GeneradorAleatorioAFP.nuevo(5));
		}
		SelectorAutomatas selector = new SelectorAutomatas();
		long tiempo = System.currentTimeMillis();
		selector.mejor(pob);
		System.out.println("tiempo: " + (System.currentTimeMillis() - tiempo));
		
	}

}
